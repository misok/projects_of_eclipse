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

package visitor;

import ast.*;
import java.lang.StringBuffer;
import java.util.Map;


/**
 * ToStringVisitor produces a string representation of a
 * <code>Program</code>. The main application for this visitor is
 * to transform a program into a String that can be printed into a
 * file.
 * <p>
 * Each <code>visit</code> method returns <code>null</code> because
 * the return value is of no interest, only the result variable
 * containing the String representation of the program counts.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class ToStringVisitor extends AbstractVisitor {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private StringBuffer buf;
    private int indents;


    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    public ToStringVisitor () {
	buf = new StringBuffer();
	indents = 0;
    }

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Returns an Object containing the result of the visits for
     * printing. 
     *
     * @return	a String representation of this program. 
     */
    public Object getResult () {
	return buf.toString();
    }

    /**
     * Prints a <code>True</code> truth value.
     *
     * @param	t  a <code>True</code> truth value
     * @return	null
     */
    public Object visit (True t) {
	buf.append("true");
	return null;
    }

    /**
     * Prints a <code>False</code> truth value.
     *
     * @param	f  a <code>False</code> truth value
     * @return	null
     */
    public Object visit (False f) {
	buf.append("false");
	return null;
    }

    /**
     * Prints an <code>Int</code>.
     *
     * @param	i  the <code>Int</code>
     * @return	null
     */
    public Object visit (Int i) {
	buf.append(i.toString());
	return null;
    }

    /**
     * Prints an <code>Identifier</code>.
     *
     * @param	id  the<code>Identifier</code>
     * @return	null
     */
    public Object visit (Identifier id) {
	buf.append(id.getName());
	return null;
    }

    /**
     * Prints an <code>ArrLength</code>, the call to the length of an
     * array. 
     *
     * @param	al  the <code>ArrLength</code>
     * @return	null
     */
    public Object visit (ArrLength al) {
	buf.append(al.toString());
	return null;
    }
    
    /**
     * Prints an <code>ArrField</code>, an array field. 
     *
     * @param	af  the <code>ArrField</code>
     * @return	null
     */
    public Object visit (ArrField af) {
	buf.append(af.toString());
	return null;
    }
    
    /**
     * Splits up an <code>ArithExp</code> into the two parts it
     * consists of and envokes the analysis for both of them with
     * itself.
     *
     * @param	ae  an <code>ArithExp</code>
     * @return	null
     */
    public Object visit (ArithExp ae) {
	buf.append("(");
	ae.getExp1().accept(this);
	buf.append(ae.getOperator().toString());
	ae.getExp2().accept(this);
	buf.append(")");
	return null;
    }

    /**
     * Splits up a <code>CompExp</code> into the two parts it
     * consists of and envokes the analysis for both of them with
     * itself.
     *
     * @param	ce  an <code>CompExp</code>
     * @return	null
     */
    public Object visit (CompExp ce) {
	ce.getExp1().accept(this);
	buf.append(ce.getBoolOp().toString());
	ce.getExp2().accept(this);
	return null;
    }

    /**
     * Prints a <code>Skip</code>.
     *
     * @param	s	a <code>Skip</code> command
     * @return	null
     */
    public Object visit (Skip s) {
	buf.append(s.toString());
	return null;
    }

    /**
     * Prints a variable declaration <code>VarDecl</code>.
     *
     * @param	vd	a <code>VarDecl</code> command
     * @return	null
     */
    public Object visit (VarDecl vd) {
	vd.getIdentifier().accept(this);
	buf.append(":"+vd.getType().toString());
	buf.append(":"+vd.getSecDomain().toString());
	return null;
    }
				
    /**
     * Prints an array declaration <code>ArrDecl</code>.
     *
     * @param	ad	a <code>ArrDecl</code> command
     * @return	null
     */
    public Object visit (ArrDecl ad) {
	ad.getIdentifier().accept(this);
	buf.append(":"+ad.getType().toString()+"["+ad.getLength()+"]");
	buf.append(":"+ad.getSecDomain().toString());
	return null;
    }

    /**
     * Prints an <code>Assign</code> command.
     *
     * @param	as	an <code>Assign</code> command
     * @return	null
     */
    public Object visit (Assign as) {
	as.getVariable().accept(this);
	buf.append(":=");
	as.getExp().accept(this);
	return null;
    }

    /**
     * Prints an <code>If</code> command.
     *
     * @param	i	an <code>If</code> command
     * @return	null
     */
    public Object visit (If i) {
	buf.append("if ");
	i.getBoolExp().accept(this);
	buf.append(" then\n");
	indents++;
	i.getIfProgram().accept(this);
	indents--;
	Program pElse = i.getElseProgram();
	if (pElse != null) {
	    buf.append("\n");
	    printIndent();
	    buf.append("else\n");
	    indents++;
	    pElse.accept(this);
	    indents--;
	}
	buf.append("\n");
	printIndent();
	buf.append("end");
	return null;
    }
    
    /**
     * Prints a <code>While</code> command.
     *
     * @param	wh	a <code>While</code> command
     * @return	null
     */
    public Object visit (While wh) {
	buf.append("while ");
	wh.getBoolExp().accept(this);
	buf.append(" do\n");
	indents++;
	wh.getProgram().accept(this);
	indents--;
	buf.append("\n");
	printIndent();
	buf.append("end");
	return null;
    }

    /**
     * Prints a <code>Fork</code> command.
     *
     * @param	fk	a <code>Fork</code> command
     * @return	null
     */
    public Object visit (Fork fk) {
	buf.append("fork(\n");
	indents++;
	fk.getProgram().accept(this);
	buf.append("\n");
	printIndent();
	buf.append("<\n");
	fk.getProgramVector().accept(this);
	buf.append("\n");
	printIndent();
	buf.append(">)");
	indents--;
	return null;
    }

    /**
     * Prints a <code>Program</code>.
     *
     * @param	p	a <code>Program</code>
     * @return	null
     */
    public Object visit (Program p) {
	printIndent();
	Command c = p.getCommand();
	c.accept(this);
	Program nextP = p.getProgram();
	if (nextP != null) {
	    buf.append(";\n");
	    nextP.accept(this);
	}
	return null;
    }

    /**
     * Splits up the check for a ProgramVector into the check of its
     * Program and the check for the next ProgramVector. 
     *
     * @param	pv	a <code>ProgramVector</code>
     * @return	null
     */
    public Object visit (ProgramVector pv) {
	Program p = pv.getProgram();
	p.accept(this);
	ProgramVector nextPv = pv.getProgramVector();
	if (nextPv != null) {
	    buf.append(",\n");
	    nextPv.accept(this);
	}
	return null;
    }

    /**
     * This method will be called if none of the more specialized
     * visit methods could be called. This should never happen
     * actually.
     * <p>
     * A method call has no effect on the result. Nothing will be done
     * and the check will executed further.
     *
     * @param	o	an <code>Object</code>
     * @return	null
     */
    public Object visit (Object o) {
	System.err.println("ToStringVisitor: This method should never "+
			   "be called!");
	return null;
    }

    //-------------------------------------------------------------------------
    // Private Methods
    //-------------------------------------------------------------------------
    private void printIndent () {
	for (int i=0; i<indents; i++)
	    buf.append("  ");
    }
}

