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
import java.io.*;
import java.util.*;
import visitor.*;

/**
 * Interpreter for the MWL Language
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class MWLInterpreter implements Visitor {
	private Map variables = null;
	private Scheduler scheduler = null;
	private Vector threadStacks = null;
	private Vector threadIDs = null;
	private LinkedList variableListeners = null;
	private LinkedList commandListeners = null;
	private int currThread = 0;
	private int nextID = 0;

	/**
	 * Create an MWLinterpreter object, if called with program = null and scheduler = null, it
	 * can be used to evaluate expressions with variables
	 * 
	 * @param program
	 *                the MWL program to be evaluated
	 * @param scheudler
	 *                the scheudler to be used for evaluating multi threaded programs
	 * @param variables
	 *                a map that contains the state of the variables
	 */
	public MWLInterpreter(Program program, Scheduler scheudler, Map variables) {
		this.variables = variables;
		this.scheduler = scheudler;

		threadStacks = new Vector();
		threadIDs = new Vector();

		if (program != null)
			addThread(program);

		commandListeners = new LinkedList();
		variableListeners = new LinkedList();
	}

	/**
	 * Registers a variable listener, this event will be thrown whenever a variable is declared
	 * or modified
	 * 
	 * @param variableListener
	 *                The Listener to be registered
	 */
	public void addVariableListener(VariableListener variableListener) {
		variableListeners.add(variableListener);
	}

	/**
	 * Register a command listener, this event will be thrown whenever a command is executed
	 * 
	 * @param commandListener
	 *                The Listener to be registered
	 */
	public void addCommandListener(CommandListener commandListener) {
		commandListeners.add(commandListener);
	}

	private void sendVariableEvent(Variable variable) {
		for (Iterator iter = variableListeners.iterator(); iter.hasNext();) {
			VariableListener vl = (VariableListener) (iter.next());
			vl.handleEvent(variable);
		}
	}

	private void sendCommandEvent(Command cmd, Map vars, int currThreads, int numThreads) {
		for (Iterator iter = commandListeners.iterator(); iter.hasNext();) {
			CommandListener cl = (CommandListener) (iter.next());
			cl.handleEvent(cmd, vars, currThreads, numThreads);
		}
	}

	private void putOnStack(int thread, Program program) {
		if (program != null)
			((Stack) threadStacks.get(thread)).push(program);
	}

	private void addThread(Program program) {
		Stack stack = new Stack();
		stack.push(program);
		threadStacks.add(stack);
		threadIDs.add(new Integer(nextID));
		nextID++;
		scheduler.setNumThreads(threadStacks.size());
	}

	private int getCurrThreadID() {
		Integer i = (Integer) threadIDs.get(currThread);
		return i.intValue();
	}

	/**
	 * Execute the next instruction of the program
	 * 
	 * @return the command executed in this step, or null if program is finished
	 */
	public Command step() throws Exception {
		// repeat as long as threads are available
		while (!threadStacks.isEmpty()) {
			currThread = scheduler.getNextThread();
			// get the rest of the program, if not available finish the thread
			Program program;
			Stack stack = (Stack) (threadStacks.get(currThread));
			try {
				program = (Program) stack.pop();
			} catch (EmptyStackException e) {
				// remove the thread
				threadStacks.remove(currThread);
				threadIDs.remove(currThread);
				scheduler.setNumThreads(threadStacks.size());
				continue;
			}

			// execute the next command
			Command cmd = (Command) program.accept(this);

			// send command event to all listeners
			sendCommandEvent(cmd, variables, getCurrThreadID(), scheduler
					.getNumThreads());

			// return the executed command and finish this step
			return cmd;
		}
		return null;
	}

	/**
	 * Evaluates an arithmetic expression
	 * 
	 * @param ae
	 *                the arithmetic expression to evaluated
	 * 
	 * @return an object corresponding to the type of the arithmetic expression
	 */
	public Object visit(ArithExp ae) {
		Operator operator = ae.getOperator();
		Object o1 = ae.getExp1().accept(this);
		Object o2 = ae.getExp2().accept(this);

		if (o1 instanceof Integer && o2 instanceof Integer) {

			int i1 = ((Integer) o1).intValue();
			int i2 = ((Integer) o2).intValue();

			if (operator instanceof Plus) {
				return new Integer(i1 + i2);
			} else if (operator instanceof Minus) {
				return new Integer(i1 - i2);
			} else if (operator instanceof Times) {
				return new Integer(i1 * i2);
			} else if (operator instanceof Div) {
				return new Integer(i1 / i2);
			} else if (operator instanceof Modulus) {
				return new Integer(i1 % i2);
			} else
				throw new RuntimeException("Unknown ArithExp");
		}
		throw new RuntimeException("Unhandled type used in ArithExp");
	}

	/**
	 * Evaluates an array declaration by adding an entry for the new variable into the map
	 * containing the state of variables
	 * 
	 * @param ad
	 *                the array delcation to be evaluated
	 * 
	 * @return always null
	 */
	public Object visit(ArrDecl ad) {
		// check if variable is already defined
		Variable tmp = (Variable) variables.get(ad.getIdentifier().getName());
		if (tmp != null)
			throw new RuntimeException("variable already declared");
		
		// get length of array, create new array and store it
		int length = ((Integer) ad.getLength().accept(this)).intValue();
		Array array = new Array(ad.getIdentifier(), length, ad.getType(), ad.getSecDomain());
		variables.put(ad.getIdentifier().getName(), array);

		// notify listeners
		sendVariableEvent(array);

		return null;
	}

	/**
	 * Evaluate an array field expression
	 * 
	 * @param af
	 *                the array field to be evaluated
	 * 
	 * @return an object of the same type as the array field expression containing the value of
	 *         the given array field
	 */
	public Object visit(ArrField af) {
		// get index and type check it
		int index = 0;
		try {
			index = ((Integer) af.getIndex().accept(this)).intValue();
		} catch (ClassCastException ce) {
			throw new RuntimeException("array index must be of integer type");
		}
		
		// retrieve array from memory
		Array arr = (Array) variables.get(af.getName());
		
		// throw exception if array is not found
		if (arr == null)
			throw new RuntimeException("array not declared");
		
		// return value of selected index
		return arr.getIndex(index);
	}

	/**
	 * Evaluate the length of an array
	 * 
	 * @return an Integer object containing the length of the array
	 */
	public Object visit(ArrLength al) {
		// retrieve array from memory
		Array arr = (Array) variables.get(al.getName());
		
		// throw exception if array is not found
		if (arr == null)
			throw new RuntimeException("array not declared");
		
		// get the length
		try {
			Object[] obj = (Object[]) arr.getValue();
			return new Integer(obj.length);
		} catch (ClassCastException e) {
			throw new RuntimeException("can get length from non array types");
		}
	}

	/**
	 * Execute an assign instruction: evaluate the expression on the right hand side, and put
	 * the result in the variable at the left hand side
	 * 
	 * @param a
	 *                an assignment instruction
	 * 
	 * @return always null
	 */
	public Object visit(Assign a) {
		Object obj = (Object) a.getExp().accept(this);
		//if (!(obj instanceof Integer))
		//	throw new RuntimeException("Assigned a non-Integer Expression");

		Assignable assignalbe = (Assignable) a.getVariable();
		Variable var = (Variable) variables.get(assignalbe.getName());
		if (assignalbe instanceof Identifier) {
			//int i = ((Integer) obj).intValue();
			//var.setValue(new Integer(i));
			var.setValue(obj);

		} else if (assignalbe instanceof ArrField) {
			ArrField af = (ArrField) assignalbe;
			Integer i = (Integer) af.getIndex().accept(this);
			Array arr = (Array) var;
			arr.setIndex(i.intValue(), obj);
		}

		// notify listeners
		sendVariableEvent(var);
		
		return null;
	}

	/**
	 * Evaluate a Comparator Expression that will evalute to a boolean value
	 * 
	 * @param ce
	 *                a comparator expression
	 * 
	 * @return a Boolean object representing the value of the expression
	 */
	public Object visit(CompExp ce) {
		BoolOp operator = ce.getBoolOp();
		Object o1 = ce.getExp1().accept(this);
		Object o2 = ce.getExp2().accept(this);

		// both expressions must be of the same type
		if (o1 instanceof Integer && o2 instanceof Integer) {

			int i1 = ((Integer) o1).intValue();
			int i2 = ((Integer) o2).intValue();

			if (operator instanceof EqualEqual)
				return new Boolean(i1 == i2);
			else if (operator instanceof NotEqual)
				return new Boolean(i1 != i2);
			else if (operator instanceof Greater)
				return new Boolean(i1 > i2);
			else if (operator instanceof GreaterEqual)
				return new Boolean(i1 >= i2);
			else if (operator instanceof Less)
				return new Boolean(i1 < i2);
			else if (operator instanceof LessEqual)
				return new Boolean(i1 <= i2);
			else
				throw new RuntimeException("unknown CompExp");

		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {

			boolean b1 = ((Boolean) o1).booleanValue();
			boolean b2 = ((Boolean) o2).booleanValue();

			if (operator instanceof And)
				return new Boolean(b1 && b2);
			else if (operator instanceof Or)
				return new Boolean(b1 || b2);
			else if (operator instanceof EqualEqual)
				return new Boolean(b1 == b2);
			else if (operator instanceof NotEqual)
				return new Boolean(b1 != b2);
			else
				throw new RuntimeException("unknown CompExp");

		} else
			throw new RuntimeException("Comparison of incompatible types");
	}

	/**
	 * The false expression will always evaluate to false
	 * 
	 * @param f
	 *                false expression to be evaluated
	 * 
	 * @return a Boolean object of value false
	 */
	public Object visit(False f) {
		return new Boolean(false);
	}

	/**
	 * Execute a fork instruction, first create all threads in the program vector, then execute
	 * the main program
	 * 
	 * @param fk
	 *                the fork instruction to be executed
	 * 
	 * @return always null
	 */
	public Object visit(Fork fk) {
		// visit the ProgramVector Object that adds new threads
		fk.getProgramVector().accept(this);
		// finally execute the program of this thread
		putOnStack(currThread, fk.getProgram());
		return null;
	}

	/**
	 * Evaluate an identifier
	 * 
	 * @param id
	 *                Identifier object
	 * 
	 * @return value of the identifier
	 */
	public Object visit(Identifier id) {
		// get variable from memory
		Variable var = (Variable) variables.get(id.getName());

		// throw exception if variable is not found
		if (var == null)
			throw new RuntimeException("variable not declared");

		// return the value
		return var.getValue();
	}

	/**
	 * An if instrcution will evaluate its condition and places the correct branch on the stack
	 * 
	 * @param If
	 *                object to be excuted
	 * @return null
	 */
	public Object visit(If i) {
		Boolean b = (Boolean) i.getBoolExp().accept(this);
		if (b.booleanValue())
			putOnStack(currThread, i.getIfProgram());
		else
			putOnStack(currThread, i.getElseProgram());
		return null;
	}

	/**
	 * An Int object evalutes to its value
	 * 
	 * @param Int
	 *                object to evaluted
	 * @return an Integer object with the same value as the Int object
	 */
	public Object visit(Int in) {
		return new Integer(in.getInt());
	}

	/**
	 * Given a program, execute the first command, but before save the rest of the program on
	 * the stack
	 * 
	 * @param p
	 *                the program to be executed
	 * @return always null
	 */
	public Object visit(Program p) {
		putOnStack(currThread, p.getProgram());
		Command cmd = p.getCommand();
		try {
			// execute the command
			cmd.accept(this);
		} catch (Exception e) {
			// add line number information to error message
			throw new RuntimeException("Error on line(" + cmd.getLineNumber() + "): "
					+ e.getMessage());
		}

		return cmd;
	}

	/**
	 * A program vector will spawn all his threads when executed
	 * 
	 * @param pv
	 *                ProgramVector Object
	 * @return null
	 */
	public Object visit(ProgramVector pv) {
		while (pv != null) {
			addThread(pv.getProgram());
			pv = pv.getProgramVector();
		}
		return null;
	}

	/**
	 * Skip instructions (does nothing)
	 * 
	 * @param s
	 *                Skip object
	 * @return null
	 */
	public Object visit(Skip s) {
		return null;
	}

	/**
	 * A True object always evaluates to true
	 * 
	 * @param t
	 *                True object
	 * @return a Boolean object with the value true
	 */
	public Object visit(True t) {
		return new Boolean(true);
	}

	/**
	 * Execute a variable declaration, a new variable is created and initialized with a default
	 * value, then a variable event is sent to all listeners
	 * 
	 * @param vd
	 *                The variable declaration
	 * @return null
	 */
	public Object visit(VarDecl vd) {
		// check if variable is already defined
		Variable tmp = (Variable) variables.get(vd.getIdentifier().getName());
		if (tmp != null)
			throw new RuntimeException("variable already declared");

		// create new variable and store it
		Variable variable = new Variable(vd.getIdentifier(), vd.getType(), vd
				.getSecDomain());
		variables.put(vd.getIdentifier().getName(), variable);

		// send event to all listeners
		sendVariableEvent(variable);

		return null;
	}

	/**
	 * Execute one loop of a while instruction, first the condition will be evaluated, if it
	 * evaluates to true, then the while instruction is placed on the stack, afterwards the
	 * inner loop is put on the stack
	 * 
	 * @param w
	 *                The while loop to be excuted
	 * @return null
	 */
	public Object visit(While w) {
		Boolean b = (Boolean) w.getBoolExp().accept(this);
		if (b.booleanValue()) {
			putOnStack(currThread, new Program(w));
			putOnStack(currThread, w.getProgram());
		}
		return null;
	}

	/**
	 * Unused
	 * 
	 * @return null
	 */
	public Object getResult() {
		return null;
	}
}
