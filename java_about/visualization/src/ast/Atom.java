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
 * Atom represents an atomic expression, such as an
 * <code>Identifier</code> or an <code>Integer</code>.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public abstract class Atom extends Exp {
    /**
     * Clones this instance.
     *
     * @return	the clone
     */
    public abstract Object clone ();

    /**
     * Represents this class as a string.
     *
     * @return	the string representation
     */
    public abstract String toString ();

    /**
     * Accept method for the visitor design pattern.
     *
     * @param	v   a visitor
     * @return	the result of the visitors visit method
     */
    public abstract Object accept (Visitor v);
}

