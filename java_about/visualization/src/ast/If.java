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
 * If represents a conditional. A conditional consists of a boolean
 * expression and two programs. The first program is executed if the
 * boolean expression is true. Otherwise the second program is executed.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class If extends ComposedCommand {
    private BoolExp boolExp;
    private Program prog1, prog2;
    
  
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	boolExp	the boolean expression
     * @param	prog1	the first program
     * @param	prog2	the second program
     */
    public If (BoolExp boolExp, Program prog1, Program prog2) {
        this.boolExp = boolExp; 
        this.prog1 = prog1; 
        this.prog2 = prog2;
    }
    
    /** 
     * If there exists no 'else-program'.
     * 
     * @param	boolExp	the operator
     * @param	prog	the program
     */
    public If (BoolExp boolExp, Program prog) {
	this.boolExp = boolExp;
	this.prog1 = prog;
	this.prog2 = null;
    }
    
    private If () {}
    
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
     * Getter method for the first program, the 'if-program'.
     *
     * @return	the program being executed if the boolean expression
     * is true
     */
    public Program getIfProgram () {
	return prog1;
    }
    
    /**
     * Getter method for the second program, the 'else-program'.
     *
     * @return	the program being executed if the boolean expression
     * is false
     */
    public Program getElseProgram () {
	return prog2;
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
     * Setter method for the first program, the 'if-program'.
     *
     * @param	prog	the program being executed if the boolean expression
     * is true
     */
    public void setIfProgram (Program prog) {
	prog1 = prog;
    }
    
    /**
     * Getter method for the second program, the 'else-program'.
     *
     * @return	the program being executed if the boolean expression
     * is false
     */
    public void setElseProgram (Program prog) {
	prog2 = prog;
    }
   
    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>If</code> instance 
     */
    public Object clone () {
        if (prog2 != null)
	    return new If((BoolExp) boolExp.clone(), 
			  (Program) prog1.clone(), 
			  (Program) prog2.clone());
	else 
	    return new If((BoolExp) boolExp.clone(), 
			  (Program) prog1.clone()); 
    } 
    
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this if command 
     */
    public String toString () {
	if ((boolExp == null) || (prog1 == null)) 
	    return null;
	StringBuffer sb = new StringBuffer("if "+boolExp.toString());
	sb.append(" then "+prog1.toString());
	if (prog2 != null) sb.append(" else "+prog2.toString()); 
	return sb.toString(); 
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
