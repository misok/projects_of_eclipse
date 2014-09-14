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

/**
 * Visitor is an interface for executing the check logic on MWL abstract syntax
 * trees. It is part of the 'Visitor' design pattern and defines a method
 * <code>visit</code> for concrete AST classes.
 * <p>
 * The required method <code>getResult</code> returns the result of
 * running this visitor on the AST classes. The result is returned as
 * an Object. All required methods <code>visit</code> return an
 * <code>Object</code>, which may be null if there is no value of
 * interest to be returned.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public interface Visitor {
    // Constant declarations for a binary security domain
    /**
     * Representing the low security domain.
     */    
    public static final int LOW = 0;
    /**
     * Representing the high security domain.
     */    
    public static final int HIGH = 1;

    public Object visit (ArithExp ae);
    public Object visit (ArrDecl ad);
    public Object visit (ArrField af);
    public Object visit (ArrLength al);
    public Object visit (Assign a);
    public Object visit (CompExp ce);
    public Object visit (False f);
    public Object visit (Fork fk);
    public Object visit (Identifier id);
    public Object visit (If i);
    public Object visit (Int in);
    public Object visit (Program p);
    public Object visit (ProgramVector pv);
    public Object visit (Skip s);
    public Object visit (True t);
    public Object visit (VarDecl vd);
    public Object visit (While w);
    public Object getResult();
}
