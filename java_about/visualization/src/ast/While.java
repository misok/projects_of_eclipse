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
 * While represents a loop command. A program is executed as long as a
 * boolean expression is true.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class While extends ComposedCommand {
    private BoolExp boolExp;
    private Program prog;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	boolExp	the boolean expression
     * @param	prog	the program
     */
    public While (BoolExp boolExp, Program prog) {
	this.boolExp = boolExp; 
	this.prog = prog;
    }

    private While () {}
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the boolean expression.
     *
     * @return	the boolean expression
     */
    public BoolExp getBoolExp () {
	return boolExp;
    }
    
    /**
     * Getter method for the program.
     *
     * @return	the program
     */
    public Program getProgram () {
	return prog;
    }
    
    /**
     * Setter method for the boolean expression.
     *
     * @param	boolExp	the boolean expression
     */
    public void setBoolExp (BoolExp boolExp) {
	this.boolExp = boolExp;
    }
    
    /**
     * Setter method for the program.
     *
     * @param	prog	the program
     */
    public void setProgram (Program prog) {
	this.prog = prog;
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>While</code> instance 
     */
    public Object clone () {
        return new While((BoolExp) boolExp.clone(), (Program) prog.clone());
    }
    
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this while command
     */
    public String toString () {
	if ((boolExp == null) || (prog == null))
	    return null;
	return "while "+boolExp.toString()+" do "+prog.toString();
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

