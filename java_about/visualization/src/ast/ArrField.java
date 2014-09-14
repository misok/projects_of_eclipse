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
 * ArrField represents an array field. An array field is specified by
 * the name of the array and the index of the field.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public final class ArrField extends Exp implements Assignable {
    private String name;
    private Exp index;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	name	the name of the identifier
     */
    public ArrField (String name, Exp index) {
	this.name = name;
	this.index = index;
    }
    
    //Private constructor -> cannot be called externally
    private ArrField () {}
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the name of the array.
     *
     * @return	name of the array identifier 
     */
    public String getName() {
	return name;
    }
    
    /**
     * Getter method for the index of the field.
     *
     * @return	the index
     */
    public Exp getIndex () {
	return index;
    }
    
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this identifier 
     */
    public String toString () {
	return name+"["+index+"]";
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>Identifier</code>
     * instance 
     */
    public Object clone () {
	return new ArrField(name,(Exp) index.clone());
    }
    
    /**
     * Accept method for the visitor design pattern.
     *
     * @param	v   a visitor
     * @return	the result of the visitors visit method
     */
    public Object accept (Visitor v) {
	return v.visit(this);
    }
}
