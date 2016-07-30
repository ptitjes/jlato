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

package org.jlato.unit.lex;

import org.jlato.internal.bu.WToken;
import org.jlato.internal.bu.WTokenRun;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.jlato.internal.bu.WTokenRun.NULL;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class WTokenRunTest {

	@Test
	public void splitTrailingWithOneSpace() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitTrailingComment();
		Assert.assertEquals(NULL, split.left);
		Assert.assertEquals(1, split.right.elements.size());
		Assert.assertEquals("[ ]", split.right.toString());
	}

	@Test
	public void splitTrailingWithNoComment() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitTrailingComment();
		Assert.assertEquals(NULL, split.left);
		Assert.assertEquals(2, split.right.elements.size());
		Assert.assertEquals("[\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitTrailingWithOneSingleLineComment() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.singleLineComment("// Comment"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitTrailingComment();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(2, split.right.elements.size());
		Assert.assertEquals("[ ,// Comment]", split.left.toString());
		Assert.assertEquals("[\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitTrailingWithOneMultiLineComment() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.singleLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitTrailingComment();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(1, split.right.elements.size());
		Assert.assertEquals("[ ,/* Comment */]", split.left.toString());
		Assert.assertEquals("[ ]", split.right.toString());
	}

	@Test
	public void splitLeadingWithOneSpace() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitLeadingComments();
		Assert.assertEquals(1, split.left.elements.size());
		Assert.assertEquals(NULL, split.right);
		Assert.assertEquals("[ ]", split.left.toString());
	}

	@Test
	public void splitLeadingWithNoComment() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitLeadingComments();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(NULL, split.right);
		Assert.assertEquals("[\\n,\\t]", split.left.toString());
	}

	@Test
	public void splitLeadingWithOneComment() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitLeadingComments();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(3, split.right.elements.size());
		Assert.assertEquals("[\\n,\\t]", split.left.toString());
		Assert.assertEquals("[/* Comment */,\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitLeadingWithTwoComments() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitLeadingComments();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(6, split.right.elements.size());
		Assert.assertEquals("[\\n,\\t]", split.left.toString());
		Assert.assertEquals("[/* Comment */,\\n,\\t,/* Comment */,\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitLeadingWithEmptyLine() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.TwoWaySplit split = run.splitLeadingComments();
		Assert.assertEquals(6, split.left.elements.size());
		Assert.assertEquals(3, split.right.elements.size());
		Assert.assertEquals("[\\n,\\t,/* Comment */,\\n,\\n,\\t]", split.left.toString());
		Assert.assertEquals("[/* Comment */,\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitTrailingLeadingWithOneSpace() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(NULL, split.left);
		Assert.assertEquals(1, split.middle.elements.size());
		Assert.assertEquals(NULL, split.right);
		Assert.assertEquals("[ ]", split.middle.toString());
	}

	@Test
	public void splitTrailingLeadingWithOneSingleLineComment() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.singleLineComment("// Comment"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(2, split.middle.elements.size());
		Assert.assertEquals(NULL, split.right);
		Assert.assertEquals("[ ,// Comment]", split.left.toString());
		Assert.assertEquals("[\\n,\\t]", split.middle.toString());
	}

	@Test
	public void splitTrailingLeadingWithOneMultiLineComment() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(NULL, split.left);
		Assert.assertEquals(2, split.middle.elements.size());
		Assert.assertEquals(3, split.right.elements.size());
		Assert.assertEquals("[\\n,\\t]", split.middle.toString());
		Assert.assertEquals("[/* Comment */,\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitTrailingLeadingWithManyTrailingMultiLineComments() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(3, split.left.elements.size());
		Assert.assertEquals(2, split.middle.elements.size());
		Assert.assertEquals(3, split.right.elements.size());
		Assert.assertEquals("[/* Comment */, ,/* Comment */]", split.left.toString());
		Assert.assertEquals("[\\n,\\t]", split.middle.toString());
		Assert.assertEquals("[/* Comment */,\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitTrailingLeadingWithManyTrailingMultiLineCommentsButNewLine() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(1, split.left.elements.size());
		Assert.assertEquals(2, split.middle.elements.size());
		Assert.assertEquals(6, split.right.elements.size());
		Assert.assertEquals("[/* Comment */]", split.left.toString());
		Assert.assertEquals("[\\n,\\t]", split.middle.toString());
		Assert.assertEquals("[/* Comment */,\\n,\\t,/* Comment */,\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitTrailingLeadingWithManyTrailingMultiLineCommentsButNewLines() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.newLine());
		builder.add(WToken.whitespace("\t"));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(1, split.left.elements.size());
		Assert.assertEquals(6, split.middle.elements.size());
		Assert.assertEquals(3, split.right.elements.size());
		Assert.assertEquals("[/* Comment */]", split.left.toString());
		Assert.assertEquals("[\\n,\\t,/* Comment */,\\n,\\n,\\t]", split.middle.toString());
		Assert.assertEquals("[/* Comment */,\\n,\\t]", split.right.toString());
	}

	@Test
	public void splitTrailingLeadingWithOneCommentNoNewLine() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(NULL, split.left);
		Assert.assertEquals(1, split.middle.elements.size());
		Assert.assertEquals(2, split.right.elements.size());
		Assert.assertEquals("[ ]", split.middle.toString());
		Assert.assertEquals("[/* Comment */, ]", split.right.toString());
	}

	@Test
	public void splitTrailingLeadingWithTwoCommentsNoNewLine() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(1, split.middle.elements.size());
		Assert.assertEquals(2, split.right.elements.size());
		Assert.assertEquals("[ ,/* Comment */]", split.left.toString());
		Assert.assertEquals("[ ]", split.middle.toString());
		Assert.assertEquals("[/* Comment */, ]", split.right.toString());
	}

	@Test
	public void splitTrailingLeadingWithThreeCommentsNoNewLine() {
		final WTokenRun.Builder builder = new WTokenRun.Builder();
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		builder.add(WToken.multiLineComment("/* Comment */"));
		builder.add(WToken.whitespace(" "));
		final WTokenRun run = builder.build();

		final WTokenRun.ThreeWaySplit split = run.splitTrailingAndLeadingComments();
		Assert.assertEquals(2, split.left.elements.size());
		Assert.assertEquals(1, split.middle.elements.size());
		Assert.assertEquals(4, split.right.elements.size());
		Assert.assertEquals("[ ,/* Comment */]", split.left.toString());
		Assert.assertEquals("[ ]", split.middle.toString());
		Assert.assertEquals("[/* Comment */, ,/* Comment */, ]", split.right.toString());
	}
}
