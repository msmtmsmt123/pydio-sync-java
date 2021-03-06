/*
 * Copyright 2012 Charles du Jeu <charles (at) pyd.io>
 * This file is part of Pydio.
 *
 * Pydio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pydio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pydio.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The latest code can be found at <http://pyd.io/>.
 *
 */
package io.pyd.synchro.exceptions;

/**
 * Exception object for errors with file operations during synchronization
 * @author WojT
 *
 */
public class SynchroFileOperationException extends Exception {

	public SynchroFileOperationException(String msg) {
		super(msg);
	}
	public SynchroFileOperationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
