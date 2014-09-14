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
 * VarDecl represents a variable declaration. A variable declaration
 * is of the form 'id: type: secDom' where 'id' is an
 * <code>Identifier</code>, 'type' its <code>Type</code>, and
 * 'secDom' its <code>SecDomain</code>.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class VarDecl extends Decl {
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	id	the identifier
     * @param	type	the type
     * @param	secDom	the security domain
     */
    public VarDecl(Identifier id, Type type, SecDomain secDom) {
	super(id, type, secDom);
    }

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>VarDecl</code>
     * instance 
     */
    public Object clone () {
        return new VarDecl((Identifier) id.clone(), 
			   (Type) type.clone(), 
			   (SecDomain) secDom.clone());  
    }

    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this variable declaration
     */
    public String toString () {
	return id.toString()+" : "+type.toString()+" : "+secDom.toString();
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
