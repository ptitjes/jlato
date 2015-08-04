package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDFormalParameter;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.type.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SFormalParameter extends SNode<SFormalParameter> implements STree {

	public static BUTree<SFormalParameter> make(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, boolean isVarArgs, BUTree<SVariableDeclaratorId> id) {
		return new BUTree<SFormalParameter>(new SFormalParameter(modifiers, type, isVarArgs, id));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<? extends SType> type;

	public final boolean isVarArgs;

	public final BUTree<SVariableDeclaratorId> id;

	public SFormalParameter(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, boolean isVarArgs, BUTree<SVariableDeclaratorId> id) {
		this.modifiers = modifiers;
		this.type = type;
		this.isVarArgs = isVarArgs;
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.FormalParameter;
	}

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SFormalParameter withModifiers(BUTree<SNodeList> modifiers) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	public BUTree<? extends SType> type() {
		return type;
	}

	public SFormalParameter withType(BUTree<? extends SType> type) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	public boolean isVarArgs() {
		return isVarArgs;
	}

	public SFormalParameter setVarArgs(boolean isVarArgs) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	public BUTree<SVariableDeclaratorId> id() {
		return id;
	}

	public SFormalParameter withId(BUTree<SVariableDeclaratorId> id) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SFormalParameter> location) {
		return new TDFormalParameter(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(VAR_ARGS);
	}

	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	@Override
	public STraversal lastChild() {
		return ID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SFormalParameter state = (SFormalParameter) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (isVarArgs != state.isVarArgs)
			return false;
		if (id == null ? state.id != null : !id.equals(state.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		result = 37 * result + (isVarArgs ? 1 : 0);
		if (id != null) result = 37 * result + id.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SFormalParameter, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SFormalParameter, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.modifiers;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SNodeList> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SFormalParameter, SType, Type> TYPE = new STypeSafeTraversal<SFormalParameter, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.type;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ID;
		}
	};

	public static STypeSafeTraversal<SFormalParameter, SVariableDeclaratorId, VariableDeclaratorId> ID = new STypeSafeTraversal<SFormalParameter, SVariableDeclaratorId, VariableDeclaratorId>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.id;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SVariableDeclaratorId> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static STypeSafeProperty<SFormalParameter, Boolean> VAR_ARGS = new STypeSafeProperty<SFormalParameter, Boolean>() {

		@Override
		public Boolean doRetrieve(SFormalParameter state) {
			return state.isVarArgs;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, Boolean value) {
			return state.setVarArgs(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.singleLineShape),
			child(TYPE),
			when(data(VAR_ARGS), token(LToken.Ellipsis)),
			when(not(childIs(TYPE, withKind(Kind.UnknownType))), none().withSpacingAfter(space())),
			child(ID)
	);

	public static final LexicalShape listShape = list(true,
			none(),
			token(LToken.Comma).withSpacingAfter(space()),
			none()
	);
}
