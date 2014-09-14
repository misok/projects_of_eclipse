/*   Information Leak Visualizer
 *   Copyright (C) 2004 - 2005  ETH Zurich
 *   Information Security Group
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package ast;

import visitor.Visitor;

/**
 * IntegerType represents the type of integers.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public final class IntegerType extends Type {
    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>IntegerType</code>
     * instance 
     */
    public Object clone () {
	return new IntegerType();
    }

    /**
     * Represents this class as a string.
     *
     * @return	the string representation 'int'
     */
    public String toString () {
        return "int";
    }
}
