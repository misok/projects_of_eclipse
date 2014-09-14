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

/**Decl represents a general declaration. It is extended by concrete
 * MWL construct declarations, such as variable, array (or record)
 * declarations. This abstract class obliges all concrete declarations
 * to consist at least of an identifier, a type, and a security domain.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public abstract class Decl extends PrimitiveCommand {
    protected Identifier id;
    protected Type type;
    protected SecDomain secDom;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	id	the identifier
     * @param	type	the type
     * @param	secDom	the security domain
     */
    public Decl (Identifier id, Type type, SecDomain secDom) {
	this.id = id; 
	this.type = type; 
	this.secDom = secDom;
    }
    
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the declared identifier.
     *
     * @return the identifier
     */
    public Identifier getIdentifier () {
	return id;
    }

    /**
     * Getter method for the type.
     *
     * @return	the type
     */
    public Type getType () {
	return type;
    }
    
    /**
     * Getter method for the security domain.
     *
     * @return	the security domain
     */
    public SecDomain getSecDomain () {
	return secDom;
    }

    /**
     * Clones this instance.
     *
     * @return  the clone
     */
    public abstract Object clone ();

    /**
     * Represents this class as a string.
     *
     * @return  the string representation
     */
    public abstract String toString ();

    /**
     * Accept method for the visitor design pattern.
     *
     * @param   v   a visitor
     * @return  the result of the visitors visit method
     */
    public abstract Object accept (Visitor v);
}