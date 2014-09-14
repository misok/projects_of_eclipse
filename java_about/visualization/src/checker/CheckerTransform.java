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

import ast.Program;
import java.io.FileOutputStream;
import java.io.PrintStream;
import visitor.ElementPair;
import visitor.ToStringVisitor;
import visitor.TransformVisitor;
import visitor.Visitor;


/**
 * A security checker for MWL programs that checks programs for direct
 * and indirect information flow and timing leaks, potentially
 * transforming the program. If the result returned by the <code>check</code>
 * method is <code>true</code>, the program was already secure or a
 * transformation was found which makes the program secure.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class CheckerTransform extends Checker {
    //-------------------------------------------------------------------------
    // Constructor
    //-------------------------------------------------------------------------
    /** 
     * @param	file	the filename containing the MWL program
     * @throws	Exception  if the MWL program cannot be parsed
     */
    public CheckerTransform (String file) throws Exception {
	super(file);
    }
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /** 
     * Returns whether a program has secure information flow or can be
     * transformed into a secure program. 
     * The file containing the program was set in the constructor.
     *
     * @throws	CheckerException  if the program is null
     * @return  <code>true</code> if the program has secure
     *            information flow, <code>false</code> otherwise
     */
    public boolean check () throws CheckerException {
	if (super.root == null) {
	    throw new CheckerException("Abstract syntax tree is null");
	}

	Visitor visitor = new TransformVisitor(super.hMap);
	ElementPair ep = (ElementPair) super.root.accept(visitor);
	Program transfProg = (Program) ep.getElement();
	boolean result = ((Boolean) visitor.getResult()).booleanValue();
	if (result == true) 
	    writeTransformed(transfProg, "secure");

	return result;
    }

    //-------------------------------------------------------------------------
    // Private Methods
    //-------------------------------------------------------------------------

    private void writeTransformed (Program p, String appendix) {
	FileOutputStream out;		// a file output object
	PrintStream pStream;		// a print stream object

	Visitor stringVis = new ToStringVisitor();
	p.accept(stringVis);
	String s = (String) stringVis.getResult();
	//	System.out.println(s);
	try {
	    // Create a new file output stream
	    out = new FileOutputStream(super.filename+"_"+appendix);
	    
	    // Connect print stream to the output stream
	    pStream = new PrintStream(out);
	    pStream.println(s);
	    pStream.close();
	} catch (Exception e) {
	    System.err.println ("Error writing to file");
	}
    }
}
