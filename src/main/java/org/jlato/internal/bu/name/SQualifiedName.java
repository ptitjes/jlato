package org.jlato.internal.bu.name;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.name.TDQualifiedName;
import org.jlato.tree.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SQualifiedName extends SNode<SQualifiedName> implements STree {

	public static BUTree<SQualifiedName> make(BUTree<SNodeOption> qualifier, BUTree<SName> name) {
		return new BUTree<SQualifiedName>(new SQualifiedName(qualifier, name));
	}

	public final BUTree<SNodeOption> qualifier;

	public final BUTree<SName> name;

	public SQualifiedName(BUTree<SNodeOption> qualifier, BUTree<SName> name) {
		this.qualifier = qualifier;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.QualifiedName;
	}

	public SQualifiedName withQualifier(BUTree<SNodeOption> qualifier) {
		return new SQualifiedName(qualifier, name);
	}

	public SQualifiedName withName(BUTree<SName> name) {
		return new SQualifiedName(qualifier, name);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SQualifiedName> location) {
		return new TDQualifiedName(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return QUALIFIER;
	}

	@Override
	public STraversal lastChild() {
		return NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SQualifiedName state = (SQualifiedName) o;
		if (!qualifier.equals(state.qualifier))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + qualifier.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SQualifiedName, SNodeOption, NodeOption<QualifiedName>> QUALIFIER = new STypeSafeTraversal<SQualifiedName, SNodeOption, NodeOption<QualifiedName>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedName state) {
			return state.qualifier;
		}

		@Override
		public SQualifiedName doRebuildParentState(SQualifiedName state, BUTree<SNodeOption> child) {
			return state.withQualifier(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SQualifiedName, SName, Name> NAME = new STypeSafeTraversal<SQualifiedName, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedName state) {
			return state.name;
		}

		@Override
		public SQualifiedName doRebuildParentState(SQualifiedName state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return QUALIFIER;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(QUALIFIER, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
