package org.jlato.internal.bu.name;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.name.TDName;
import org.jlato.parser.ParserImplConstants;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SName extends SNodeState<SName> implements SExpr {

	public static STree<SName> make(String id) {
		return new STree<SName>(new SName(id));
	}

	public final String id;

	public SName(String id) {
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.Name;
	}

	public String id() {
		return id;
	}

	public SName withId(String id) {
		return new SName(id);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SName> location) {
		return new TDName(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(ID);
	}

	@Override
	public STraversal firstChild() {
		return null;
	}

	@Override
	public STraversal lastChild() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SName state = (SName) o;
		if (id == null ? state.id != null : !id.equals(state.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (id != null) result = 37 * result + id.hashCode();
		return result;
	}

	public static STypeSafeProperty<SName, String> ID = new STypeSafeProperty<SName, String>() {

		@Override
		public String doRetrieve(SName state) {
			return state.id;
		}

		@Override
		public SName doRebuildParentState(SName state, String value) {
			return state.withId(value);
		}
	};

	public static final LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return new LToken(ParserImplConstants.IDENTIFIER, ((SName) tree.state).id);
		}
	});
}
