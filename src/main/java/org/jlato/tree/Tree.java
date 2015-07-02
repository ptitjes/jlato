package org.jlato.tree;

import org.jlato.internal.bu.*;

/**
 * @author Didier Villevalois
 */
public abstract class Tree {

	protected final SLocation location;

	protected Tree(SLocation location) {
		this.location = location;
	}

	public Tree parent() {
		return location.parent();
	}

	public Tree root() {
		return location.root();
	}

	protected static STree treeOf(Tree facade) {
		return facade == null ? null : facade.location.tree;
	}

	protected static LRun runOf(Tree... facades) {
		LRun.RunBuilder builder = LRun.RunBuilder.withFixedArity();
		for (Tree facade : facades) {
			builder.addTree(treeOf(facade));
		}
		return builder.build();
	}

	public interface Kind {

		Tree instantiate(SLocation location);
	}

	protected abstract static class SContext {

		public abstract SLocation original();

		public abstract SLocation rebuiltWith(STree content);

		public static class Root extends SContext {

			@Override
			public SLocation original() {
				return null;
			}

			@Override
			public SLocation rebuiltWith(STree content) {
				return null;
			}
		}

		public static class Child extends SContext {

			private final SLocation parent;
			private final int index;

			public Child(SLocation parent, int index) {
				this.parent = parent;
				this.index = index;
			}

			@Override
			public SLocation original() {
				return parent;
			}

			@Override
			public SLocation rebuiltWith(STree content) {
				SNode node = (SNode) parent.tree;
				SNode newNode = node.with(node.run.set(index, content));
				return parent.withTree(newNode);
			}
		}
	}

	protected static class SLocation {

		protected final SContext context;
		protected final STree tree;
		private final boolean changed;
		protected final Tree facade;

		public SLocation(STree tree) {
			this(new SContext.Root(), tree, false);
		}

		public SLocation(SContext context, STree tree) {
			this(context, tree, false);
		}

		public SLocation(SContext context, STree tree, boolean changed) {
			this.context = context;
			this.tree = tree;
			this.changed = changed;
			this.facade = tree.kind.instantiate(this);
		}

		public SLocation withTree(STree newTree) {
			return newTree == tree ? this : new SLocation(context, newTree, true);
		}

		public Tree parent() {
			SLocation parentLocation = changed ? context.rebuiltWith(tree) : context.original();
			return parentLocation == null ? null : parentLocation.facade;
		}

		public Tree root() {
			Tree parent = parent();
			return parent == null ? facade : parent.root();
		}

		@SuppressWarnings("unchecked")
		public <S extends Tree, T extends S> T replace(T replacement) {
			STree newTree = treeOf(replacement);
			return (T) withTree(newTree).facade;
		}

		@SuppressWarnings("unchecked")
		public <C extends Tree> C nodeChild(final int index) {
			STree childTree = ((SNode) tree).run.tree(index);
			if (childTree == null) return null;

			SContext childContext = new SContext.Child(this, index);
			SLocation childLocation = new SLocation(childContext, childTree);
			return (C) childLocation.facade;
		}

		@SuppressWarnings("unchecked")
		public <T extends Tree, C extends Tree> T nodeWithChild(int index, C newChild) {
			SNode node = (SNode) this.tree;
			SNode newNode = node.with(node.run.set(index, treeOf(newChild)));
			return (T) withTree(newNode).facade;
		}

		public LRun nodeRun() {
			SNode node = (SNode) this.tree;
			return node.run;
		}

		@SuppressWarnings("unchecked")
		public <T extends Tree, C extends Tree> T nodeWithRun(LRun newRun) {
			SNode node = (SNode) this.tree;
			SNode newNode = node.with(newRun);
			return (T) withTree(newNode).facade;
		}

		public LToken leafToken() {
			SLeaf leaf = (SLeaf) this.tree;
			return leaf.token;
		}

		@SuppressWarnings("unchecked")
		public <T extends Tree> T leafWithToken(LToken newToken) {
			SLeaf leaf = (SLeaf) this.tree;
			SLeaf newLeaf = leaf.with(newToken);
			return (T) withTree(newLeaf).facade;
		}
	}
}
