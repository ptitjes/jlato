# JLaTo

JLaTo is a Java Language Tools library.

[![Build Status](https://travis-ci.org/ptitjes/jlato.png)](https://travis-ci.org/ptitjes/jlato)

## Features

### Current

JLaTo currently provides:

* Parsing of Java source files up to **Java 1.8** version (that runs with Java 1.6+)
* An **immutable** abstract syntax tree with fluent mutators
* Lexical preservation of whitespaces and comments
* Formatting of new (and already formatted) abstract syntax trees with formatting settings

### Roadmap

In the long term, JLaTo also aims to provide:

* Semantic analysis of abstract syntax trees
* A add-on mechanism to enable syntactic and semantic extensions
* Matching/Filtering/Searching of abstract syntax trees with patterns
* Quasi-quotes of Java snippets to ease the building of new abstract syntax trees and patterns

## Documentation

JLaTo is still in heavy development. Documentation will be gathered along the way in the [Documentation Directory](src/main/doc/).

For now, we provide [basic usage instructions](src/main/doc/usage/getting-started.md).

## License

JLaTo is provided under the GNU Lesser General Public License. See the included [COPYING.LESSER](https://github.com/javaparser/javaparser/blob/master/COPYING.LESSER), [COPYING](https://github.com/javaparser/javaparser/blob/master/COPYING) files.
