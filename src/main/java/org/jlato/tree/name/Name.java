package org.jlato.tree.name;

import com.github.javaparser.ASTParserConstants;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class Name extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public Name instantiate(SLocation location) {
			return new Name(location);
		}
	};

	private Name(SLocation location) {
		super(location);
	}

	public Name(LToken identifier) {
		super(new SLocation(new SLeaf(kind, identifier)));
	}

	public Name(String name) {
		this(new LToken(ASTParserConstants.IDENTIFIER, name));
	}

	public String name() {
		return location.leafToken().toString();
	}

	public Name withName(String name) {
		return location.leafWithToken(new LToken(ASTParserConstants.IDENTIFIER, name));
	}

	@Override
	public String toString() {
		return name();
	}
}
