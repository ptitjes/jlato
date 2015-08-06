package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SModifier;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.Modifier;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.util.Mutation;

/**
 * A modifier.
 */
public class TDModifier extends TDTree<SModifier, ExtendedModifier, Modifier> implements Modifier {

	/**
	 * Returns the kind of this modifier.
	 *
	 * @return the kind of this modifier.
	 */
	public Kind kind() {
		return Kind.Modifier;
	}

	/**
	 * Creates a modifier for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDModifier(TDLocation<SModifier> location) {
		super(location);
	}

	/**
	 * Creates a modifier with the specified child trees.
	 *
	 * @param keyword the keyword child tree.
	 */
	public TDModifier(ModifierKeyword keyword) {
		super(new TDLocation<SModifier>(SModifier.make(keyword)));
	}

	/**
	 * Returns the keyword of this modifier.
	 *
	 * @return the keyword of this modifier.
	 */
	public ModifierKeyword keyword() {
		return location.safeProperty(SModifier.KEYWORD);
	}

	/**
	 * Replaces the keyword of this modifier.
	 *
	 * @param keyword the replacement for the keyword of this modifier.
	 * @return the resulting mutated modifier.
	 */
	public Modifier withKeyword(ModifierKeyword keyword) {
		return location.safePropertyReplace(SModifier.KEYWORD, keyword);
	}

	/**
	 * Mutates the keyword of this modifier.
	 *
	 * @param mutation the mutation to apply to the keyword of this modifier.
	 * @return the resulting mutated modifier.
	 */
	public Modifier withKeyword(Mutation<ModifierKeyword> mutation) {
		return location.safePropertyMutate(SModifier.KEYWORD, mutation);
	}
}
