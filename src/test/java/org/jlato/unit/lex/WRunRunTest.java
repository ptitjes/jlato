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

package org.jlato.unit.lex;

import com.github.andrewoma.dexx.collection.ArrayList;
import org.jlato.internal.bu.WRun;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WToken;
import org.jlato.internal.bu.WTokenRun;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class WRunRunTest {

	WRunRun base = new WRunRun(
			ArrayList.<WRun>empty()
					.append(WTokenRun.EMPTY.append(WToken.whitespace(" ")))
					.append(null)
					.append(WTokenRun.EMPTY.append(WToken.whitespace(" ")))
	);

	@Test
	public void insert() {
		Assert.assertEquals("[[ ],null,null,null,[ ]]", base.insert(1, 2).toString());
	}

	@Test
	public void insertDelete() {
		Assert.assertEquals("[[ ],null,[ ]]", base.insert(1, 2).delete(1, 2).toString());
	}

	@Test(expected=IllegalArgumentException.class)
	public void insertInvalidPositionBefore() {
		base.insert(0, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void deleteInvalidPositionBefore() {
		base.delete(0, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void insertInvalidPositionAfter() {
		base.insert(3, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void deleteInvalidPositionAfter() {
		base.delete(3, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void insertInvalidCount() {
		base.insert(1, 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void insertDeleteInvalidCount() {
		base.delete(1, 0);
	}
}
