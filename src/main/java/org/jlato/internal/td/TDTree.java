/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.td;

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.Printer;
import org.jlato.rewrite.MatchVisitor;
import org.jlato.rewrite.Matcher;
import org.jlato.rewrite.Substitution;
import org.jlato.rewrite.TypeSafeMatcher;
import org.jlato.tree.*;
import org.jlato.tree.expr.Expr;

import java.util.Arrays;
import java.util.LinkedList;

import static org.jlato.internal.bu.WToken.newLine;
import static org.jlato.internal.bu.WToken.whitespace;

/**
 * @author Didier Villevalois
 */
public abstract class TDTree<S extends STree, ST extends Tree, T extends ST> implements Tree, TreeCombinators<T> {

	protected final TDLocation<S> location;

	protected TDTree(TDLocation<S> location) {
		this.location = location;
	}

	public Tree parent() {
		TDLocation<?> parentLocation = location.parent();
		return parentLocation == null ? null : parentLocation.facade;
	}

	public Tree root() {
		return location.root().facade;
	}

	@Override
	public boolean hasProblems() {
		return location.tree.hasProblems();
	}

	@Override
	public java.lang.Iterable<Problem> problems() {
		ProblemCollector collector = new ProblemCollector();
		leftForAll(new TypeSafeMatcher<Tree>() {
			@Override
			public Substitution match(Object object) {
				return match(object, Substitution.empty());
			}

			@Override
			public Substitution match(Object object, Substitution substitution) {
				return object instanceof TDTree && !((TDTree) object).location.tree.problems().isEmpty() ? substitution : null;
			}
		}, collector);
		return collector.getList();
	}

	private static class ProblemCollector implements MatchVisitor<Tree> {

		private final LinkedList<Problem> list = new LinkedList<Problem>();

		@Override
		public Tree visit(Tree t, Substitution s) {
			Vector<BUProblem> problems = ((TDTree<?, ?, ?>) t).location.tree.problems();
			for (BUProblem problem : problems) {
				list.add(new TDProblem(problem, t));
			}
			return t;
		}

