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
 * Modulus represents the binary arithmetic operator 'mod'.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public final class Modulus extends Operator {
    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>Modulus</code> instance
     */
    public Object clone () {
        return new Modulus();
    }

    /**
     * Represents this class as a string.
     *
     * @return	the string representation 'mod'
     */
    public String toString () {
        return " mod ";
    }
}
