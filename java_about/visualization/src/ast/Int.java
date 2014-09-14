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

import java.util.Random;

import visitor.Visitor;

/**
 * Int represents an integer.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
final public class Int extends Atom {
    private int number1, number2;
    private String description;
    private boolean user;
    static Random r = new Random();

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------
    
    /** 
     * @param	number	the integer
     * @param	user	can be set through the simulator
     */
    public Int (int number1, int number2, String description, boolean user) {
    	this.number1 = number1;
    	this.number2 = number2;
    	this.description = description;
    	this.user = user;
    }
    
    private Int () {}
  
    //-------------------------------------------------------------------------
    // Public Methods
    //-------------------------------------------------------------------------

    /**
     * Getter method for the integer.
     *
     * @return	the integer
     */
    public int getInt () {
    	if (number1 == number2)
    		return number1;
    	else	{
    		return r.nextInt(number2 - number1 + 1) + number1;
    	}	
    }
    
    /**
     * Setter method for the integer.
     *
     * @return	the integer
     */
    public void setInt (int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }   

    public String getContent () {
    	if (number1 == number2)
			return (new Integer(number1)).toString();
		else
			return number1 + ":" + number2;
    }
    
    /**
     * Getter method for the changeable setting.
     *
     * @return	changeable setting
     */
    public boolean getUser () {
        return user;
    }   
    
    /**
     * Getter method for the changeable setting.
     *
     * @return	changeable setting
     */
    public String getDescription () {
        return description;
    }  
    
    /**
     * Represents this class as a string.
     *
     * @return	the string representation of this integer.
     */
    public String toString () {
    	if (user)
    		if (number1 == number2)
    			return "user{" + description + ", " + number1 + "}";
    		else
    			return "user{" + description + ", " + number1 + ":" + number2 + "}";
    	else
    		return (new Integer(number1)).toString();
    }

    /**
     * Clones this instance.
     *
     * @return	the clone, which is a new <code>Int</code> instance 
     */
    public Object clone () {
	return new Int(number1, number2, description, user);
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
