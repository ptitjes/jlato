package org.jlato.tree;

import org.jlato.printer.Printer;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class BasicTest {

	@Test
	public void testPrinter() {
		final ImportDecl importDecl = new ImportDecl(QName.of("org.jlato"), false, true);

		System.out.println(Printer.printToString(importDecl));
	}
}
