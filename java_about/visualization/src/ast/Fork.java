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
 * Fork represents a fork command, which generates a vector of
 * programs to be executed in parallel to a main program. 
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class Fork extends ComposedCommand  {
    private Program prog;
    private ProgramVector progVect;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	prog	 the main program
     * @param	progVect the vector of programs
     */
    public Fork (Program prog, ProgramVector progVect) {
	this.prog = prog; 
	this.progVect = progVect;
    }

    private Fork () {}
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the main program.
     *
     * @return	the program
     */
    public Program getProgram () {
	return prog;
    }
    
    /**
     * Getter method for the vector of programs.
     *
     * @return	the program vector
     */
    public ProgramVector getProgramVector () {
	return progVect;
    }
    
    /**
     * Setter method for the main program.
     *
     * @param	prog	the program
     */
    public void setProgram (Program prog) {
	this.prog = prog;
    }
    
    /**
     * Setter method for the vector of programs.
     *
     * @param	progVect the program vector
     */
    public void setProgramVector (ProgramVector progVect) {
	this.progVect = progVect;
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>Fork</code> instance
     */
    public Object clone () {
	if (progVect != null)  
	    return new Fork((Program) prog.clone(), 
			    (ProgramVector) progVect.clone());
	else
	    return new Fork((Program) prog.clone(), null);
    }
    
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of fork command
     */
    public String toString () {
	StringBuffer sb = new StringBuffer();
	if (prog != null) sb.append(prog.toString());
	if (progVect != null) {
	    sb.append("<");
	    sb.append(progVect.toString());
	    sb.append(">");
	}
	return "fork("+sb.toString()+")";
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
