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

/**ArrDecl represents an array declaration. An MWL array declaration
 * is of the form 'arr: type[l]: secDom', where 'arr' is the name of
 * the array (an <code>Identifier</code>), 'l' its length as an
 * identifier or an integer, 'type' the <code>Type</code> of its
 * contents, and 'secDom' the security domain of its contents.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class ArrDecl extends Decl {
    private Atom length;
    
    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	id	the identifier of the array
     * @param	l	its length
     * @param	type	the type of its contents
     * @param	secDom	the security domain of its contents
     */
    public ArrDecl (Identifier id, int l, Type type, SecDomain secDom) {
	super(id, type, secDom);
	this.length = new Int(l, l, null, false);
    }

    /** 
     * @param	id	the identifier of the array
     * @param	length	its length as an <code>Atom</code> (either an
     *			<code>Identifier</code> or an <code>Int</code>)
     * @param	type	the type of its contents
     * @param	secDom	the security domain of its contents
     */
    public ArrDecl (Identifier id, Atom length, Type type, SecDomain secDom) {
	super(id, type, secDom);
	this.length = length;
    }

    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------
    
    /**
     * Getter method for the length of the array.
     *
     * @return	the length of the array
     */
    public Atom getLength () {
	return length;
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>VarDecl</code>
     * instance 
     */
    public Object clone () {
        return new ArrDecl((Identifier) id.clone(),
			   (Atom) length.clone(),
			   (Type) type.clone(), 
			   (SecDomain) secDom.clone());  
    }

    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this array declaration
     */
    public String toString () {
	return id.toString()+" : "+type.toString()+"["+length.toString()+"] : "+secDom.toString(); 
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
