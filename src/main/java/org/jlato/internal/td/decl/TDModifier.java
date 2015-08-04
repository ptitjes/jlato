package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SModifier;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.Modifier;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.util.Mutation;

public class TDModifier extends TDTree<SModifier, ExtendedModifier, Modifier> implements Modifier {

	public Kind kind() {
		return Kind.Modifier;
	}

	public TDModifier(SLocation<SModifier> location) {
		super(location);
	}

	public TDModifier(ModifierKeyword keyword) {
		super(new SLocation<SModifier>(SModifier.make(keyword)));
	}

	public ModifierKeyword keyword() {
		return location.safeProperty(SModifier.KEYWORD);
	}

	public Modifier withKeyword(ModifierKeyword keyword) {
		return location.safePropertyReplace(SModifier.KEYWORD, keyword);
	}

	public Modifier withKeyword(Mutation<ModifierKeyword> mutation) {
		return location.safePropertyMutate(SModifier.KEYWORD, mutation);
	}
}
