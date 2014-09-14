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

package visualizer;

import ast.*;
import java.util.*;
import visitor.*;

/**
 * Traverse the abstract syntax tree, and put references to all user
 * instructions into a Vector.
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class UserVisitor extends AbstractVisitor {
	private Vector ints = null;

	/**
	 * Create a new UserVisitor object
	 * 
	 * @param ints
	 *                Reference to a Vector object
	 */
	public UserVisitor(Vector ints) {
		this.ints = ints;
	}

	public Object visit(ArithExp ae) {
		Object o1 = ae.getExp1().accept(this);
		Object o2 = ae.getExp2().accept(this);
		return null;
	}

	public Object visit(ArrDecl ad) {
		ad.getLength().accept(this);
		return null;
	}

	public Object visit(ArrField af) {
		af.getIndex().accept(this);
		return null;
	}

	public Object visit(Assign a) {
		a.getExp().accept(this);
		return null;
	}

	public Object visit(CompExp ce) {
		Object o1 = ce.getExp1().accept(this);
		Object o2 = ce.getExp2().accept(this);
		return null;
	}

	public Object visit(Fork fk) {
		fk.getProgramVector().accept(this);
		return null;
	}

	public Object visit(If i) {
		i.getBoolExp().accept(this);
		i.getIfProgram().accept(this);
		if (i.getElseProgram() != null)
			i.getElseProgram().accept(this);
		return null;
	}

	public Object visit(Int i) {
		if (i.getUser())
			ints.add(i);
		return null;
	}

	public synchronized Object visit(Program p) {
		p.getCommand().accept(this);
		if (p.getProgram() != null)
			p.getProgram().accept(this);
		return null;
	}

	public Object visit(ProgramVector pv) {
		while (pv != null) {
			pv.getProgram().accept(this);
			pv = pv.getProgramVector();
		}
		return null;
	}

	public Object visit(While w) {
		w.getBoolExp().accept(this);
		w.getProgram().accept(this);
		return null;
	}

	public Object getResult() {
		return null;
	}
}
