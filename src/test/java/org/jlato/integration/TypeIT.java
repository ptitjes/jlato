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

package org.jlato.integration;

import org.jlato.integration.utils.BulkTestRunner;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.tree.type.Type;
import org.junit.runner.RunWith;

/**
 * @author Didier Villevalois
 */
@RunWith(BulkTestRunner.class)
public class TypeIT extends ChunkIntegrationTest<Type> {

	public String testResourcesPath() {
		return "org/jlato/integration/types";
	}

	@Override
	protected Type parse(String content) throws ParseException {
		return new Parser().parse(ParseContext.Type, content);
	}
}
