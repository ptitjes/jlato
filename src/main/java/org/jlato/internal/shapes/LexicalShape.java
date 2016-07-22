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
import org.jlato.internal.bu.coll.SNodeEither;
import org.jlato.internal.bu.coll.SNodeOption;

import static org.jlato.internal.shapes.SpacingConstraint.space;

/**
 * @author Didier Villevalois
 */
public abstract class LexicalShape {

	// TODO Add argument to follow lexical runs

	public abstract boolean isDefined(BUTree tree);

	public abstract void dress(DressingBuilder<?> builder, BUTree<?> discriminator);

	public abstract boolean acceptsTrailingWhitespace();

	public abstract boolean acceptsLeadingWhitespace();

	public abstract void dressTrailing(WTokenRun tokens, DressingBuilder<?> builder);

	public abstract void dressLeading(WTokenRun tokens, DressingBuilder<?> builder);

	public void render(BUTree tree, Print print) {
		render(tree, tree.dressing == null ? null : tree.dressing.run, print);
	}

	public abstract void render(BUTree tree, WRunRun run, Print print);

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

	public static LexicalShape composite(LexicalShape... shapes) {
		return new LSComposite(shapes);
	}

	public static LSToken token(LToken token) {
		return new LSToken(token);
	}

	public static LSToken token(LSToken.Provider tokenProvider) {
		return new LSToken(tokenProvider);
	}

	public static LexicalShape keyword(LToken token) {
		return token(token).withSpacing(space(), space());
	}

	public static LexicalShape keyword(LSToken.Provider tokenProvider) {
		return token(tokenProvider).withSpacing(space(), space());
	}

	public static LexicalShape element() {
		return element(defaultShape());
	}

	public static LexicalShape element(LexicalShape shape) {
		return new LSTraversal(SNodeOption.elementTraversal(), shape);
	}

	public static LexicalShape leftOrRight() {
		return leftOrRight(defaultShape());
	}

	public static LexicalShape leftOrRight(LexicalShape shape) {
		return new LSTraversal(SNodeEither.elementTraversal(), shape);
	}

	public static LexicalShape child(STraversal traversal) {
		return child(traversal, defaultShape());
	}

	public static LexicalShape child(STraversal traversal, LexicalShape shape) {
		return new LSTraversal(traversal, shape);
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
