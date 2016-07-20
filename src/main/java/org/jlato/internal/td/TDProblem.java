/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.td;

import org.jlato.internal.bu.BUProblem;
import org.jlato.tree.Problem;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class TDProblem implements Problem {

	public TDProblem(BUProblem problem, Tree tree) {
		this.problem = problem;
		this.tree = tree;
	}

	private final BUProblem problem;

	private final Tree tree;

	@Override
	public Severity severity() {
		return problem.severity();
	}

	@Override
	public String code() {
		return problem.code();
	}

	@Override
	public Tree tree() {
		return tree;
	}
}
