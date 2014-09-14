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
import visitor.NoTransformVisitor;
import visitor.Visitor;


/**
 * A security checker for MWL programs that checks programs for direct
 * and indirect information flow without transforming them. The result
 * is returned by the <code>check</code> method.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class CheckerNoTransform extends Checker {
    //-------------------------------------------------------------------------
    // Constructor
    //-------------------------------------------------------------------------
    /** 
     * @param	file	the filename containing the MWL program
     * @throws	Exception  if the MWL program cannot be parsed
     */
    public CheckerNoTransform (String file) throws Exception {
	super(file);
    }
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /** 
     * Returns whether a program has secure information flow.
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
	Program p = super.root;

	Visitor visitor = new NoTransformVisitor(super.hMap);
	p.accept(visitor);
	return ((Boolean) visitor.getResult()).booleanValue();
    }
}
