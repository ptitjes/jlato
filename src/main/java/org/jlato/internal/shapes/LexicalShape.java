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

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.printer.Printer;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class LexicalShape {

	// TODO Add argument to follow lexical runs

	public abstract boolean isDefined(STree tree);

	public abstract boolean isWhitespaceOnly(STree tree);

	public abstract void render(STree tree, Printer printer);

	public abstract SpacingConstraint spacingBefore(STree tree);

	public abstract SpacingConstraint spacingAfter(STree tree);

	public interface ShapeProvider {
		LexicalShape shapeFor(STree tree);
	}

	private static class DefaultShapeProvider implements ShapeProvider {

		public LexicalShape shapeFor(STree tree) {
			return tree.kind.shape();
		}
	}

	public static class Factory {
		public static LSNone none() {
			return new LSNone();
		}

		public static LexicalShape option(ShapeProvider provider) {
			return new LSDynamicShape(provider);
		}

		public static LexicalShape childKindAlternative(final int index, final Tree.Kind kind, final LexicalShape shape, final LexicalShape alternative) {
			return option(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final SNodeState state = ((SNode) tree).state();
					final Tree.Kind childKind = state.child(index).kind;
					return childKind == kind ? shape : alternative;
				}
			});
		}

		public static LexicalShape dataOption(final int index, final LexicalShape shape) {
			return new LSDynamicShape(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final STreeState state = tree.state;
					return state != null && (Boolean) state.data(index) ? shape : null;
				}
			});
		}

		public static LexicalShape nonNullChild(final int index, LexicalShape shape) {
			return nonNullChild(index, shape, null);
		}

		public static LexicalShape nonNullChild(final int index, final LexicalShape shape, final LexicalShape alternative) {
			return new LSDynamicShape(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final SNodeState state = ((SNode) tree).state();
					return state != null && state.child(index) != null ? shape : alternative;
				}
			});
		}

		public static LexicalShape nonEmptyChildren(final int index, final LexicalShape shape) {
			return nonEmptyChildren(index, shape, null);
		}

		public static LexicalShape nonEmptyChildren(final int index, final LexicalShape shape, final LexicalShape alternative) {
			return new LSDynamicShape(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final SNodeState state = ((SNode) tree).state();
					final SNodeList nodeList = (SNodeList) state.child(index);
					if (nodeList == null) return alternative;

					final Vector<STree> children = nodeList.state().children;
					return !(children == null || children.isEmpty()) ? shape : alternative;
				}
			});
		}

		public static LexicalShape emptyChildren(final int index, final LexicalShape shape) {
			return nonEmptyChildren(index, null, shape);
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

		public static LexicalShape child(int index) {
			return child(index, defaultShape());
		}

		public static LexicalShape child(int index, LexicalShape shape) {
			return new LSTravesal(index, shape);
		}

		public static LexicalShape children(int index) {
			return children(index, null, null, null);
		}

		public static LexicalShape children(int index, LexicalShape separator) {
			return children(index, null, separator, null);
		}

		public static LexicalShape children(int index, LexicalShape shape, LexicalShape separator) {
			return children(index, shape, null, separator, null);
		}

		public static LexicalShape children(int index, LexicalShape before, LexicalShape separator, LexicalShape after) {
			return children(index, defaultShape(), before, separator, after);
		}

		public static LexicalShape children(int index, LexicalShape shape, LexicalShape before, LexicalShape separator, LexicalShape after) {
			return child(index, new LSListShape(shape, before, separator, after));
		}

		private static LexicalShape defaultShape() {
			return new LSDynamicShape(new DefaultShapeProvider());
		}
	}
}
