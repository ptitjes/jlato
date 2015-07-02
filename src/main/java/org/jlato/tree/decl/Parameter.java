package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

public class Parameter extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public Parameter instantiate(SLocation location) {
			return new Parameter(location);
		}
	};

	private Parameter(SLocation location) {
		super(location);
	}

	public Parameter(Modifiers modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(new SLocation(new SNode(kind, runOf(modifiers, type, new NodeOption<VarArgMarker>(isVarArgs ? new VarArgMarker() : null), id))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public Parameter withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public Parameter withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public boolean isVarArgs() {
		return ((NodeOption<VarArgMarker>) location.nodeChild(VAR_ARG_MARKER)).isDefined();
	}

	public Parameter setVarArgs(boolean isVarArgs) {
		return location.nodeWithChild(VAR_ARG_MARKER, new NodeOption<VarArgMarker>(isVarArgs ? new VarArgMarker() : null));
	}

	public VariableDeclaratorId id() {
		return location.nodeChild(ID);
	}

	public Parameter withId(VariableDeclaratorId id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int VAR_ARG_MARKER = 2;
	private static final int ID = 3;

	private static class VarArgMarker extends Tree {

		public final static Kind kind = new Kind() {
			public Tree instantiate(SLocation location) {
				return new VarArgMarker(location);
			}
		};

		protected VarArgMarker(SLocation location) {
			super(location);
		}

		private VarArgMarker() {
			super(new SLocation(new SLeaf(kind, LToken.Ellipsis)));
		}

		public String toString() {
			return location.leafToken().toString();
		}
	}
}
