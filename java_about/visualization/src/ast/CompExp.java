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
 * CompExp represents an boolean expression. An boolean expression
 * consists of an operator (a <code>BoolOp</code> instance) and two
 * expressions (<code>Exp</code> instances such as <code>Int</code>,
 * <code>Identifier</code>, <code>True</code>, and
 * <code>False</code>).
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class CompExp extends BoolExp {
    private Exp exp1, exp2;
    private BoolOp op;
  
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	op	the operator
     * @param	exp1	the first operand
     * @param	exp2	the second operand
     */
    public CompExp (BoolOp op, Exp exp1, Exp exp2) {
        this.op = op; 
        this.exp1 = exp1; 
        this.exp2 = exp2;
    }

    private CompExp () {}

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the first operand.
     *
     * @return	the first operand
     */
    public Exp getExp1 () {
	return exp1;
    }
    
    /**
     * Getter method for the second operand.
     *
     * @return	the second operand
     */
    public Exp getExp2 () {
	return exp2;
    }

    /**
     * Getter method for the operator.
     *
     * @return	the operator
     */
    public BoolOp getBoolOp () {
	return op;
    }
    
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this boolean expression
     */
    public String toString () {
        return exp1.toString()+op.toString()+exp2.toString();
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>CompExp</code>
     * instance 
     */
    public Object clone () {
        return new CompExp((BoolOp) op.clone(), 
			   (Exp) exp1.clone(), 
			   (Exp) exp2.clone());
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
