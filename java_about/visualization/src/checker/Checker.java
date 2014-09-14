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

package checker;

import ast.*;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java.util.HashMap;
import java.util.Map;
import mwlParser.MWLParser;
import mwlParser.MWLScanner;
import visitor.Visitor;



/**
 * Abstract super class of all security checkers.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public abstract class Checker {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    Map hMap;			// contains the variable declarations
				// key: variable name, value: Decl
    String filename;
    Program root;

    //-------------------------------------------------------------------------
    // Abstract Methods
    //-------------------------------------------------------------------------
    /** 
     * Returns whether a program has secure information flow or can
     * potentially have it after some transformations by applying
     * transformaton rules. The file containing the program was to be
     * set in the constructor. 
     *
     * @throws	CheckerException  if the program cannot be checked
     * @return  <code>true</code> if the program has secure
     *            information flow, <code>false</code> otherwise
     */
    public abstract boolean check () throws CheckerException;


    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------
    /** 
     * @param	file	the filename containing the MWL program
     * @throws	Exception  if the MWL program cannot be parsed
     */
    public Checker (String file) throws Exception {
	this.filename = file; 
	Scanner scanner = new MWLScanner(new java.io.FileInputStream(file));
	Symbol sym = new MWLParser(scanner).parse();
	root = (Program) sym.value;
	readDecls(root);
    }
    
    private Checker () {}
    
    //-------------------------------------------------------------------------
    // Private Methods
    //-------------------------------------------------------------------------

    /** 
     * Inserts all declarations into the local <code>Map</code>. It
     * traverses the AST tree program to find them. The variable name
     * is the key of the HashMap, <code>Decl</code> the value.
     *
     * @param  p a <code>Program</code> for which the variable
     *           declarations are to be found 
     */
    private void readDecls (Program p) {
	Command c;
	hMap = new HashMap();

	while (p != null) {
	    c = p.getCommand();
	    if ((c != null) && (c instanceof Decl)) {
		// insert this Decl into the Map hMap
		hMap.put(((Decl) c).getIdentifier().getName(), c);
	    }
	    p = p.getProgram();
	}
    }
}
