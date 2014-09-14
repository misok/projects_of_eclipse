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
 * AbstractVisitor is an abstract class implementing the
 * <code>Visitor</code> interface. It is extended by {@link
 * ExpVisitor}, {@link ToStringVisitor}, {@link NoTransformVisitor},
 * and {@link TransformVisitor}.
 * <p> 
 * AbstractVisitor implements all defined <code>visit</code>
 * methods by empty bodies. This releases its subclasses from
 * implementing the visit method for <i>all</i> AST class instances,
 * even for those they do not deal with. 
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public abstract class AbstractVisitor implements Visitor {

    /**
     * @param	ae	an arithmetic expression
     * @return	null
     */
    public Object visit (ArithExp ae) { return null; }

    /**
     * @param   ad      an array declaration
     * @return  null
     */    
    public Object visit (ArrDecl ad) { return null; }
    
    /**
     * @param   af      an array field
     * @return  null
     */
    public Object visit (ArrField af) { return null; }
    
    /**
     * @param   al      an array length
     * @return  null
     */
    public Object visit (ArrLength al) { return null; }

	
    /**
     * @param	a	an assignment
     * @return	null
     */
    public Object visit (Assign a) { return null; }

    /**
     * @param	ce	a comparison expression
     * @return	null
     */
    public Object visit (CompExp ce) { return null; }

    /**
     * @param	f	a false truth value
     * @return	null
     */
    public Object visit (False f) { return null; }

    /**
     * @param	fk	a fork command
     * @return	null
     */
    public Object visit (Fork fk) { return null; }

    /**
     * @param	id	an identifier
     * @return	null
     */
    public Object visit (Identifier id) { return null; }

    /**
     * @param	i	an if command
     * @return	null
     */
    public Object visit (If i) { return null; }

    /**
     * @param	in	an integer
     * @return	null
     */
    public Object visit (Int in) { return null; }

    /**
     * @param	p	a program
     * @return	null
     */
    public Object visit (Program p) { return null; }

    /**
     * @param	pv	a program vector
     * @return	null
     */
    public Object visit (ProgramVector pv) { return null; }

    /**
     * @param	s	a skip command
     * @return	null
     */
    public Object visit (Skip s) { return null; }

    /**
     * @param	t	a true truth value
     * @return	null
     */
    public Object visit (True t) { return null; }

    /**
     * @param	vd	a variable declaration
     * @return	null
     */
    public Object visit (VarDecl vd) { return null; }

    /**
     * @param	w	a while command
     * @return	null
     */
    public Object visit (While w) { return null; }

    /**
     * @return the result of the visitor's anlysis
     */
    public abstract Object getResult ();
}
