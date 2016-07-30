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

package org.jlato.internal.bu;

import org.jlato.tree.Problem;

/**
 * @author Didier Villevalois
 */
public class BUProblem {

	public BUProblem(Problem.Severity severity, String code) {
		this.severity = severity;
		this.code = code;
	}

	private final Problem.Severity severity;

	private final String code;

	public Problem.Severity severity() {
		return severity;
	}

	public String code() {
		return code;
	}
}
