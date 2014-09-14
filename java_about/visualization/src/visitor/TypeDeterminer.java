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
 * TypeDeterminer determines the type of commands and programs for MWL
 * programs and is used by <code>TransformVisitor</code>.  
 * <p> 
 * The <code>getType</code> methods return an Object which is the type
 * of the given command and which is, in general, a modified
 * copy/clone of the original command.
 * <p>
 * NOTE: This class is deprecated.
 * 
 * @deprecated
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class TypeDeterminer {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private static final int LOW = Visitor.LOW;
    private static final int HIGH = Visitor.HIGH;

    private Map varDecl;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	varDecls   a Map containing the variable declarations
     *			   for the program to be visited 
     */
    protected TypeDeterminer (Map varDecls) {
	this.varDecl = varDecls;
    }

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------
  
    /**
     * Determines the type of a <code>Skip</code>.
     *
     * @param	s	a <code>Skip</code> command
     * @return		a clone of s as an Object
     */
    protected Object getType (Skip s) {
	return s.clone();				// [Skip]
    }

    /**
     * Determines the type of a <code>VarDecl</code>.
     *
     * @param	vd	a <code>VarDecl</code> command
     * @return		a clone of vd as an Object
     */
    protected Object getType (VarDecl vd) {
	return vd.clone();				// [Var]
    }

    /**
     * Determines the type of an <code>Assign</code>.
     * <p>
     * The type is the assignment itself, if is an assignment to a low
     * variable or a <code>Skip</code> otherwise.
     *
     * @param	a	an <code>Assign</code> command
     * @return		the type of a as an Object
     */
    protected Object getType (Assign a) {
	ExpVisitor idV = new ExpVisitor(varDecl);
        a.getVariable().accept(idV);
	int sdId = ((Integer) idV.getResult()).intValue();

	if (sdId == LOW)
	    return a.clone();				// [Assign_low]
	return new Skip();				// [Assign_high]
    }

    /**
     * Determines the type of an <code>If</code>.
     * <p>
     * The type is either an If command with the types of the
     * contained programs (in case of [If_low]) or the sequential
     * composition of a skip and the types of ONE contained program
     * (in case of [If_high]). It is the type of only one, because the
     * if-command is assumed to be already transformed and thus the
     * types both programs are equal, which corresponds to the
     * sequential execution of both low slices.
     *
     * @param	i	an <code>If</code> command
     * @return		the type of i as an Object
     */
    protected Object getType (If i) {
	// First we get the security type of the conditional
	ExpVisitor boolV = new ExpVisitor(varDecl);
	i.getBoolExp().accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();

	// Then we get the two programs the if-command consists of
	Program ifProg = i.getIfProgram();
	Program elseProg = i.getElseProgram();
	
	if (sdBool == LOW) {				// [If_low]
	    // If we have a low conditional we clone the complete
	    // if-command and set the types of the programs as new
	    // programs.
	    If ifType = (If) i.clone();
	    ifType.setIfProgram((Program) getType(ifProg));
	    if (elseProg != null)
		ifType.setElseProgram((Program) getType(elseProg));
	    return ifType;
	} else {					// [If_high]
	    // If we have a high conditional we need to create a new
	    // program from the if-command. The program consists of
	    // a skip and the type of ONE transformed program (because
	    // the types of the two transformed programs are the
	    // same or should be at least, otherwise there is an
	    // error).
	    return new Program(new Skip(), (Program) getType(ifProg));
	}
    }

    /**
     * Determines the type of a <code>While</code>.
     *
     * @param	wh	a <code>While</code> command
     * @return		the type of wh as an Object
     */
    protected Object getType (While wh) {
	ExpVisitor boolV = new ExpVisitor(varDecl);
	wh.getBoolExp().accept(boolV);
	int sdBool = ((Integer) boolV.getResult()).intValue();

	While whType = null;
	if (sdBool == LOW) {				// [While_low]
	    whType = (While) wh.clone();
	    whType.setProgram((Program) getType(wh.getProgram()));
	}
	// [While_high] must be caught by TransformVisitor
	return whType;
    }

    /**
     * Determines the type of a <code>Fork</code>.
     *
     * @param	fk	a <code>Fork</code> command
     * @return		the type of fk as an Object
     */
    protected Object getType (Fork fk) {
	Fork fkType = (Fork) fk.clone();		// [Fork]

	Program prog = fk.getProgram();
	fkType.setProgram((Program) getType(prog));

	ProgramVector progVect= fk.getProgramVector();
	if (progVect != null) {
	    fkType.setProgramVector((ProgramVector) getType(progVect));
	}
	return fkType;
    }

    /**
     * Determines the type of a <code>Program</code>.
     *
     * @param	p	a <code>Program</code>
     * @return		the type of p as an Object
     */
    protected Object getType (Program p) {
	Program pType = (Program) p.clone();		// [Seq](implicitely)

	Program prog = pType.getProgram();
	if (prog != null)
	    pType.setProgram((Program) getType(prog));

	Command c = p.getCommand();
	if (c instanceof Skip)
	    pType.setCommand((Command) getType((Skip) c));
	else if (c instanceof VarDecl)
	    pType.setCommand((Command) getType((VarDecl) c));
	else if (c instanceof Assign)
	    pType.setCommand((Command) getType((Assign) c));
	else if (c instanceof While)
	    pType.setCommand((Command) getType((While) c));
	else if (c instanceof Fork)
	    pType.setCommand((Command) getType((Fork) c));
	else if (c instanceof If) {
	    Object o = getType((If) c);
	    if (o instanceof Command)
		pType.setCommand((Command) o);
	    else if (o instanceof Program) {
		// o may also be a Program (the type of an If_high
		// transformation)
		Program oP = (Program) o;
		pType.setCommand((Command) oP.getCommand());
		Program newP = oP.getProgram();
		newP.append(pType.getProgram());
		pType.setProgram((Program) newP);
	    }
	}
	else
	    System.err.println("TypeDeterminer: Unusual Command. "+
			       "Something is wrong");
	return pType;
    }

    /**
     * Determines the type of a <code>ProgramVector</code>.
     *
     * @param	pv	a <code>ProgramVector</code> command
     * @return		the type of pv as an Object
     */
    protected Object getType (ProgramVector pv) {
	ProgramVector pvType = (ProgramVector) pv.clone();	// [Par]

	Program prog = pv.getProgram();
	pvType.setProgram((Program) getType(prog));

	ProgramVector progVect= pv.getProgramVector();
	if (progVect != null) {
	    pvType.setProgramVector((ProgramVector) getType(progVect));
	}
	return pvType;
    }

    /**
     * This method will be called if none of the more specialized
     * <code>getType</code> methods could be called. This should never
     * happen actually.
     * <p> 
     * A method call has no effect on the result. The Object will be
     * returned unchanged and the check will be executed further.
     *
     * @param	o an <code>Object</code>
     */
    protected Object getType (Object o) {
	System.err.println("TypeDeterminer: This method should " + 
			   "never be called!");
	return o;
    }
}
