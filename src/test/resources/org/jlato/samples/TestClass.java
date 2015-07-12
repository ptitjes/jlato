package org.jlato.tree;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;

public abstract class TestClass {

	protected final SLocation location;

	protected TestClass(SLocation location) {
		this.location = location;
	}

	public Tree parent() {
		return location.parent();
	}

	public Tree root() {
		return location.root();
	}

	public static STree treeOf(Tree facade) {
		return facade == null ? null : facade.location.tree;
	}

	protected static ArrayList<STree> treesOf(Tree... facades) {
		final Builder<STree, ArrayList<STree>> builder = ArrayList.<STree>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}

	protected static ArrayList<Object> dataOf(Object... attributes) {
		final Builder<Object, ArrayList<Object>> builder = ArrayList.factory().newBuilder();
		for (Object attribute : attributes) {
			builder.add(attribute);
		}
		return builder.build();
	}

	protected static Vector<STree> treeListOf(Tree... facades) {
		final Builder<STree, Vector<STree>> builder = Vector.<STree>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}

	public interface Kind {

		Tree instantiate(SLocation location);

		LexicalShape shape();
	}
}
