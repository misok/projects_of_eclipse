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
 * NoTransformVisitor implements the check logic for security checks
 * of programs without transformation. It realizes the following
 * rules: 
 * <ul>
 *   <li>[Var]</li>
 *   <li>[Skip]</li>
 *   <li>[Assign_high]</li>
 *   <li>[Assign_low]</li>
 *   <li>[If_low]</li>
 *   <li>[While_low]</li>
 *   <li>[Fork]</li>
 * </ul>
 * The basic idea is: In the beginning, we assume the program to be
 * secure, i.e. the result is set to <code>true</code>. For each command we
 * encounter we try to apply the security check rules. The result is
 * set to <code>false</code> if the command is insecure or it remains
 * unmodified if the command is secure. Thus, once the result is
 * false, it remains false till the end of the execution. 
 * <p>
 * Each <code>visit</code> method returns <code>null</code> because
 * the return value is of no interest, only the result variable counts. 
 * (November 2004)
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class NoTransformVisitor extends AbstractVisitor {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private static final int LOW = Visitor.LOW;
    private static final int HIGH = Visitor.HIGH;

    private boolean checkResult;	// the check result for this program
    private boolean notFound;		// whether a first insecure
					// command has not yet been found
    private Map decls;


    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	decls   a Map containing the variable declarations
     *			   for the program to be visited 
     */
    public NoTransformVisitor (Map decls) {
	checkResult = true;
	notFound = true;
	this.decls = decls;
    }

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Returns an Object containing the result of the check.
     *
     * @return	a Boolean instance containing <code>true</code> if the
     *		program is secure according to the check,
     *		<code>false</code> otherwise. 
     */
    public Object getResult () {
	return new Boolean(checkResult);
    }

    /**
     * Implements the [Skip] rule.
     *
     * @param	s	a <code>Skip</code> command
     * @return	null
     */
    public Object visit (Skip s) {
	return null;
    }

    /**
     * Implements the [Var] rule.
     *
     * @param	vd	a <code>VarDecl</code> command
     * @return	null
     */
    public Object visit (VarDecl vd) {
	return null;
    }
    
    /**
     * Implements the [ArrDecl] rule.
     *
     * @param   ad      a <code>VarDecl</code> command
     * @return  null
     */
    public Object visit (ArrDecl ad) {
        return null;
    }
	
    /**
     * Implements the [Assign_high] and [Assign_low] rules.
     *
     * @param	as	an <code>Assign</code> command
     * @return	null
     */
    public Object visit (Assign as) {
	ExpVisitor idV = new ExpVisitor(decls);
        as.getVariable().accept(idV);
	int sdId = ((Integer) idV.getResult()).intValue();

	ExpVisitor expV = new ExpVisitor(decls);
	as.getExp().accept(expV);
	int sdExp = ((Integer) expV.getResult()).intValue();

	if (sdId == HIGH)
	    checkResult = checkResult & true;		// [Assign_high]
	else if ((sdId == LOW) && (sdExp == LOW))
	    checkResult = checkResult & true;		// [Assign_low]
	else {
	    setFound("The first insecure command is a high assignment "+
		     "to a low variable:\n"+as+"\n");
	    checkResult = false;
	}
	return null;
    }

    /**
     * Implements the [If_low] rule.
     *
     * @param	i	an <code>If</code> command
     * @return	null
     */
    public Object visit (If i) {
	ExpVisitor boolV = new ExpVisitor(decls);
	i.getBoolExp().accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();
	
	if (sdBool == LOW) {				// [If_low]
	    i.getIfProgram().accept(this);
	    Program pElse = i.getElseProgram();
	    if (pElse != null)
		pElse.accept(this);
	    checkResult = checkResult & true;
	} else {
	    setFound("The first insecure command is a high "+
		     "conditional:\n"+i);
	    checkResult = false;
	}
	return null;
    }
    
    /**
     * Implements the [While_low] rule.
     *
     * @param	wh	a <code>While</code> command
     * @return	null
     */
     public Object visit (While wh) {
	ExpVisitor boolV = new ExpVisitor(decls);
	wh.getBoolExp().accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();

	if (sdBool == LOW)				// [While_low]
	    checkResult = checkResult & true;
	else {
	    setFound("The first insecure command is a high while loop:\n"
		     +wh);
	    checkResult = false;
	}
	wh.getProgram().accept(this);
	return null;
    }

    /**
     * Implements the [Fork] rule.
     *
     * @param	fk	a <code>Fork</code> command
     * @return	null
     */
    public Object visit (Fork fk) {			// [Fork]
	Program p = fk.getProgram();
	ProgramVector pv = fk.getProgramVector();
	p.accept(this);
	if (pv != null)
	    pv.accept(this);
	return null;
    }

    /**
     * Splits up the check for a Program into the check of its Command
     * and the check for the next Program. Implements the [Seq] rule
     * implicitely. 
     *
     * @param	p	a <code>Program</code>
     * @return	null
     */
    public Object visit (Program p) {
	Command c = p.getCommand();
	Program nextP = p.getProgram();
	c.accept(this);
	if (nextP != null) 
	    nextP.accept(this);
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
	ProgramVector nextPv = pv.getProgramVector();
	p.accept(this);
	if (nextPv != null)
	    nextPv.accept(this);
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
	System.err.println("NoTransformVisitor: This method should "+
			   "never be called!");
	return null;
    }

    //-------------------------------------------------------------------------
    // Private Methods
    //-------------------------------------------------------------------------
    private void setFound (String s) {
	if (notFound) {
	    System.out.print(s);
	    notFound = false;
	}
    }
}
