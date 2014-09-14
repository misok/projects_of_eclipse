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
 * TransformOldVisitor implements a security checker for MWL programs to
 * detect timing leaks. If it detects a timing leak that can be
 * repaired it will return this transformed program.  
 * <p> 
 * The basic idea is: In the beginning, we assume the program to be
 * secure, i.e. the result is set to true. For each command we
 * encounter we try to apply the security check rules. The result is
 * set to <code>false</code> if the command is insecure and it cannot
 * be transformed to a secure command. It remains <code>true</code> as
 * long as the commands are secure or can be transformed to secure
 * commands without modifying the semantics. If such a transformation
 * is possible and necessary it will be executed directly on the given
 * program.
 * <p>
 * Each <code>visit</code> method returns <code>null</code> because
 * the AST tree is transformed directly and there is no return value
 * of interest.
 * <p>
 * NOTE: This class is deprecated.
 *
 * @deprecated
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class TransformOldVisitor extends AbstractVisitor {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private static final int LOW = Visitor.LOW;
    private static final int HIGH = Visitor.HIGH;

    private boolean checkResult;	//whether a program is secure
					//or can be transformed to a
					//secure one
    private Map varDecl;


    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	varDecls   a Map containing the variable declarations
     *			   for the program to be visited
     */
    public TransformOldVisitor (Map varDecls) {
	checkResult = true;
	this.varDecl = varDecls;
    }

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Returns an Object containing the result of the check.
     *
     * @return	a Boolean instance containing <code>true</code>
     *          if the program is secure according to the check or
     *          could be transformed to a secure one, containing
     *          <code>false</code> otherwise. 
     */
    public Object getResult () {
	return new Boolean(checkResult);
    }

    /**
     * Implements the [Skip] rule. Skip remains the same, no
     * modifications at all.
     *
     * @param	s	a <code>Skip</code> command
     * @return	null
     */
    public Object visit (Skip s) {
	return null;
    }

    /**
     * Implements the [Var] rule. No modification at all.
     *
     * @param	vd	a <code>VarDecl</code> command
     * @return	null
     */
    public Object visit (VarDecl vd) {
	return null;
    }				

    /**
     * Implements the [Assign_high] and [Assign_low] rules.
     *
     * @param	as	a <code>Assign</code> command
     * @return	null
     */
    public Object visit (Assign as) {
	ExpVisitor idV = new ExpVisitor(varDecl);
        as.getVariable().accept(idV);
	int sdId = ((Integer) idV.getResult()).intValue();

	// If the security domain of the identifier is HIGH, nothing
	// has to be done, the transformated assignment is equal to
	// the original one. [Assign_high]
	// If it is LOW, the expression must be of security domain
	// low as well. [Assign_low]
	if (sdId == LOW) {
	    ExpVisitor expV = new ExpVisitor(varDecl);
	    as.getExp().accept(expV);
	    int sdExp = ((Integer) expV.getResult()).intValue();
	    if (sdExp != LOW)
		checkResult = false;
	}
	return null;
    }

    /**
     * Implements the [If_low] and [If_high] rules.
     *
     * @param	i  an <code>If</code> command
     * @return	null
     */
     public Object visit (If i) {
	ExpVisitor boolV = new ExpVisitor(varDecl);
	i.getBoolExp().accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();

	Program pIf = i.getIfProgram();
	Program pElse = i.getElseProgram();
	if (sdBool == LOW) {				// [If_low]
	    pIf.accept(this);
	    i.setIfProgram(pIf);
	    
	    if (pElse != null) {
		pElse.accept(this);
		i.setElseProgram(pElse);
	    }
	} else {					// [If_high]
	    // First, we need to transform the two programs of an if-command
	    // => pIf and pElse, which already modifies the if-command
	    // from "if B then C1 else C2" to "if B then C1' else C2'" 
	    pIf.accept(this);	    
	    if (pElse != null)
		pElse.accept(this);

	    // Then, we determine the types of these two transformed programs
	    // => pIfType and pElseType, which are copies!
	    TypeDeterminer td = new TypeDeterminer(varDecl);
	    Program pIfType = (Program) (td.getType(pIf));
	    Program pElseType = null;
	    if (pElse != null)
		pElseType = (Program) (td.getType(pElse));
					   
	    // The third thing is to check that there are no assignments to
	    // low variables in the two programs
	    AlVisitor alVis = new AlVisitor(varDecl);
	    pIfType.accept(alVis);
	    if (pElseType != null)
		pElseType.accept(alVis);
	    boolean al = ((Boolean) alVis.getResult()).booleanValue();

	    if (al == false) {
		// Now all conditions are fulfilled. We modify the
		// original if-command further from "if B then C1'
		// else C2'" to "if B then C1';Sl2 else Sl1;C2'"
		pIf.append(pElseType);
		pIfType.append(pElse);
		i.setElseProgram(pIfType);
	    } else 
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
	ExpVisitor boolV = new ExpVisitor(varDecl);
	wh.getBoolExp().accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();

	// If the While condition is low, we only need to set the
	// transformed program.
	if (sdBool == LOW) {				// [While_low]
	    Program p = wh.getProgram();
	    p.accept(this);
	    wh.setProgram(p);
	}
	else
	    checkResult = false;
	return null;
    }

    /**
     * Implements the [Fork] rule.
     *
     * @param	fk  a <code>Fork</code> command
     * @return	null
     */
    public Object visit (Fork fk) {			// [Fork]
	Program p = fk.getProgram();
	p.accept(this);
	fk.setProgram(p);

	ProgramVector pv = fk.getProgramVector();

	if (pv != null) {
	    pv.accept(this);
	    fk.setProgramVector(pv);
	}
	return null;
    }

    /**
     * Splits up the check for a Program into the check of its Command
     * and the check for the next Program. Implements the [Seq] rule
     * implicitely. 
     *
     * @param	p  a <code>Program</code>
     */
    public Object visit (Program p) {
	Command c = p.getCommand();
	c.accept(this);
	p.setCommand(c);

	Program nextP = p.getProgram();
	if (nextP != null) {
	    nextP.accept(this);
	    p.setProgram(nextP);
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
	pv.setProgram(p);

	ProgramVector nextPv = pv.getProgramVector();
	if (nextPv != null) {
	    nextPv.accept(this);
	    pv.setProgramVector(nextPv);
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
     * @param	o an <code>Object</code>
     * @return	null
     */
    public Object visit (Object o) {
	System.out.println("TransformOldVisitor: This method should " + 
			   "never be called!");
	return null;
    }
}
