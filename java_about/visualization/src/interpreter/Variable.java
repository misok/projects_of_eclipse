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

package interpreter;

import ast.*;

/**
 * Store runtime information about a variable
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class Variable {
	protected Identifier identifier = null;
	protected Type type = null;
	protected SecDomain secDomain = null;
	protected Object value = null;

	/**
	 * Creates a new variable and sets its vaule to the type default
	 * 
	 * @param identifier
	 *                Name of the variable
	 * @param type
	 *                Type of the variable
	 * @param secDomain
	 *                Secuirty domain of the variable
	 */
	public Variable(Identifier identifier, Type type, SecDomain secDomain) {
		this.identifier = identifier;
		this.type = type;
		this.secDomain = secDomain;
		value = getDefault(type);
	}

	/**
	 * Get the default value for a type, needs to be extended if new base types are to be added
	 * 
	 * @param type
	 *                Type
	 * @return Default value
	 */
	protected Object getDefault(Type type) {
		if (type instanceof IntegerType) {
			return new Integer(0);
		} else if (type instanceof BooleanType) {
			return new Boolean(false);
		} else {
			throw new RuntimeException("declared type is unknown");
		}
	}

	/**
	 * Get a reference to the identifier
	 * 
	 * @return reference to the identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * Get a reference to the type
	 * 
	 * @return reference to the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Get a reference to the security domain
	 * 
	 * @return reference to the security domain
	 */
	public SecDomain getSecDomain() {
		return secDomain;
	}

	/**
	 * Get a reference to the value of the variable
	 * 
	 * @return a reference to the value of the variable
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set the value of the variable to value
	 * 
	 * @param value new reference for the variable value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public String toString() {
		return value.toString();
	}
}
