/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu.coll;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.coll.TDNodeOption;
import org.jlato.tree.*;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SNodeOption implements STree {

	public final BUTree<?> element;

	public SNodeOption(BUTree<?> element) {
		this.element = element;
	}

	@Override
	public STreeKind treeKind() {
		return STreeKind.NodeOption;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(TDLocation<?> location) {
		return new TDNodeOption<Tree>((TDLocation<SNodeOption>) location);
	}

	@Override
	public LexicalShape shape() {
		throw new UnsupportedOperationException();
	}

	public static ElementTraversal elementTraversal() {
		return new ElementTraversal();
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal firstChild() {
		return elementTraversal();
	}

	@Override
	public STraversal lastChild() {
		return elementTraversal();
	}

	public SNodeOption withElement(BUTree<?> element) {
		return new SNodeOption(element);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SNodeOption that = (SNodeOption) o;

		return !(element != null ? !element.equals(that.element) : that.element != null);

	}

	@Override
	public int hashCode() {
		return element != null ? element.hashCode() : 0;
	}

	@Override
	public void validate(BUTree<?> tree) {
		if (element != null) element.validate();
	}


	public static class ElementTraversal extends STypeSafeTraversal<SNodeOption, STree, Tree> {

		public ElementTraversal() {
		}

		@Override
		public BUTree<?> doTraverse(SNodeOption state) {
			return state.element;
		}

		@Override
		public SNodeOption doRebuildParentState(SNodeOption state, BUTree<STree> child) {
			return state.withElement(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	}
}
