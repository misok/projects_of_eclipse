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
 * TransformVisitor implements a security checker for MWL programs to
 * detect timing leaks. It builds up a transformed program that is rid
 * of timing leaks if possible. If this transformation is possible is
 * indicated by the result variable as return value of the
 * <code>check</code> method. The program will be build up anyway,
 * disregarding the security of the final program (alway check the
 * result variable when using the transformed program!)
 * <p> 
 * The basic idea is: In the beginning, we assume the program to be
 * secure, i.e. the result is set to true. For each command we
 * encounter we try to apply the security check rules. The result is
 * set to <code>false</code> if the command is insecure and it cannot
 * be transformed to a secure command. It remains <code>true</code> as
 * long as the commands are secure or can be transformed to secure
 * commands without modifying the semantics. 
 * <p>
 * Each <code>visit</code> method returns an <code>ElementPair</code>
 * instance containing the transformed command or program part, its
 * type and a flag indicating assignment to low of this subtree. The
 * new program, transformed or not, is created on the fly and with the
 * information received from the children. All
 * <code>ElementPair</code>s contain clones and no original commands
 * and programs.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class TransformVisitor extends AbstractVisitor {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private static final int LOW = Visitor.LOW;
    private static final int HIGH = Visitor.HIGH;

    private boolean checkResult;	// whether a program is secure
					// or can be transformed to a
					// secure one
    private Map decls;


    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	decls   a Map containing the variable declarations
     *			   for the program to be visited
     */
    public TransformVisitor (Map decls) {
	checkResult = true;
	this.decls = decls;
    }

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Returns an Object containing the result of the check.
     *
     * @return	a Boolean instance containing <code>true</code>
     *          if the program is secure according to the check or
     *          could be transformed into a secure one, containing
     *          <code>false</code> otherwise. 
     */
    public Object getResult () {
	return new Boolean(checkResult);
    }

    /**
     * Implements the [Skip] rule.
     *
     * @param	s	a <code>Skip</code> command
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation  
     */
    public Object visit (Skip s) {
	Skip sNew = (Skip) s.clone();
	Skip sNewType = (Skip) s.clone();
	return new ElementPair(sNew, sNewType, false);
    }

    /**
     * Implements the [Var] rule.
     *
     * @param	vd	a <code>VarDecl</code> command
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation
     */
    public Object visit (VarDecl vd) {
	VarDecl vNew = (VarDecl) vd.clone();
	VarDecl vNewType = (VarDecl) vd.clone();
	return new ElementPair(vNew, vNewType, false);
    }				

    /**
     * Implements the [ArrDecl] rule.
     *
     * @param   ad      an <code>ArrDecl</code> command
     * @return  the resulting <code>ElementPair</code> from the
     *          ransformation
     */
    public Object visit (ArrDecl ad) {
         ArrDecl aNew = (ArrDecl) ad.clone();
         ArrDecl aNewType = (ArrDecl) ad.clone();
         return new ElementPair(aNew, aNewType, false);
     }
	
    /**
     * Implements the [Assign_high] and [Assign_low] rules.
     *
     * @param	as	a <code>Assign</code> command
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation
     */
    public Object visit (Assign as) {
	// First clone the assignment, because it is unchanged during
	// the transformation.
	Assign a = (Assign) as.clone();

	ElementPair elPair = null;		// the final element pair

	// Then get the security domain of the identifier
	ExpVisitor idV = new ExpVisitor(decls);
        as.getVariable().accept(idV);
	int sdId = ((Integer) idV.getResult()).intValue();
	
	if (sdId == HIGH)				       // [Assign_high]
	    // If the security domain is HIGH, the type is a new Skip
	    // and the assignment to a low variable is false.
	    elPair = new ElementPair(a, new Skip(), false);
	else if (sdId == LOW) {					// [Assign_low]
	    // If it is LOW, the expression must be of security domain
	    // low as well [Assign_low], otherwise the checkResult is
	    // false.   
	    ExpVisitor expV = new ExpVisitor(decls);
	    as.getExp().accept(expV);
	    int sdExp = ((Integer) expV.getResult()).intValue();
	    if (sdExp != LOW)
		checkResult = false;
	    
	    // The type of the assignment is a clone of it, too.
	    Assign aType = (Assign) as.clone();
	    elPair = new ElementPair(a, aType, true);
	} else {
	    System.err.println("Undefined security domain "+sdId);
	    checkResult = false;
	    elPair = null;
	}
	return elPair;
    }

    /**
     * Implements the [If_low] and [If_high] rules.
     *
     * @param	i  an <code>If</code> command
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation, <code>null</code> in case of error
     */
    public Object visit (If i) {
	// First we get the two programs of the if-command.
	Program pIf = i.getIfProgram();
	Program pElse = i.getElseProgram();

	// Then we invoke the transformation of the programs which has
	// to be done anyway regardless of the security type of the
	// boolean expression.
	ElementPair pIfPair = (ElementPair) pIf.accept(this);
	ElementPair pElsePair = null;
	if (pElse != null)
	    pElsePair = (ElementPair) pElse.accept(this);

	// We get the transformed if-program, its type and the flag
	// whether there is an assignment to low...
	Program pIfProg = (Program) pIfPair.getElement();
	Program pIfType = (Program) pIfPair.getType();
	boolean pIfAl = pIfPair.getAlFlag();
	
	// ... and do the same for the else-program.
	Program pElseProg = null;
	Program pElseType = null;
	boolean pElseAl = false;
	if (pElsePair != null) {
	    pElseProg = (Program) pElsePair.getElement();
	    pElseType = (Program) pElsePair.getType();
	    pElseAl = pElsePair.getAlFlag();
	}

	ElementPair elPair = null;	// will contain the final result
	
	// Then we get the security domain of the boolean expression. 
	ExpVisitor boolV = new ExpVisitor(decls);
	BoolExp be = i.getBoolExp();
	be.accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();

	if (sdBool == LOW) {					// [If_low]
	    // In the case of a low conditional we finally construct
	    // the new if-command, its type and the resulting
	    // ElementPair to be returned.
	    Command ifNew = new If((BoolExp) be.clone(), pIfProg, pElseProg);
	    Command ifNewType = new If((BoolExp) be.clone(), 
				       pIfType, pElseType);
	    elPair = new ElementPair(ifNew, ifNewType, pIfAl || pElseAl);

	} else if (sdBool == HIGH) {				// [If_high]
	    // We need to check if there are assignments to low
	    // variables in the two programs.
	    boolean al = pIfAl || pElseAl;

	    // We create a new (transformed) if command and its
	    // type. First we clone Sl_1 and Sl_2, because we need it
	    // for the transformed command as well as for its type. 
	    Program pIfTypeClone = (Program) pIfType.clone();
	    Program pElseTypeClone = null;
	    if (pElseType != null)
		pElseTypeClone = (Program) pElseType.clone();
	    
	    // Compose the "if B then C_1';Sl_2 else Sl_1;C_2'"
	    pIfProg.append(pElseType);
	    pIfType.append(pElseProg);
	    Command ifNew = new If((BoolExp) be.clone(), 
				   pIfProg, pIfType);
	    
	    // Compose the "skip;Sl_1;Sl_2"
	    pIfTypeClone.append(pElseTypeClone);
	    Program ifNewType = new Program(new Skip(), pIfTypeClone);
	    
	    if (al!=false)	
		// There is an assignment to a low variable in the
		// programs -> the program cannot be typed
		checkResult = false;

	    elPair = new ElementPair(ifNew, ifNewType, false);
	} else {
	    System.err.println("Undefined security domain "+sdBool);
	    checkResult = false;
	    elPair = null;
	}
	return elPair;
    }
    
    /**
     * Implements the [While_low] rule.
     *
     * @param	wh	a <code>While</code> command
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation
     */
    public Object visit (While wh) {
	// First we get the program of the while-command.
	Program p = wh.getProgram();
	
	// Now we invoke the transformation of the program
	ElementPair pPair = (ElementPair) p.accept(this);

	// We get the transformed program, its security type
	// and the flag whether there is an assignment to low.
	Program pWhileProg = (Program) pPair.getElement();
	Program pWhileType = (Program) pPair.getType();
	boolean pWhileAl = pPair.getAlFlag();

	// Then we get the security domain of the boolean expression. 
	ExpVisitor boolV = new ExpVisitor(decls);
	BoolExp be = wh.getBoolExp();
	be.accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();

	// Reject this while command if its boolean expression is of
	// type HIGH
	if (sdBool == HIGH)				// [While_high]
	    checkResult = false;

	// Now we create a new (transformed) while command and its type.
	Command whileNew = new While((BoolExp) be.clone(), pWhileProg);
	Command whileNewType = new While((BoolExp) be.clone(), pWhileType);

	// Regardless of the check result we return the new ElementPair
	return new ElementPair(whileNew, whileNewType, pWhileAl);
    }

    /**
     * Implements the [Fork] rule.
     *
     * @param	fk  a <code>Fork</code> command
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation
     */
    public Object visit (Fork fk) {			// [Fork]
	// First we get the program and the program vector of the
	// fork-command.
	Program p = fk.getProgram();
	ProgramVector pv = fk.getProgramVector();
	
	// Now we invoke the transformation of the program as well as
	// of the program vector
	ElementPair pPair = (ElementPair) p.accept(this);
	ElementPair pvPair = null;
	if (pv != null) 
	    pvPair = (ElementPair) pv.accept(this);
	
	// We get the transformed program and program vector as well
	// as their types and the flags whether there is an assignment
	// to low...
	Program pProg = (Program) pPair.getElement();
	Program pType = (Program) pPair.getType();
	boolean pAl = pPair.getAlFlag();
	
	ProgramVector pvProg = null;
	ProgramVector pvType = null;
	boolean pvAl = false;
	if (pvPair != null) {
	    pvProg = (ProgramVector) pvPair.getElement();
	    pvType = (ProgramVector) pvPair.getType();
	    pvAl = pvPair.getAlFlag();
	}

	// Now we create a new (transformed) fork command and its type.
	Command forkNew = new Fork(pProg, pvProg);
	Command forkNewType = new Fork(pType, pvType);

	// Regardless of the check result we return the new ElementPair
	return new ElementPair(forkNew, forkNewType, pAl || pvAl);
    }

    /**
     * Splits up the check for a Program into the check of its Command
     * and the check for the next Program. Implements the [Seq] rule
     * implicitely. 
     *
     * @param	p  a <code>Program</code>
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation
     */
    public Object visit (Program p) {
	// First we get the command and the next program of p
	Command c = p.getCommand();
	Program pNext = p.getProgram();

	// Now we invoke the transformation of the command as well as
	// of the next program
	ElementPair cPair = (ElementPair) c.accept(this);
	ElementPair pPair = null;
	if (pNext != null) 
	    pPair = (ElementPair) pNext.accept(this);

	// We get the transformed command and program as well as their
	// types and the flags whether there is an assignment to
	// low...
	Command cComm = (Command) cPair.getElement();
	MwlElement cType = (MwlElement) cPair.getType();
	boolean cAl = cPair.getAlFlag();

	Program pProg = null;
	Program pType = null;
	boolean pAl = false;
	if (pPair != null) {
	    pProg = (Program) pPair.getElement();
	    pType = (Program) pPair.getType();
	    pAl = pPair.getAlFlag();
	}

	// Now we create a new (transformed) program and its type.
	Program progNew = new Program(cComm, pProg);
	// To create its type, which is a program, we need to
	// differentiate on the type of the new command - is it a
	// command or a program itself (-> If_high)?
	Program progNewType = null;
	if (cType instanceof Command) 
	    progNewType = new Program((Command) cType, pType);
	else if (cType instanceof Program) {
	    Program cTypeProg = (Program) cType;
	    if (pType != null)
		pType.append(cTypeProg.getProgram());
	    else
		pType = cTypeProg.getProgram();
	    progNewType = new Program(cTypeProg.getCommand(), pType);
	} else 
	    System.err.println("Case that should never happen: "+
			       "The type of the transformed command "+c+
			       " is a ProgramVector.");

	// We return the new ElementPair
	return new ElementPair(progNew, progNewType, cAl || pAl);
    }


    /**
     * Splits up the check for a ProgramVector into the check of its
     * Program and the check for the next ProgramVector. 
     *
     * @param	pv	a <code>ProgramVector</code>
     * @return	the resulting <code>ElementPair</code> from the
     *		transformation
     */
    public Object visit (ProgramVector pv) {
	// First we get the program and the next program vector
	Program p = pv.getProgram();
	ProgramVector pvNext = pv.getProgramVector();
	
	// Now we invoke the transformation of the program as well as
	// of the program vector
	ElementPair pPair = (ElementPair) p.accept(this);
	ElementPair pvPair = null;
	if (pvNext != null) 
	    pvPair = (ElementPair) pvNext.accept(this);
	
	// We get the transformed program and program vector as well
	// as their types and the flags whether there is an assignment
	// to low...
	Program pProg = (Program) pPair.getElement();
	Program pType = (Program) pPair.getType();
	boolean pAl = pPair.getAlFlag();

	ProgramVector pvProg = null;
	ProgramVector pvType = null;
	boolean pvAl = false;
	if (pvPair != null) {
	    pvProg = (ProgramVector) pvPair.getElement();
	    pvType = (ProgramVector) pvPair.getType();
	    pvAl = pvPair.getAlFlag();
	}

	// Now we create a new (transformed) program vector and its type.
	ProgramVector pvNew = new ProgramVector(pProg, pvProg);
	ProgramVector pvNewType = new ProgramVector(pType, pvType);

	// We return the new ElementPair
	return new ElementPair(pvNew, pvNewType, pAl || pvAl);
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
	System.out.println("TransformVisitor: This method should " + 
			   "never be called!");
	return null;
    }
}
