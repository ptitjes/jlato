package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;

public class ImportDecl extends Tree {

	public final static Kind kind = new Kind() {
		public ImportDecl instantiate(SLocation location) {
			return new ImportDecl(location);
		}
	};

	private ImportDecl(SLocation location) {
		super(location);
	}

	public ImportDecl(Expr name, boolean isStatic, boolean isOnDemand) {
		super(new SLocation(new SNode(kind, runOf(modifiers, type, new NodeOption<VarArgMarker>(isVarArgs ? new VarArgMarker() : null), id))));
	}

	public Expr name() {
		return location.nodeChild(NAME);
	}

	public ImportDecl withName(Expr name) {
		return location.nodeWithChild(NAME, type);
	}

	public boolean isStatic() {
		return ((NodeOption<VarArgMarker>) location.nodeChild(VAR_ARG_MARKER)).isDefined();
	}

	public ImportDecl setStatic(boolean isStatic) {
		return location.nodeWithChild(VAR_ARG_MARKER, new NodeOption<VarArgMarker>(isVarArgs ? new VarArgMarker() : null));
	}

	public VariableDeclaratorId id() {
		return location.nodeChild(ID);
	}

	public ImportDecl withId(VariableDeclaratorId id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
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
