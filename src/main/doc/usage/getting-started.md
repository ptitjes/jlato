# Getting Started


## Parsing

### Basics

You first have to create a `Parser` object:

```java
final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(preserveWhitespaces));
```

Then parsing is just a matter of calling the `parse(...)` method with the appropriate reader, file or input-stream and encoding.

```java
final File sourceFile = new File("<path-to-java-source-file>.java");
final CompilationUnit cu = parser.parse(sourceFile, "UTF-8");
```

### Customization

A `Parser` can be instantiated with a `ParserConfiguration` object. Through this configuration object, you can enable
whitespace preservation.

```java
final Parser parser = new Parser(
		ParserConfiguration.Default.preserveWhitespaces(preserveWhitespaces));
```

The `Parser` class also provide a finer-grain `parse(...)` method that accept a `ParseContext` object. For instance, you
can parse an expression:

```java
final String exprString = "x -> x + x";
final Expr expr = parser.parse(ParseContext.Expression, exprString);
```


## Printing

### Basics

You have to create a `Printer` object and call its `print(...)` method:

```java
final CompilationUnit cu = // ...
PrintWriter writer = new PrintWriter(new FileWriter("out.java"));
Printer printer = new Printer(writer);
printer.print(cu);
```

The `Printer` class also propose static helper methods:

```java
String formatted = Printer.printToString(tree);
```

### Customization

A `Printer` can be instantiated with an additional boolean flag to enable formatting of existing nodes and a
`FormattingSetting` object to customize rendering:

```java
// ...
Printer printer = new Printer(writer, true, FormattingSettings.Default.withIndentation("    "));
printer.print(cu);
```

Two base settings are provided (`FormattingSettings.Default` and `FormattingSettings.JavaParser`). Please refer to the
JavaDoc of `FormattingSettings` for further customization.


## Abstract Syntax Tree Manipulation

Every abstract syntax tree objects derive from the base `Tree` class and obeys common design rules.

For each property `prop` of a `Tree` subclass is provided an accessor `prop()` and a mutator `withProp(...)`. For
instance, the `ImportDecl` class provide the following accessor and mutator for its properties:

```java
public QualifiedName name();
public ImportDecl withName(QualifiedName name);
public boolean isStatic();
public ImportDecl setStatic(boolean isStatic);
public boolean isOnDemand();
public ImportDecl setOnDemand(boolean isOnDemand);
```

Thus you can modify an `ImportDecl` in the following way:

```java
final ImportDecl decl = new ImportDecl(QualifiedName.of("org.jlato.tree"), false, true);
Assert.assertEquals("import org.jlato.tree.*;", Printer.printToString(decl));

final ImportDecl newDecl = decl.withName(new QualifiedName(decl.name(), "Tree"))
                               .setOnDemand(false);
Assert.assertEquals("import org.jlato.tree.Tree;", Printer.printToString(newDecl));
```

Once you modified a tree node, you can go back to its parent tree or up to the root by calling the `parent()` or
`root()` methods. Those will accordingly recreate the intermediate immutable tree nodes:

```java
final CompilationUnit cu = // ...

final ImportDecl anImportDecl = cu.imports().get(0);

final ImportDecl newImportDecl = anImportDecl.withName(QualifiedName.of("com.acme.MyClass"))
                                             .setStatic(false)
                                             .setOnDemand(false);

final CompilationUnit newCU = (CompilationUnit) newImportDecl.root();
Assert.assertEquals("import com.acme.MyClass;", Printer.printToString(newCU.imports().get(0));
```
