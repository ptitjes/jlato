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

package org.jlato.internal.shapes;

import org.jlato.internal.bu.*;
import org.jlato.printer.IndentationConstraint;
import org.jlato.printer.Printer;
import org.jlato.printer.SpacingConstraint;
import org.jlato.tree.Tree;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public abstract class LexicalShape {

	// TODO Add argument to follow lexical runs

	public abstract boolean isDefined(STree tree);

	public abstract WRunRun enRun(STree tree, Iterator<WTokenRun> tokenIterator);

	public abstract void render(STree tree, WRunRun run, Printer printer);

	public LSDecorated withSpacing(SpacingConstraint before, SpacingConstraint after) {
		return new LSConstraint(this, before, after, null, null);
	}

	public LSDecorated withSpacingBefore(SpacingConstraint spacingBefore) {
		return new LSConstraint(this, spacingBefore, null, null, null);
	}

	public LSDecorated withSpacingAfter(SpacingConstraint spacingAfter) {
		return new LSConstraint(this, null, spacingAfter, null, null);
	}

	public LSDecorated withIndentationBefore(IndentationConstraint indentationBefore) {
		return new LSConstraint(this, null, null, indentationBefore, null);
	}

	public LSDecorated withIndentationAfter(IndentationConstraint indentationAfter) {
		return new LSConstraint(this, null, null, null, indentationAfter);
	}

	public static LSNone none() {
		return new LSNone();
	}

	public static LexicalShape when(LSCondition condition, LexicalShape shape) {
		return new LSAlternative(condition, shape, none());
	}

	public static LexicalShape alternative(LSCondition condition, LexicalShape shape, LexicalShape alternative) {
		return new LSAlternative(condition, shape, alternative);
	}

	public static LexicalShape childKindAlternative(int index, Tree.Kind kind, LexicalShape shape, LexicalShape alternative) {
		return alternative(LSCondition.childKind(index, kind), shape, alternative);
	}

	public static LexicalShape dataOption(int index, LexicalShape shape) {
		return alternative(LSCondition.data(index), shape, null);
	}

	@Deprecated
	public static LexicalShape nonNullChild(int index, LexicalShape shape) {
		return nonNullChild(index, shape, null);
	}

	@Deprecated
	public static LexicalShape nonNullChild(int index, LexicalShape shape, LexicalShape alternative) {
		return alternative(LSCondition.nonNullChild(index), shape, alternative);
	}

	public static LexicalShape nonEmptyChildren(int index, LexicalShape shape) {
		return nonEmptyChildren(index, shape, null);
	}

	public static LexicalShape nonEmptyChildren(int index, LexicalShape shape, LexicalShape alternative) {
		return alternative(LSCondition.not(LSCondition.emptyList(index)), shape, alternative);
	}

	public static LexicalShape emptyChildren(int index, LexicalShape shape) {
		return nonEmptyChildren(index, null, shape);
	}

	public static LexicalShape emptyChildren(int index, LexicalShape shape, LexicalShape alternative) {
		return nonEmptyChildren(index, alternative, shape);
	}

	public static LexicalShape composite(LexicalShape... shapes) {
		return new LSComposite(shapes);
	}

	public static LSToken token(LToken token) {
		return new LSToken(token);
	}

	public static LSToken token(LSToken.Provider tokenProvider) {
		return new LSToken(tokenProvider);
	}

	public static LexicalShape element() {
		return element(defaultShape());
	}

	public static LexicalShape element(LexicalShape shape) {
		return new LSNodeOptionElement(shape);
	}

	public static LexicalShape child(int index) {
		return child(index, defaultShape());
	}

	public static LexicalShape child(int index, LexicalShape shape) {
		return new LSNodeChild(index, shape);
	}

	public static LexicalShape list() {
		return list(false, defaultShape(), null, null, null);
	}

	public static LexicalShape list(LexicalShape separator) {
		return list(false, defaultShape(), null, separator, null);
	}

	public static LexicalShape list(LexicalShape before, LexicalShape separator, LexicalShape after) {
		return list(false, defaultShape(), before, separator, after);
	}

	public static LexicalShape list(boolean renderIfEmpty, LexicalShape before, LexicalShape separator, LexicalShape after) {
		return list(renderIfEmpty, defaultShape(), before, separator, after);
	}

	public static LexicalShape list(boolean renderIfEmpty, LexicalShape shape, LexicalShape before, LexicalShape separator, LexicalShape after) {
		return new LSList(shape, before, separator, after, renderIfEmpty);
	}

	public static LexicalShape defaultShape() {
		return new LSDefault();
	}
}