		public LinkedList<Problem> getList() {
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	private T self() {
		return (T) this;
	}

	@Override
	public String toString() {
		// TODO Add specific cases for NodeList, NodeOption, NodeEither
		return Printer.printToString(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TDTree<?, ?, ?> tdTree = (TDTree<?, ?, ?>) o;

		return location.tree.equals(tdTree.location.tree);
	}

	@Override
	public int hashCode() {
		return location.tree.hashCode();
	}

	// Combinators

	public <U extends Tree> T forAll(TypeSafeMatcher<? extends U> matcher, MatchVisitor<U> visitor) {
		return leftForAll(matcher, visitor);
	}

	public <U extends Tree> T leftForAll(TypeSafeMatcher<? extends U> matcher, MatchVisitor<U> visitor) {
		return Traversal.leftForAll(self(), matcher, visitor);
	}

	public <U extends Tree> T rightForAll(TypeSafeMatcher<? extends U> matcher, MatchVisitor<U> visitor) {
		return Traversal.rightForAll(self(), matcher, visitor);
	}

	public Substitution match(Matcher matcher) {
		return matcher.match(this);
	}

	public boolean matches(Matcher matcher) {
		return matcher.match(this) != null;
	}

	public T match(TypeSafeMatcher<? extends T> matcher, MatchVisitor<T> visitor) {
		Substitution match = matcher.match(this);
		return match == null ? self() : visitor.visit(self(), match);
	}

	public <U extends Tree> Iterable<U> findAll(TypeSafeMatcher<U> matcher) {
		return leftFindAll(matcher);
	}

	public <U extends Tree> Iterable<U> leftFindAll(TypeSafeMatcher<U> matcher) {
		final MatchCollector<U> collector = new MatchCollector<U>();
		leftForAll(matcher, collector);
		return collector.getList();
	}

	public <U extends Tree> Iterable<U> rightFindAll(TypeSafeMatcher<U> matcher) {
		final MatchCollector<U> collector = new MatchCollector<U>();
		rightForAll(matcher, collector);
		return collector.getList();
	}

	private static class MatchCollector<U extends Tree> implements MatchVisitor<U> {

		private final LinkedList<U> list = new LinkedList<U>();

		@Override
		public U visit(U t, Substitution s) {
			list.add(t);
			return t;
		}

		public LinkedList<U> getList() {
			return list;
		}
	}

	// Comment manipulation functions

	private static final String[] NO_COMMENTS = {};

	public String[] leadingComments() {
		final BUTree<S> tree = location.tree;
		return tree.dressing == null || tree.dressing.leading == null ? NO_COMMENTS :
				tree.dressing.leading.getComments();
	}

	public String[] trailingComments() {
		final BUTree<S> tree = location.tree;
		return tree.dressing == null || tree.dressing.trailing == null ? NO_COMMENTS :
				tree.dressing.trailing.getComments();
	}

	public T insertLeadingComment(String commentString) {
		return insertLeadingComment(commentString, false);
	}

	public T insertLeadingComment(String commentString, boolean forceMultiLine) {
		boolean expressionContext = expressionContext();

		final BUTree<S> tree = location.tree;
		final WDressing dressing = tree.dressing == null ? new WDressing() : tree.dressing;

		final WTokenRun leading = dressing.leading == null ? WTokenRun.EMPTY : dressing.leading;

		final WToken comment = createComment(commentString, expressionContext, forceMultiLine);
		final WTokenRun newLeading;
		if (expressionContext && comment.kind != ParserImplConstants.SINGLE_LINE_COMMENT) {
			newLeading = leading.append(comment).append(whitespace(" "));
		} else {
			newLeading = leading.append(comment).append(newLine());
		}

		return location.replaceTree(tree.withDressing(dressing.withLeading(newLeading)));
	}

	public T insertTrailingComment(String commentString) {
		return insertTrailingComment(commentString, false);
	}

	public T insertTrailingComment(String commentString, boolean forceMultiLine) {
		boolean expressionContext = expressionContext();

		final BUTree<S> tree = location.tree;
		final WDressing dressing = tree.dressing == null ? new WDressing() : tree.dressing;

		final WTokenRun trailing = dressing.trailing == null ? WTokenRun.EMPTY : dressing.trailing;

		final WToken comment = createComment(commentString, expressionContext, forceMultiLine || trailing.containsSingleLineComment());
		final WTokenRun newTrailing = trailing.prepend(comment).prepend(whitespace(" "));

		return location.replaceTree(tree.withDressing(dressing.withTrailing(newTrailing)));
	}

	private boolean expressionContext() {
		return this instanceof Expr;
	}

	private WToken createComment(String commentString, boolean expressionContext, boolean forceMultiLine) {
		if (expressionContext || forceMultiLine)
			return WToken.multiLineComment("/* " + commentString.trim() + " */");
		else
			return WToken.singleLineComment("// " + commentString.trim());
	}

	public T withDocComment(String commentString) {
		WToken comment = WToken.javaDocComment("/** " + commentString.trim() + " */");

		final BUTree<S> tree = location.tree;
		final WDressing dressing = tree.dressing == null ? new WDressing() : tree.dressing;

		final WTokenRun leading = dressing.leading == null ? WTokenRun.EMPTY : dressing.leading;
		final WTokenRun newLeading = leading.replaceOrAppendDocComment(comment);

		return location.replaceTree(tree.withDressing(dressing.withLeading(newLeading)));
	}

	public String docComment() {
		final BUTree<S> tree = location.tree;
		final WDressing dressing = tree.dressing == null ? new WDressing() : tree.dressing;

		final WTokenRun leading = dressing.leading == null ? WTokenRun.EMPTY : dressing.leading;
		final WToken docComment = leading.getDocComment();
		return docComment != null ? trimDocCommentString(docComment.string) : null;
	}

	private String trimDocCommentString(String docCommentString) {
		return docCommentString.substring("/**".length(), docCommentString.length() - "*/".length()).trim();
	}

	public T insertNewLineBefore() {
		final BUTree<S> tree = location.tree;
		final WDressing dressing = tree.dressing == null ? new WDressing() : tree.dressing;

		final WTokenRun leading = dressing.leading == null ? WTokenRun.EMPTY : dressing.leading;
		final WTokenRun newLeading = leading.append(newLine());

		return location.replaceTree(tree.withDressing(dressing.withLeading(newLeading)));
	}

	// Convenience conversion helper methods
	// TODO Move that elsewhere

	@SuppressWarnings("unchecked")
	public static <S extends STree> TDLocation<S> locationOf(Tree facade) {
		return facade == null ? null : ((TDTree<S, ?, ?>) facade).location;
	}

	public static <S extends STree> BUTree<S> treeOf(Tree facade) {
		final TDLocation<S> location = TDTree.locationOf(facade);
		return location == null ? null : location.tree;
	}

	public static Vector<BUTree<? extends STree>> treeListOf(Tree... facades) {
		final Builder<BUTree<?>, Vector<BUTree<?>>> builder = Vector.<BUTree<?>>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}
}
