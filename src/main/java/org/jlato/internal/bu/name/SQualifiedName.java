package org.jlato.internal.bu.name;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.name.TDQualifiedName;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SQualifiedName extends SNodeState<SQualifiedName> implements STreeState {

	public static STree<SQualifiedName> make(STree<SNodeOptionState> qualifier, STree<SName> name) {
		return new STree<SQualifiedName>(new SQualifiedName(qualifier, name));
	}

	public final STree<SNodeOptionState> qualifier;

	public final STree<SName> name;

	public SQualifiedName(STree<SNodeOptionState> qualifier, STree<SName> name) {
		this.qualifier = qualifier;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.QualifiedName;
	}

	public STree<SNodeOptionState> qualifier() {
		return qualifier;
	}

	public SQualifiedName withQualifier(STree<SNodeOptionState> qualifier) {
		return new SQualifiedName(qualifier, name);
	}

	public STree<SName> name() {
		return name;
	}

	public SQualifiedName withName(STree<SName> name) {
		return new SQualifiedName(qualifier, name);
	}

	@Override
	protected Tree doInstantiate(SLocation<SQualifiedName> location) {
		return new TDQualifiedName(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return QUALIFIER;
	}

	@Override
	public STraversal lastChild() {
		return NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SQualifiedName state = (SQualifiedName) o;
		if (!qualifier.equals(state.qualifier))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + qualifier.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SQualifiedName, SNodeOptionState, NodeOption<QualifiedName>> QUALIFIER = new STypeSafeTraversal<SQualifiedName, SNodeOptionState, NodeOption<QualifiedName>>() {

		@Override
		public STree<?> doTraverse(SQualifiedName state) {
			return state.qualifier;
		}

		@Override
		public SQualifiedName doRebuildParentState(SQualifiedName state, STree<SNodeOptionState> child) {
			return state.withQualifier(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SQualifiedName, SName, Name> NAME = new STypeSafeTraversal<SQualifiedName, SName, Name>() {

		@Override
		public STree<?> doTraverse(SQualifiedName state) {
			return state.name;
		}

		@Override
		public SQualifiedName doRebuildParentState(SQualifiedName state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return QUALIFIER;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(QUALIFIER, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
