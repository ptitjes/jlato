package org.jlato.tree;

/**
 * @author Didier Villevalois
 */
public abstract class Expr extends Tree implements Modifiable<Expr> {

	protected Expr(SLocation location) {
		super(location);
	}

	public <S extends Expr> Expr replace(S replacement) {
		return location.replace(replacement);
	}
}
