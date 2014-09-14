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
 * ProgramVector represents a vector of programs. This construct is
 * needed by the fork operatiorn. A <code>ProgramVector</code>
 * consists of the first program and the rest of the program vector.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class ProgramVector implements Cloneable, MwlElement {
    private Program prog;
    private ProgramVector progVect;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	prog	 the first program
     * @param	progVect the rest of the program vector
     */
    public ProgramVector (Program prog, ProgramVector progVect) {
	this.prog = prog;
	this.progVect = progVect;
    }
    
    /** 
     * If there is only one program in the vector of programs.
     *
     * @param	prog	 the first program
     */
    public ProgramVector (Program prog) {
	this.prog = prog;
	this.progVect = null;
    }

    private ProgramVector () {}

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the first program.
     *
     * @return	the first program
     */
    public Program getProgram () {
	return prog;
    }

    /**
     * Getter method for the program vector.
     *
     * @return	the program vector
     */
    public ProgramVector getProgramVector () {
	return progVect;
    }

    /**
     * Setter method for the first program.
     *
     * @param	prog	the first program
     */
    public void setProgram (Program prog) {
	this.prog = prog;
    }
    
    /**
     * Setter method for the program vector.
     *
     * @param	progVect the program vector
     */
    public void setProgramVector (ProgramVector progVect) {
	this.progVect = progVect;
    }
   
    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>ProgramVector</code>
     * instance 
     */
    public Object clone () {
	if (progVect != null)
	    return new ProgramVector ((Program) prog.clone(), 
				      (ProgramVector) progVect.clone());
	else	
	    return new ProgramVector ((Program) prog.clone());
    }
 
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this program vector
     */
    public String toString () {
	StringBuffer sb = new StringBuffer();
	if (prog != null) sb.append(prog.toString());
	if (progVect!=null) {
	    sb.append(","+progVect.toString());
	}
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
