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
 * Assign represents an assignment of an expression to an
 * identifier or to an array field. 
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class Assign extends PrimitiveCommand {
    private Assignable ass;
    private Exp exp;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * Assignment to an Assignable, which may be an
     * <code>Identifier</code> or an <code>ArrField</code>.
     *
     * @param	ass	the assignable
     * @param	exp	the expression
     */
    public Assign (Assignable ass, Exp exp) {
        this.ass = ass; 
        this.exp = exp;
    }    

    private Assign () {}
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the variable.
     *
     * @return	the variable
     */
    public Assignable getVariable () {
        return ass;
    }

    /**
     * Getter method for the expression.
     *
     * @return	the expression
     */
     public Exp getExp () { 
        return exp;
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>Assign</code>
     * instance 
     */
    public Object clone () {
	return new Assign((Assignable) ass.clone(), (Exp) exp.clone());
    }
  
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this assignment
     */
    public String toString () {
	return ass.toString()+":="+exp.toString();
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
