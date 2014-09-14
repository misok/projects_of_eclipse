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
import visitor.*;

/**
 * A Visitor for the abstract syntax tree, that calculates the duration of a
 * command. The timing are taken from a Parameter Object.
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class DurationVisitor extends AbstractVisitor {
	private Parameters params = null;
	private int result = 0;

	/**
	 * Create a new DurationVisitor instance
	 * 
	 * @param params
	 *                A parameter object with timings for the commands and
	 *                operators
	 */
	public DurationVisitor(Parameters params) {
		super();
		this.params = params;
	}

	public Object visit(ArithExp ae) {
		int left = ((Integer) ae.getExp1().accept(this)).intValue();
		int right = ((Integer) ae.getExp2().accept(this)).intValue();
		int op = 0;
		Operator operator = ae.getOperator();
		if (operator instanceof Plus || operator instanceof Minus)
			op = params.durations[params.PLUS_MINUS];
		else if (operator instanceof Times || operator instanceof Div)
			op = params.durations[params.TIMES_DIV];
		else if (operator instanceof Modulus)
			op = params.durations[params.MOD];
		else
			throw new RuntimeException("DurationVisitor: Unknown ArithExp");

		return new Integer(left + op + right);
	}

	public Object visit(ArrDecl ad) {
		result = 0;
		return null;
	}

	public Object visit(ArrField af) {
		return new Integer(0);
	}

	public Object visit(ArrLength al) {
		return new Integer(0);
	}

	public Object visit(Assign a) {
		int expr = ((Integer) a.getExp().accept(this)).intValue();
		result = expr + params.durations[params.ASSIGN];
		return null;
	}

	public Object visit(CompExp ce) {
		int left = ((Integer) ce.getExp1().accept(this)).intValue();
		int right = ((Integer) ce.getExp2().accept(this)).intValue();
		int op = params.durations[params.BOOL_OP];
		return new Integer(left + op + right);
	}

	public Object visit(False f) {
		return new Integer(0);
	}

	public Object visit(Fork fk) {
		result = params.durations[params.FORK];
		return null;
	}

	public Object visit(Identifier id) {
		return new Integer(0);
	}

	public Object visit(If i) {
		int expr = ((Integer) i.getBoolExp().accept(this)).intValue();
		result = expr + params.durations[params.IF];
		return null;
	}

	public Object visit(Int in) {
		return new Integer(0);
	}

	public Object visit(Skip s) {
		result = params.durations[params.SKIP];
		return null;
	}

	public Object visit(True t) {
		return new Integer(0);
	}

	public Object visit(VarDecl vd) {
		result = 0;
		return null;
	}

	public Object visit(While w) {
		int expr = ((Integer) w.getBoolExp().accept(this)).intValue();
		result = expr + params.durations[params.WHILE];
		return null;
	}

	/**
	 * Get the result from the last Visitor run
	 * 
	 * @return the duration of the visited command
	 */
	public Object getResult() {
		return new Integer(result);
	}
}
