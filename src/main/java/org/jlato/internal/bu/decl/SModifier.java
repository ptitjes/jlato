package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDModifier;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.token;

public class SModifier extends SNode<SModifier> implements SExtendedModifier {

	public static BUTree<SModifier> make(ModifierKeyword keyword) {
		return new BUTree<SModifier>(new SModifier(keyword));
	}

	public final ModifierKeyword keyword;

	public SModifier(ModifierKeyword keyword) {
		this.keyword = keyword;
	}

	@Override
	public Kind kind() {
		return Kind.Modifier;
	}

	public ModifierKeyword keyword() {
		return keyword;
	}

	public SModifier withKeyword(ModifierKeyword keyword) {
		return new SModifier(keyword);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SModifier> location) {
		return new TDModifier(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(KEYWORD);
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
		SModifier state = (SModifier) o;
		if (keyword == null ? state.keyword != null : !keyword.equals(state.keyword))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (keyword != null) result = 37 * result + keyword.hashCode();
		return result;
	}

	public static STypeSafeProperty<SModifier, ModifierKeyword> KEYWORD = new STypeSafeProperty<SModifier, ModifierKeyword>() {

		@Override
		public ModifierKeyword doRetrieve(SModifier state) {
			return state.keyword;
		}

		@Override
		public SModifier doRebuildParentState(SModifier state, ModifierKeyword value) {
			return state.withKeyword(value);
		}
	};

	public static final LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(BUTree tree) {
			final ModifierKeyword keyword = ((SModifier) tree.state).keyword;
			switch (keyword) {
				case Public:
					return LToken.Public;
				case Protected:
					return LToken.Protected;
				case Private:
					return LToken.Private;
				case Abstract:
					return LToken.Abstract;
				case Default:
					return LToken.Default;
				case Static:
					return LToken.Static;
				case Final:
					return LToken.Final;
				case Transient:
					return LToken.Transient;
				case Volatile:
					return LToken.Volatile;
				case Synchronized:
					return LToken.Synchronized;
				case Native:
					return LToken.Native;
				case StrictFP:
					return LToken.StrictFP;
				default:
					// Can't happen by definition of enum
					throw new IllegalStateException();
			}
		}
	});
}
