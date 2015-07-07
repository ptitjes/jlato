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
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public abstract class LexicalShape {

	// TODO Add argument to follow lexical runs

	public abstract void render(STree tree, Printer printer);

	public interface ShapeProvider {
		LexicalShape shapeFor(STree tree);
	}

	private static class FixedShapeProvider implements ShapeProvider {

		private final LexicalShape shape;

		public FixedShapeProvider(LexicalShape shape) {
			this.shape = shape;
		}

		public LexicalShape shapeFor(STree tree) {
			return shape;
		}
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
			return new LSOption(provider);
		}

		public static LexicalShape dataOption(final int index, final LexicalShape shape) {
			return new LSOption(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final STreeState state = tree.state;
					return state != null && (Boolean) state.data(index) ? shape : null;
				}
			});
		}

		public static LexicalShape nonNullData(final int index, LexicalShape shape) {
			return nonNullData(index, shape, null);
		}

		public static LexicalShape nonNullData(final int index, final LexicalShape shape, final LexicalShape alternative) {
			return new LSOption(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final STreeState state = tree.state;
					return state != null && state.data(index) != null ? shape : alternative;
				}
			});
		}

		public static LexicalShape nonNullChild(final int index, LexicalShape shape) {
			return nonNullChild(index, shape, null);
		}

		public static LexicalShape nonNullChild(final int index, final LexicalShape shape, final LexicalShape alternative) {
			return new LSOption(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final SNodeState state = (SNodeState) tree.state;
					return state != null && state.child(index) != null ? shape : alternative;
				}
			});
		}

		public static LexicalShape nonEmptyChildren(final int index, final LexicalShape shape) {
			return nonEmptyChildren(index, shape, null);
		}

		public static LexicalShape nonEmptyChildren(final int index, final LexicalShape shape, final LexicalShape alternative) {
			return new LSOption(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final SNodeList nodeList = (SNodeList) ((SNodeState) tree.state).child(index);
					if (nodeList == null) return alternative;

					final LRun run = nodeList.run;
					return run != null && run.treeCount() > 0 ? shape : alternative;
				}
			});
		}

		public static LexicalShape nonEmptyChildren(final int index, final Function1<STree, Boolean> filter, final LexicalShape shape, final LexicalShape alternative) {
			return new LSOption(new ShapeProvider() {
				public LexicalShape shapeFor(STree tree) {
					final SNodeList nodeList = (SNodeList) ((SNodeState) tree.state).child(index);
					if (nodeList == null) return alternative;

					final LRun run = nodeList.run;
					if (run == null || run.treeCount() == 0) return alternative;

					for (STree child : run) {
						if (filter.apply(child)) return shape;
					}
					return alternative;
				}
			});
		}

		public static LexicalShape indent(final IndentationContext context) {
			return new LexicalShape() {
				public void render(STree tree, Printer printer) {
					printer.indent(printer.formattingSettings.indentation(context));
				}
			};
		}

		public static LexicalShape unIndent(final IndentationContext context) {
			return new LexicalShape() {
				public void render(STree tree, Printer printer) {
					printer.unIndent(printer.formattingSettings.indentation(context));
				}
			};
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

		public static LexicalShape child(final int index) {
			return new LSChild(new LSChild.ChildSelector() {
				public STree select(SNode node) {
					return ((SNodeState) node.state).child(index);
				}
			}, new DefaultShapeProvider());
		}

		public static LexicalShape child(final int index, final LexicalShape shape) {
			return new LSChild(new LSChild.ChildSelector() {
				public STree select(SNode node) {
					return ((SNodeState) node.state).child(index);
				}
			}, new FixedShapeProvider(shape));
		}

		public static LexicalShape children(int index) {
			return children(index, Function1.Std.<STree>alwaysTrue(), null, null, null);
		}

		public static LexicalShape children(int index, LexicalShape separator) {
			return children(index, Function1.Std.<STree>alwaysTrue(), null, separator, null);
		}

		public static LexicalShape children(int index, Function1<STree, Boolean> filter, LexicalShape separator) {
			return children(index, filter, null, separator, null);
		}

		public static LexicalShape children(int index,
		                                    LexicalShape before, LexicalShape separator, LexicalShape after) {
			return children(index, Function1.Std.<STree>alwaysTrue(), before, separator, after);
		}

		public static LexicalShape children(int index, Function1<STree, Boolean> filter,
		                                    LexicalShape before, LexicalShape separator, LexicalShape after) {
			return children(index, filter, new DefaultShapeProvider(), before, separator, after);
		}

		public static LexicalShape children(int index, LexicalShape shape, LexicalShape separator) {
			return children(index, Function1.Std.<STree>alwaysTrue(), shape, null, separator, null);
		}

		public static LexicalShape children(int index, Function1<STree, Boolean> filter,
		                                    LexicalShape shape, LexicalShape separator) {
			return children(index, filter, shape, null, separator, null);
		}

		public static LexicalShape children(int index, LexicalShape shape,
		                                    LexicalShape before, LexicalShape separator, LexicalShape after) {
			return children(index, Function1.Std.<STree>alwaysTrue(), shape, before, separator, after);
		}

		public static LexicalShape children(int index, Function1<STree, Boolean> filter, LexicalShape shape,
		                                    LexicalShape before, LexicalShape separator, LexicalShape after) {
			return children(index, filter, new FixedShapeProvider(shape), before, separator, after);
		}

		public static LexicalShape children(final int index, final Function1<STree, Boolean> filter, ShapeProvider shapeProvider,
		                                    final LexicalShape before, final LexicalShape separator, final LexicalShape after) {
			return new LSChildren(new LSChildren.ChildrenSelector() {
				public SNodeList select(SNode node) {
					return (SNodeList) ((SNodeState) node.state).child(index);
				}

				public boolean filter(STree tree) {
					return filter.apply(tree);
				}
			}, shapeProvider, before, separator, after);
		}
	}
}
