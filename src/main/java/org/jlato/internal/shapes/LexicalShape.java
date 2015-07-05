package org.jlato.internal.shapes;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public interface LexicalShape {

	void render(STree tree, Printer printer);

	class Factory {
		public static LexicalShape dataOption(final int dataIndex, LexicalShape shape) {
			return new LSOption(shape) {
				@Override
				public boolean decide(STree tree) {
					return (Boolean) tree.state.data(dataIndex);
				}
			};
		}

		public static LexicalShape nonNullData(final int dataIndex, LexicalShape shape) {
			return new LSOption(shape) {
				@Override
				public boolean decide(STree tree) {
					return tree.state.data(dataIndex) != null;
				}
			};
		}

		public static LexicalShape nonNullChild(final int dataIndex, LexicalShape shape) {
			return new LSOption(shape) {
				@Override
				public boolean decide(STree tree) {
					return ((SNodeState) tree.state).child(dataIndex) != null;
				}
			};
		}

		public static LexicalShape spacing(String spacing) {
			return new LSSpacing(spacing);
		}

		public static LexicalShape composite(LexicalShape... shapes) {
			return new LSComposite(shapes);
		}

		public static LexicalShape token(final LToken token) {
			return new LSToken() {
				@Override
				protected LToken token(STree tree) {
					return token;
				}
			};
		}

		public static LexicalShape child(final int childIndex) {
			return new LSChild() {
				@Override
				public LexicalShape shape(STree tree) {
					return tree.kind.shape();
				}

				@Override
				public STree select(SNode node) {
					return ((SNodeState) node.state).child(childIndex);
				}
			};
		}

		public static LexicalShape child(final int childIndex, final LexicalShape shape) {
			return new LSChild() {
				@Override
				public LexicalShape shape(STree tree) {
					return shape;
				}

				@Override
				public STree select(SNode node) {
					return ((SNodeState) node.state).child(childIndex);
				}
			};
		}
	}
}
