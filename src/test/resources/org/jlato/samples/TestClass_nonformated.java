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

package org.jlato.tree;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.BUTree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;

public abstract class TestClass {

protected final TDLocation location;

protected TestClass(TDLocation location) {
this.location = location;
}

public Tree parent() {
return location.parent();
}

public Tree root() {
return location.root();
}

public static BUTree treeOf(Tree facade) {
return facade == null ? null : facade.location.tree;
}

protected static ArrayList<BUTree> treesOf(Tree... facades) {
final Builder<BUTree, ArrayList<BUTree>> builder = ArrayList.<BUTree>factory().newBuilder();
for (Tree facade : facades) {
builder.add(treeOf(facade));
}
return builder.build();
}

protected static ArrayList<Object> dataOf(Object... attributes) {
final Builder<Object, ArrayList<Object>> builder = ArrayList.factory().newBuilder();
for (Object attribute : attributes) {
builder.add(attribute);
}
return builder.build();
}

protected static Vector<BUTree> treeListOf(Tree... facades) {
final Builder<BUTree, Vector<BUTree>> builder = Vector.<BUTree>factory().newBuilder();
for (Tree facade : facades) {
builder.add(treeOf(facade));
}
return builder.build();
}

public interface Kind {

Tree instantiate(TDLocation location);

LexicalShape shape();
}
}
