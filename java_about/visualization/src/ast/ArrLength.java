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
 * ArrLength represents the request of an array length.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public final class ArrLength extends Exp {
    private String array;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	array	the name of the array
     */
    public ArrLength (String array) {
	this.array = array;
    }
    
    /** 
     * Private constructor -> cannot be called externally
     */
    private ArrLength () {}
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the array's name.
     *
     * @return	the name
     */
    public String getName () {
	return array;
    }
    
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this identifier 
     */
    public String toString () {
	return array+".length";
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>Identifier</code>
     * instance 
     */
    public Object clone () {
	return new ArrLength(array);
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
