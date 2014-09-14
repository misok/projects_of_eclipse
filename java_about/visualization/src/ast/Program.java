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
 * Program represents an MWL program which is composed of single
 * commands. A <code>Program</code> contains the first command of the
 * sequential composition and the rest of the program. If there is
 * only one command, then the rest of the program is null.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class Program implements Cloneable, MwlElement {
    private Command comm;
    private Program prog;
    
  
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	comm	the command
     * @param	prog	the program
     */
    public Program (Command comm, Program prog) {
	this.comm = comm;
	this.prog = prog;
    }
    
    /** 
     * If the program consists of a single command.
     *
     * @param	comm	the command
     */
    public Program (Command comm) {
	this.comm = comm;
	this.prog = null;
    }

    private Program () {}
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the command.
     *
     * @return	the command
     */
    public Command getCommand () {
	return comm;
    }
    
    /**
     * Getter method for the program.
     *
     * @return	the program
     */
    public Program getProgram () {
	return prog;
    }
    
    /**
     * Setter method for the command.
     *
     * @param	comm	the command
     */
    public void setCommand (Command comm) {
	this.comm = comm;
    }
    
    /**
     * Setter method for the program.
     *
     * @param	p	the program
     */
    public void setProgram (Program p) {
	prog = p;
    }

    /**
     * Appends a program to this program. This method is needed by the
     * transform visitor in case of a high conditional.
     *
     * @param	pTail	the program to be appended
     */
    public void append (Program pTail) {
	if (prog == null)
	    prog = pTail;
	else
	    prog.append(pTail); 
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>Program</code>
     * instance 
     */
    public Object clone () {
	if (prog != null) 
	    return new Program((Command) comm.clone(), 
			       (Program) prog.clone());
	else
	    return new Program((Command) comm.clone());
    }
 
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this arithmetic
     * expression 
     */
    public String toString () {
	StringBuffer s = new StringBuffer();
	if (comm != null) s.append(comm.toString()+"\n");    
	if (prog != null) s.append(prog.toString());
	return s.toString();
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
