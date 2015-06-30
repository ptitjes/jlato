package org.jlato.tree;

/**
 * @author Didier Villevalois
 */
public abstract class Stmt extends Tree implements Modifiable<Stmt> {

	protected Stmt(SLocation location) {
		super(location);
	}

	public <S extends Stmt> Stmt replace(S replacement) {
		return location.replace(replacement);
	}
}
