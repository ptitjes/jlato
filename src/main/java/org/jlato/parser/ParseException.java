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

package org.jlato.parser;

/**
 * This exception is thrown when parse errors are encountered.
 */
public class ParseException extends Exception {

	static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 *
	 * @param message the exception message.
	 */
	public ParseException(String message) {
		super(message);
	}

	/**
	 * Constructor with message.
	 *
	 * @param message the exception message.
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method).  (A <tt>null</tt> value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 */
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
