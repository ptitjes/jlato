package org.jlato.tree;

/**
 * @author Didier Villevalois
 */
public interface Modifiable<T> {

	<S extends T> T replace(S replacement);
/*
	<S extends T, U extends T> T modify(ModifyFunc<T, S, U> f);

	interface ModifyFunc<T, S extends T, U extends T> {
		U apply(S original);
	}
*/
}
