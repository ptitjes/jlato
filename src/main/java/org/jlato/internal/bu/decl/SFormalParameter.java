package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.decl.TDFormalParameter;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.type.Type;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SFormalParameter extends SNodeState<SFormalParameter> implements STreeState {

	public static STree<SFormalParameter> make(STree<SNodeListState> modifiers, STree<? extends SType> type, boolean isVarArgs, STree<SVariableDeclaratorId> id) {
		return new STree<SFormalParameter>(new SFormalParameter(modifiers, type, isVarArgs, id));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<? extends SType> type;

	public final boolean isVarArgs;

	public final STree<SVariableDeclaratorId> id;

	public SFormalParameter(STree<SNodeListState> modifiers, STree<? extends SType> type, boolean isVarArgs, STree<SVariableDeclaratorId> id) {
		this.modifiers = modifiers;
		this.type = type;
		this.isVarArgs = isVarArgs;
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.FormalParameter;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SFormalParameter withModifiers(STree<SNodeListState> modifiers) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SFormalParameter withType(STree<? extends SType> type) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	public boolean isVarArgs() {
		return isVarArgs;
	}

	public SFormalParameter setVarArgs(boolean isVarArgs) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	public STree<SVariableDeclaratorId> id() {
		return id;
	}

	public SFormalParameter withId(STree<SVariableDeclaratorId> id) {
		return new SFormalParameter(modifiers, type, isVarArgs, id);
	}

	@Override
	protected Tree doInstantiate(SLocation<SFormalParameter> location) {
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

	public static STypeSafeTraversal<SFormalParameter, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SFormalParameter, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(SFormalParameter state) {
			return state.modifiers;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, STree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SFormalParameter, SType, Type> TYPE = new STypeSafeTraversal<SFormalParameter, SType, Type>() {

		@Override
		public STree<?> doTraverse(SFormalParameter state) {
			return state.type;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, STree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ID;
		}
	};

	public static STypeSafeTraversal<SFormalParameter, SVariableDeclaratorId, VariableDeclaratorId> ID = new STypeSafeTraversal<SFormalParameter, SVariableDeclaratorId, VariableDeclaratorId>() {

		@Override
		public STree<?> doTraverse(SFormalParameter state) {
			return state.id;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, STree<SVariableDeclaratorId> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
