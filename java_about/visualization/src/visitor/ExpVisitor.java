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
 * ExpVisitor implements the check logic for expressions, determining
 * the security domain of an expression. It realizes implicitely the
 * following rules:  
 * <ul>
 *   <li>[Arith_low]</li>
 *   <li>[Arith_high1]</li>
 *   <li>[Arith_high2]</li>
 *   <li>[Exp]</li>
 * </ul>
 * The basic idea is: In the beginning, we assume each expression to
 * be of security domain LOW, i.e. the result is set to LOW. For each
 * expression and expression part (such as <code>Identifier</code>s,
 * <code>Int</code>s etc.) we encounter we try to apply the security
 * check rules. Once the result is HIGH, it remains HIGH till the end
 * of the execution.
 * Each <code>visit</code> method returns <code>null</code> because
 * the return value is of no interest, only the result variable
 * containing the security domain counts.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class ExpVisitor extends AbstractVisitor {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private static final int LOW = Visitor.LOW;
    private static final int HIGH = Visitor.HIGH;

    private int secDomResult;
    private Map decls;


    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	decls	a Map containing the declarations for the 
     *			program to be visited
     */
    public ExpVisitor (Map decls) {
	secDomResult = LOW;
	this.decls = decls;
    }


    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Returns an Object containing the result of the analysis.
     *
     * @return an Integer instance containing int HIGH if the
     *	       expression is of security domain high and int LOW
     *	       otherwise.
     */
    public Object getResult() {
	return new Integer(secDomResult);
    }

    /**
     * Modifies the result according to the security domain of this
     * <code>True</code> truth value.
     *
     * @param	t  a <code>True</code> truth value
     * @return	null
     */
    public Object visit (True t) {
	setSecDom(LOW);
	return null;
    }

    /**
     * Modifies the result according to the security domain of this
     * <code>False</code> truth value.
     *
     * @param	f  a <code>False</code> truth value
     * @return	null
     */
    public Object visit (False f) {
	setSecDom(LOW);
	return null;
    }

    /**
     * Modifies the result according to the security domain of this
     * <code>Int</code>.
     *
     * @param	i  an <code>Int</code>
     * @return	null
     */
    public Object visit (Int i) {
	setSecDom(LOW);
	return null;
    }

    /**
     * Modifies the result according to the security domain of this
     * <code>ArrLength</code> call.
     *
     * @param	al  an <code>ArrLength</code>
     * @return	null
     */
    public Object visit (ArrLength al) {
	// In the current used type systems, the length of an array is
	// always public and thus has security domain low.
	setSecDom(LOW);
	return null;
    }

    /**
     * Modifies the result according to the security domain of this
     * <code>Identifier</code>.
     *
     * @param	id  an <code>Identifier</code>
     * @return	null
     */
    public Object visit (Identifier id) {
	String name = id.getName();
	Decl decl = (Decl) decls.get(name);

	// Check whether the identifier has actually been declared as
	// a variable
	if (! (decl instanceof VarDecl)) {
	    System.err.println("ERROR: Identifier "+name+" is "+
			       "not declared as identifier!"+
			       " Aborting check.");
	    System.exit(1);
	}

	// set the security domain according to the security domain of
	// this variable
	if (decl != null) 
	    setSecDom(decl.getSecDomain());
	else {
	    System.err.println("WARNING: Variable "+name+
			       " is not declared! Security Domain"+
			       " is set to HIGH for "+name);
	    setSecDom(HIGH);
	}
	return null;
    }

    /**
     * Modifies the result according to the security domain of this
     * <code>ArrField</code>.
     *
     * @param   af  an <code>ArrField</code>
     * @return  null
     */
    public Object visit (ArrField af) {
        String name = af.getName();
	Decl decl = (Decl) decls.get(name);

	// Check whether the identifier has actually been declared as
	// array, respectively has been declared at all. This catches
	// also a null decl.
	if (! (decl instanceof ArrDecl)) {
	    System.err.println("ERROR: Identifier "+name+" is "+
			       "not declared as array!"+
			       " Aborting check.");
	    System.exit(1);
	}

	// Set the security domain according to the security domain of
	// the index and of the contents of the array.
	// Array: H | H | L | L
	// Index: H | L | H | L
	// --------------------
	// result:H | H |XXX| L
	ExpVisitor indexV = new ExpVisitor(decls);
	Exp index = af.getIndex();
	index.accept(indexV);
	int sdIndex = ((Integer) indexV.getResult()).intValue();

	SecDomain sdArray = decl.getSecDomain();
	if (sdArray instanceof HighSec)
	    setSecDom(HIGH);
	else {
	    if (sdIndex == LOW)
		setSecDom(LOW);
	    else {
		System.out.println(af+" has a high index but the array "+
				   "is low, which is forbidden by the "+
				   "security policy.");
		System.out.println("==> The program is NOT secure! It "+
				   "cannot be transformed into a secure one.");
		System.exit(0);
	    }
	}

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
	ae.getExp1().accept(this);
	ae.getExp2().accept(this);
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
	ce.getExp2().accept(this);
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
	System.out.println("ExpVisitor: This Method should never be called!");
	return null;
    }

    /**
     * Returns the Map with the variable declarations.
     *
     * @return	a Map containing the variable declarations.
     */
    public Map getDecls () {
	return decls;
    }


    //-------------------------------------------------------------------------
    // Private Methods
    //-------------------------------------------------------------------------

    /** 
     * Sets the secDomResult variable depending on its current value
     * and a security domain of a new Exp.
     *
     * @param	int  the security domain found in the new Exp
     */
    private void setSecDom(int sd) {
	if ((secDomResult == HIGH) || (sd == HIGH))
	    secDomResult = HIGH;
	else
	    secDomResult = LOW;
    }

    /** 
     * Calls the setSecDom(int) method with the appropriate parameter
     * HIGH or LOW depending on the class of sd.
     *
     * @param	sd  the security domain of an identifier
     */
    private void setSecDom(SecDomain sd) {
	if (sd instanceof HighSec)
	    setSecDom(HIGH);
	else
	    setSecDom(LOW);
    }
    
    /** 
     * Returns the security domain of an expression that is composed
     * of two parts, whose security domains are given.
     *
     * @param	sd1  the security domain of the first expression part
     *		sd2  the security domain of the second expression part
     * @return	int  the security domain of an expression composed of
     *		     these two parts.
     */
    private int getCombSecDomain(int sd1, int sd2) {
	if ((sd1 == HIGH) || (sd2 == HIGH))
	    return HIGH;
	return LOW;
    }
}
