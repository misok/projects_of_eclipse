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
 * Assignable respresents all MWL constructs to which expressions can
 * be assigned, i.e. <code>Assignable</code> implementations are the
 * left side of assignments.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public interface Assignable {
    /**
     * Getter method for name of Assignable.
     *
     * @return	string representation of this Assignable 
     */
    public String getName ();

    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this Assignable
     */
    public String toString ();

    /**
     * Clones this instance.
     *
     * @return	the clone, which is an <code>Assignable</code>
     * instance 
     */
    public Object clone ();

    /**
     * Accept method for the visitor design pattern.
     *
     * @param	v   a visitor
     * @return	the result of the visitors visit method
     */
    public Object accept (Visitor v);
}