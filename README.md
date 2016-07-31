# JLaTo

JLaTo is a Java Language Tools library.

[![Maven Central][shield-maven]][info-maven]
[![JavaDoc][shield-javadoc]][info-javadoc]
[![Documentation][shield-doc]][info-doc]
[![Build Status][shield-build]][info-build]
[![Coverage Status][shield-coverage]][info-coverage]
[![Issues][shield-issues]][info-issues]
[![License][shield-license]][info-license]
[![IRC Invite][shield-irc]][info-irc]

[info-maven]: https://maven-badges.herokuapp.com/maven-central/org.jlato/jlato
[info-javadoc]: http://javadoc.io/doc/org.jlato/jlato
[info-doc]: https://jlato.readthedocs.io/
[info-build]: https://travis-ci.org/ptitjes/jlato
[info-coverage]: https://codecov.io/gh/ptitjes/jlato
[info-issues]: https://github.com/ptitjes/jlato/issues
[info-license]: COPYING.LESSER
[info-irc]: https://webchat.freenode.net/?channels=jlato

[shield-maven]: https://img.shields.io/maven-central/v/org.jlato/jlato.svg
[shield-javadoc]: https://img.shields.io/badge/javadoc-latest-orange.svg
[shield-doc]: https://readthedocs.org/projects/docs/badge/?version=latest
[shield-build]: https://img.shields.io/travis/ptitjes/jlato/master.svg
[shield-coverage]: https://codecov.io/gh/ptitjes/jlato/branch/master/graph/badge.svg
[shield-issues]: https://img.shields.io/github/issues-raw/ptitjes/jlato.svg
[shield-license]: https://img.shields.io/badge/license-LGPL%203.0-blue.svg
[shield-irc]: https://img.shields.io/badge/irc-%23jlato-red.svg

## Features

### Current

JLaTo currently provides:

* Parsing of Java source files up to **Java 1.8** version (that runs with Java 1.6+)
* An **immutable** abstract syntax tree with **fluent mutators**
* **Lexical preservation** of whitespaces and comments
* **Formatting** of new (and already formatted) abstract syntax trees with **formatting settings**
* [Experimental] **Matching/Filtering/Searching** of abstract syntax trees with patterns
* [Experimental] **Quasi-quotes of Java snippets** to ease the building of new abstract syntax trees and patterns

### Roadmap

In the long term, JLaTo also aims to provide:

* Semantic analysis of abstract syntax trees
* A add-on mechanism to enable syntactic and semantic extensions

## Installation

Releases are distributed on Maven central:
```xml
<dependency>
  <groupId>org.jlato</groupId>
  <artifactId>jlato</artifactId>
  <version>0.0.6</version>
</dependency>
```

You can also use [JitPack](https://jitpack.io/#ptitjes/jlato), if ever you require a build for a specific commit:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<!-- ... -->

<dependency>
  <groupId>com.github.ptitjes</groupId>
  <artifactId>jlato</artifactId>
  <version>master-SNAPSHOT</version> <!-- or a commit hash -->
</dependency>
```


## Documentation

You will find the documentation at [readthedocs.io][info-doc] and the latest javadoc on [javadoc.io][info-javadoc].

## License

JLaTo is provided under the GNU Lesser General Public License. See the included [COPYING.LESSER](COPYING.LESSER), [COPYING](COPYING) files.
