package org.jlato;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class ImportAST {

	public static void main(String[] args) throws IOException, ParseException {

		File sourceAST = new File("../java/javaparser/master/javaparser-core/src/main/java/com/github/javaparser/ast/");
		File destAST = new File("src/main/java/org/jlato/tree/");

		generateTrees(sourceAST, destAST, "expr", "expr", "Expr");
	}

	private static void generateTrees(File sourceAST, File destAST,
	                                  String sourceDir, String destDir,
	                                  String baseClass) throws IOException, ParseException {

		for (File sourceFile : listJavaFiles(new File(sourceAST, sourceDir))) {
			CompilationUnit result = JavaParser.parse(sourceFile);

			TypeDeclaration declaration = result.getTypes().get(0);
			String nodeName = declaration.getName();

			List<Field> fields = new ArrayList<Field>();
			for (BodyDeclaration member : declaration.getMembers()) {
				if (!(member instanceof FieldDeclaration)) continue;

				FieldDeclaration fieldDecl = (FieldDeclaration) member;
				Field field = new Field();
				field.type = ((FieldDeclaration) member).getType().toString();
				field.name = fieldDecl.getVariables().get(0).getId().getName();
				fields.add(field);
			}

			// Generate AST node

			File destFile = new File(destAST, destDir + "/" + nodeName + ".java");
			SourcePrinter out = new SourcePrinter(new PrintWriter(destFile, "UTF-8"));

			out.printLn("package org.jlato.tree." + destDir + ";");
			out.printLn();

			out.printLn("import org.jlato.internal.bu.*;");
			out.printLn("import org.jlato.tree.*;");
			out.printLn();

			out.printLn("public class " + nodeName + " extends " + baseClass + "<" + nodeName + "> {");
			out.indent();

			out.printLn("private " + nodeName + "(SContext context, STree<" + nodeName + "> content) {");
			out.indent();
			out.printLn("super(context, content);");
			out.unindent();
			out.printLn("}");

			out.print("public " + nodeName + "(");
			{
				boolean notFirst = false;
				for (Field field : fields) {
					if (notFirst) out.print(", ");
					else notFirst = true;
					out.print(field.type + " " + field.name);
				}
			}
			out.printLn(") {");
			out.indent();
			out.print("super(null, new SNode<" + nodeName + ">(type, null, treesOf(");
			{
				boolean notFirst = false;
				for (Field field : fields) {
					if (notFirst) out.print(", ");
					else notFirst = true;
					out.print(field.name);
				}
			}
			out.printLn("), null));");
			out.unindent();
			out.printLn("}");
			out.printLn();

			{
				int index = 0;
				for (Field field : fields) {
					out.printLn("public " + field.type + " " + field.name + "() {");
					out.indent();
					out.printLn("return child(" + constantName(field.name) + ");");
					out.unindent();
					out.printLn("}");
					out.printLn();

					out.printLn("public " + nodeName + " with" + upperCaseFirst(field.name) + "(" + field.type + " " + field.name + ") {");
					out.indent();
					out.printLn("return with(" + constantName(field.name) + ", " + field.name + ");");
					out.unindent();
					out.printLn("}");
					out.printLn();

					index++;
				}
			}
			out.printLn();

			{
				int index = 0;
				for (Field field : fields) {
					out.printLn("private static final int " + constantName(field.name) + " = " + index + ";");
					index++;
				}
			}
			out.printLn();

			out.printLn("public final static Expr.Type<" + nodeName + "> type = new Expr.Type<" + nodeName + ">() {");
			out.indent();
			out.printLn("protected " + nodeName + " instantiateNode(SContext context, STree<" + nodeName + "> content) {");
			out.indent();
			out.printLn("return new " + nodeName + "(context, content);");
			out.unindent();
			out.printLn("}");
			out.unindent();
			out.printLn("};");

			out.unindent();
			out.printLn("}");
			out.close();
		}
	}

	private static String upperCaseFirst(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private static String constantName(String name) {
		StringBuilder buffer = new StringBuilder();
		for (char c : name.toCharArray()) {
			if (Character.isUpperCase(c)) {
				buffer.append('_');
			}
			buffer.append(c);
		}
		return buffer.toString().toUpperCase();
	}

	public static class Field {
		public String type;
		public String name;
	}

	private static List<File> listJavaFiles(File directory) {
		return Arrays.asList(directory.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".java");
			}
		}));
	}

	private static class SourcePrinter {

		public SourcePrinter(PrintWriter out) {
			this.out = out;
		}

		private PrintWriter out;

		private int level = 0;

		private boolean indented = false;

		public void indent() {
			level++;
		}

		public void unindent() {
			level--;
		}

		private void makeIndent() {
			for (int i = 0; i < level; i++) {
				out.append("\t");
			}
		}

		public void print(final String arg) {
			if (!indented) {
				makeIndent();
				indented = true;
			}
			out.append(arg);
		}

		public void printLn(final String arg) {
			print(arg);
			printLn();
		}

		public void printLn() {
			out.append("\n");
			indented = false;
		}

		public void close() {
			out.close();
		}
	}
}
