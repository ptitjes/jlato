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

package org.jlato.unit;

import org.jlato.internal.bu.Literals;
import org.jlato.parser.ParseException;
import org.jlato.rewrite.Quotes;
import org.jlato.tree.Trees;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class UtilityClassesWellDefinedTest {

	@Test
	public void literalsClass() throws FileNotFoundException, ParseException {
		assertUtilityClassWellDefined(Literals.class);
	}

	@Test
	public void treesClass() throws FileNotFoundException, ParseException {
		assertUtilityClassWellDefined(Trees.class);
	}

	@Test
	public void quotesClass() throws FileNotFoundException, ParseException {
		assertUtilityClassWellDefined(Quotes.class);
	}

	private static void assertUtilityClassWellDefined(Class<?> clazz) {
		Assert.assertTrue("Utility class '" + clazz.getName() + "' must be final",
				Modifier.isFinal(clazz.getModifiers()));

		Assert.assertTrue("Utility class '" + clazz.getName() + "' must have a unique private constructor",
				clazz.getDeclaredConstructors().length == 1);

		try {
			Constructor<?> e = clazz.getDeclaredConstructor();
			Assert.assertTrue("Constructor of utility class '" + clazz.getName() + "' must be private",
					Modifier.isPrivate(e.getModifiers()));

			e.setAccessible(true);
			e.newInstance();
			e.setAccessible(false);

			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (method.getDeclaringClass().equals(clazz))
					Assert.assertTrue("Method '" + method.getName() + "' of utility class '" + clazz.getName() + "' is not static",
							Modifier.isStatic(method.getModifiers()));
			}

		} catch (NoSuchMethodException e) {
			Assert.fail(e.getMessage());
		} catch (InstantiationException e) {
			Assert.fail(e.getMessage());
		} catch (IllegalAccessException e) {
			Assert.fail(e.getMessage());
		} catch (InvocationTargetException e) {
			Assert.fail(e.getMessage());
		}
	}
}
