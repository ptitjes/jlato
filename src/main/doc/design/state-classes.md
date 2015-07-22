# State Classes

The so-called State classes are the bottom-up-constructed classes of the abstract syntax tree that back up the
top-down-constructed Tree facade classes. They all inherit the `STreeState` interface and are wrapped inside `STree`
instances which convey additional data for these nodes such as lexical information, semantic attributes, and user data.

They obey the following general contract:

## General Contract

State objects contain child `STree` instances and additional primitive (or `String`) data.

In a fully operational state, State objects can't carry `null` values neither for their child trees nor for their data.
Optional child trees must be wrapped into **option trees** (`STree<SNodeOptionState>`), lists of children trees must be
wrapped into **list trees** (`STree<SNodeListState>`) and alternative child trees must be wrapped into **either trees**
(`STree<SNodeEitherState>`).

State objects can thus be either pending initialization or fully initialized.

### Pending Initialization State

When first instantiated, a State object may have null values for its non-compound child trees (those that are not
options, lists or either trees). Their `equals(...)` and `hashCode()` methods may be called while pending initialization
and, thus, must be null-proofed for those fields.

The compound child trees (those that are options, lists or either trees) must be fully initialized at construction time.
The State constructor must then include individual nullity tests and instantiate new `STree<SNodeOptionState>`,
`STree<SNodeListState>` or `STree<SNodeEitherState>` accordingly.

### Fully Initialized State

The following actions will blindly query the State objects and therefore consider them to be fully initialized:

- Tree traversals
- Pattern matching and building
- Rendering with the help of the corresponding shape

The State objects must ensure non-nullity of their non-compound child trees as their first validation action in their
`STreeState.validate()` implementation.
