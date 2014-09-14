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

import ast.MwlElement;

/**
 * ElementPair instances are the 'return values' of the transform
 * function: They consist of an <code>MwlElement</code> (the transformed
 * {@link ast.MwlElement}), its type (low slice) which is also an
 * <code>MwlElement</code> and a flag indicating whether there is an
 * assignment to a low variable in the transformed
 * <code>MwlElement</code>.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class ElementPair {
    //-------------------------------------------------------------------------
    // Constants and Declarations
    //-------------------------------------------------------------------------
    private MwlElement el;	// el represents a transformed MwlElement
    private MwlElement type;	// type represents the type (low
				// slice) of a transformed MwlElement  
    private boolean assToLow;	// if there is an assignment to low in
				// Element e1

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    /** 
     * @param	e1	a tranformed <code>MwlElement</code>
     *		e2	the type (low slice) of e1, also a
     *			<code>MwlElement</code> 
     *		assToL	if there is an assignment to a low variable in
     *			e1
     */
    public ElementPair (MwlElement e1, MwlElement e2, boolean assToL) {
	el = e1;
	type = e2;
	assToLow = assToL;
    }


    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------
    /**
     * Getter method for the MwlElement (the language construct, either a
     * command, a program, or a program vector).
     * 
     * @return	the transformed <code>MwlElement</code>
     */ 
    public MwlElement getElement () {
	return el;
    }
    
    /**
     * Getter method for the type of the MwlElement.
     * 
     * @return	the type (low slice) of the transformed
     *		<code>MwlElement</code> 
     */ 
    public MwlElement getType () {
	return type;
    }

    /**
     * Getter method for the assignment-to-low flag. It indicates whether there
     * is an assignment to a low variable in the MwlElement.
     * 
     * @return	the flag indicating an assignment to a low variable
     */ 
    public boolean getAlFlag () {
	return assToLow;
    }

    /**
     * Represents this class as a string.
     * 
     * @return	the String representation of this <code>ElementPair</code>
     */
    public String toString() {
	return "MWLElement: "+getElement()+"\n of type: "+getType()+
	       "\n AlFlag:"+getAlFlag();
    }    
}
