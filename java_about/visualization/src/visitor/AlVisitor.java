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
import java.util.Map;

/**
 * AlVisitor implements a check logic for commands and programs,
 * namely whether the command or the program contains an assignment to
 * a low variable (<b>Al</b> stands for Assignment to Low). It is
 * needed by <code>TransformOldVisitor</code> to realizes the [If_high]
 * rule. 
 * <p>
 * The basic idea is: In the beginning, we assume each command
 * <i>not</i> to contain an assignment to a low variable, i.e. the
 * internal result variable is set to false. Whenever we encouter a
 * low assignment in a command, we set the result variable to
 * true. Once the result is true it remains true in an instance of 
 * this class AlVisitor.
 * Each <code>visit</code> method returns <code>null</code> because
 * the return value is of no interest, only the result variable.
 * <p>
 * NOTE: This class is deprecated.
 * 
 * @deprecated
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class AlVisitor extends AbstractVisitor {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private static final int LOW = Visitor.LOW;
    private static final int HIGH = Visitor.HIGH;

    private boolean alResult;
    private Map varDecl;


    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	varDecl   a Map containing the variable declarations
     *			   for the program to be visited 
     */
    public AlVisitor (Map varDecl) {
	alResult = false;
	this.varDecl = varDecl;
    }


    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Returns an Object containing the result of the analysis.
     *
     * @return a Boolean instance containing <code>true</code> if an
     *	       assignment to a low variable could be found,
     *	       <code>true</code> otherwise. 
     */
    public Object getResult() {
	return new Boolean(alResult);
    }

    /**
     * Modifies the result according to the assignments in this
     * <code>Skip</code> command. Nothing is done for a skip. 
     *
     * @param	s  the <code>Skip</code> command
     * @return	null
     */
    public Object visit (Skip s) {
	return null;
    }

    /**
     * Modifies the result according to the assignments in this
     * <code>VarDecl</code> command. Nothing is done for a variable
     * declaration.
     *
     * @param	v  the <code>VarDecl</code> command
     * @return	null
     */
    public Object visit (VarDecl v) {
	return null;
    }

    /**
     * Modifies the result according to the assignments int this 
     * <code>Assign</code> command.
     *
     * @param	a  the <code>Assign</code> command
     * @return	null
     */
    public Object visit (Assign a) {
	ExpVisitor idV = new ExpVisitor(varDecl);
        a.getVariable().accept(idV);
	int sdId = ((Integer) idV.getResult()).intValue();
	
	// if the security domain is low set the result to true, else
	// do nothing 
	if (sdId == LOW)
	    alResult = true;
	return null;
    }

    /**
     * Modifies the result according to the assignments in this 
     * <code>If</code> command.
     *
     * @param	i  the <code>If</code> command
     * @return	null
     */
    public Object visit (If i) {
	i.getIfProgram().accept(this);
	Program iElse = i.getElseProgram();
	if (iElse != null)
	    iElse.accept(this);
	return null;
    }
    
    /**
     * Modifies the result according to the assignments in this 
     * <code>While</code> command.
     *
     * @param	wh  the <code>While</code> command
     * @return	null
     */
    public Object visit (While wh) {
	wh.getProgram().accept(this);
	return null;
    }

    /**
     * Modifies the result according to the assignments in this 
     * <code>Fork</code> command.
     *
     * @param	fk  the <code>Fork</code> command
     * @return	null
     */
    public Object visit (Fork fk) {
	fk.getProgram().accept(this);
	ProgramVector pv = fk.getProgramVector();
	if (pv != null)
	    pv.accept(this);
	return null;
    }

    /**
     * Modifies the result according to the assignments in this 
     * <code>Program</code>.
     *
     * @param	p  the <code>Program</code>
     * @return	null
     */
    public Object visit (Program p) {
	p.getCommand().accept(this);
	Program pNext = p.getProgram();
	if (pNext != null)
	    pNext.accept(this);
	return null;
    }


    /**
     * Modifies the result according to the assignments in this 
     * <code>ProgramVector</code>.
     *
     * @param	pv  the <code>ProgramVector</code>
     * @return	null
     */
    public Object visit (ProgramVector pv) {
	pv.getProgram().accept(this);
	ProgramVector pvNext = pv.getProgramVector();
	if (pvNext != null)
	    pvNext.accept(this);
	return null;
    }
    
    /**
     * This method will be called if none of the more specialized
     * visit methods could be called. This should never happen
     * actually.
     * <p>
     * This has no effect on the result. Simply nothing will be done
     * and the check will executed further.
     *
     * @param	o	an <code>Object</code>
     * @return	null
     */
    public Object visit (Object o) {
	System.out.println("AlVisitor: This Method should never be called!");
	return null;
    }
}
