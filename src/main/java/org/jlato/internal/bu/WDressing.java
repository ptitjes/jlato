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

/**
 * @author Didier Villevalois
 */
public class WDressing {

	public final WTokenRun leading;
	public final WRunRun run;
	public final WTokenRun trailing;

	public WDressing() {
		this(null, null, null);
	}

	public WDressing(WRunRun run) {
		this(null, run, null);
	}

	public WDressing(WTokenRun leading, WRunRun run, WTokenRun trailing) {
		this.leading = leading;
		this.run = run;
		this.trailing = trailing;
	}

	public WDressing withRun(WRunRun run) {
		return new WDressing(leading, run, trailing);
	}

	public WDressing withLeading(WTokenRun leading) {
		return new WDressing(leading, run, trailing);
	}

	public WDressing withTrailing(WTokenRun trailing) {
		return new WDressing(leading, run, trailing);
	}
}
