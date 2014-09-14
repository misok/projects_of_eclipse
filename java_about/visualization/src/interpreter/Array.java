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
 * Store runtime information about an array
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class Array extends Variable {

	/**
	 * Creates a new array and sets all of its elements to the default value for the given type
	 * 
	 * @param identifier
	 *                The identifier of the represented variable
	 * @param length
	 *                The length of the array
	 * @param type
	 *                The type of the array elements
	 * @param secDomain
	 *                The security domain of the array
	 */
	public Array(Identifier identifier, int length, Type type, SecDomain secDomain) {
		super(identifier, type, secDomain);
		if (length < 0)
			throw new RuntimeException("declared array of negative size");
		value = new Object[length];
		for (int i = 0; i < length; i++)
			((Object[]) value)[i] = getDefault(type);
	}

	/**
	 * Get a reference to the object at position of the index
	 * 
	 * @param index
	 *                position of the requested element
	 * @return the element if the index is valid else the default value of that type
	 */
	public Object getIndex(int index) {
		// if index is out of bounds return the default value for the type
		if (index < 0 || index >= ((Object[]) value).length)
			return getDefault(type);
		else
			return ((Object[]) value)[index];
	}

	/**
	 * Set a reference to the object at position of the index
	 * 
	 * @param index
	 *                position of the requested element
	 * @param value
	 *                the element to be placed at the position of the index in the array
	 */
	public void setIndex(int index, Object value) {
		// if index is out of bounds return
		if (index < 0 || index >= ((Object[]) this.value).length)
			return;
		else
			((Object[]) this.value)[index] = value;
	}

	public String toString() {
		Object[] objects = ((Object[]) this.value);
		if (objects.length == 0)
			return "";
		String tmp = new String(objects[0].toString());
		for (int i = 1; i < objects.length; i++)
			tmp += ", " + objects[i].toString();
		return tmp;
	}
}
