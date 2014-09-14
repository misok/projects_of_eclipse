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
import interpreter.*;
import java.io.*;
import java.util.*;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import mwlParser.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import visitor.*;

/**
 * Implements the visualizer and the interpreter for the MWL Language
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class VisualizerTab extends AbstractTab {
	CodeView codeView = null;
	VariableView varView = null;
	TraceView traceView = null;
	MWLInterpreter interpreter = null;
	Parameters params = null;

	/**
	 * Create a new instance of a VisualizerTab
	 * 
	 * @param parent
	 *                Parent widget
	 * @param style
	 *                Style of the parent widget
	 */
	public VisualizerTab(Composite parent, int style) {
		super(parent, style);

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		setLayout(fillLayout);

		SashForm verticalSash = new SashForm(this, SWT.VERTICAL);
		SashForm horizontalSash = new SashForm(verticalSash, SWT.HORIZONTAL);

		Group tracesGroup = new Group(verticalSash, SWT.BORDER);
		tracesGroup.setText("Traces");
		tracesGroup.setLayout(fillLayout);
		traceView = new TraceView(tracesGroup, SWT.NONE);

		Group codeGroup = new Group(horizontalSash, SWT.BORDER);
		codeGroup.setText("Program");
		codeGroup.setLayout(fillLayout);
		codeView = new CodeView(codeGroup, SWT.NONE);

		Group varGroup = new Group(horizontalSash, SWT.BORDER);
		varGroup.setText("Variables");
		varGroup.setLayout(fillLayout);
		varView = new VariableView(varGroup, SWT.NONE);

		params = new Parameters();
	}

	public void copy() {
		codeView.copy();
	}

	public void cut() {
		codeView.cut();
	}

	public void paste() {
		codeView.paste();
	}

	public boolean close() {
		return true;
	}

	public void setCode(String program) {
		codeView.setText(program);
	}

	public String getCode() {
		return codeView.getText();
	}

	public void simulate() {
		Program program = null;
		try {
			Scanner scanner = new MWLScanner(new StringReader(codeView.getText()));
			Symbol sym = new MWLParser(scanner).parse();
			program = (Program) sym.value;
			params.variables = new Vector();
			program.accept(new UserVisitor(params.variables));

		} catch (Exception e) {
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage("An error occured while parsing the code: \n" + e);
			messageBox.open();
			e.printStackTrace();
			return;
		} catch (Error e) {
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage("An error occured while parsing the code: \n" + e);
			messageBox.open();
			e.printStackTrace();
			return;
		}

		RunDialog runDialog = new RunDialog(this.getShell(), SWT.NONE);
		runDialog.setParameters(params);
		if (runDialog.open() == null)
			return;

		reset();
		for (int i = 0; i < params.turns; i++) {
			MWLInterpreter interpreter = new MWLInterpreter(program, getScheduler(),
					new HashMap());
			Trace trace = new Trace(traceView, params);
			traceView.addTrace(trace);
			interpreter.addCommandListener(trace);

			try {
				while (interpreter.step() != null) {
				}
			} catch (Exception e) {
				MessageBox messageBox = new MessageBox(this.getShell(),
						SWT.ICON_ERROR);
				messageBox
						.setMessage("An error occured while executing the program: \n"
								+ e);
				messageBox.open();
				e.printStackTrace();
				return;
			}
		}
	}

	private Map readDecls(Program p) {
		Command c;
		HashMap hMap = new HashMap();

		while (p != null) {
			c = p.getCommand();
			if ((c != null) && (c instanceof Decl)) {
				// insert this Decl into the Map hMap
				hMap.put(((Decl) c).getIdentifier().getName(), c);
			}
			p = p.getProgram();
		}
		return hMap;
	}

	public void check() {
		try {
			Scanner scanner = new MWLScanner(new StringReader(codeView.getText()));
			Symbol sym = new MWLParser(scanner).parse();
			Program p = (Program) sym.value;

			Visitor noTransformVisitor = new NoTransformVisitor(readDecls(p));
			p.accept(noTransformVisitor);
			boolean resultNoTransform = ((Boolean) noTransformVisitor.getResult())
					.booleanValue();
			String result = "";
			if (resultNoTransform == false)
				result = "NOT ";
			MessageBox messageBox = new MessageBox(this.getShell(),
					SWT.ICON_INFORMATION);
			messageBox.setMessage("Program is " + result + "secure.");
			messageBox.open();
		} catch (Exception e) {
			MessageBox messageBox = new MessageBox(this.getShell(),
					SWT.ICON_INFORMATION);
			messageBox.setMessage("An error occured while transforming the program: \n"
					+ e);
			messageBox.open();
			e.printStackTrace();
		}
	}

	public void transform() {
		try {
			Scanner scanner = new MWLScanner(new StringReader(codeView.getText()));
			Symbol sym = new MWLParser(scanner).parse();
			Program p = (Program) sym.value;

			Visitor noTransformVisitor = new NoTransformVisitor(readDecls(p));
			p.accept(noTransformVisitor);
			boolean resultNoTransform = ((Boolean) noTransformVisitor.getResult())
					.booleanValue();
			if (resultNoTransform == true) {
				MessageBox messageBox = new MessageBox(this.getShell(),
						SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox
						.setMessage("Program is already secure. Apply the transformation?");
				if (messageBox.open() == SWT.NO)
					return;
			}

			Visitor transformVisitor = new TransformVisitor(readDecls(p));
			ElementPair ep = (ElementPair) p.accept(transformVisitor);
			Program transfProg = (Program) ep.getElement();
			boolean resultTransform = ((Boolean) transformVisitor.getResult())
					.booleanValue();
			if (resultTransform != true) {
				MessageBox messageBox = new MessageBox(this.getShell(),
						SWT.ICON_INFORMATION);
				messageBox.setMessage("Program can't be transformed.");
				messageBox.open();
				return;
			}

			Visitor stringVis = new ToStringVisitor();
			transfProg.accept(stringVis);
			codeView.setText((String) stringVis.getResult());
		} catch (Exception e) {

			MessageBox messageBox = new MessageBox(this.getShell(),
					SWT.ICON_INFORMATION);
			messageBox.setMessage("An error occured while transforming the program: \n"
					+ e);
			messageBox.open();
			e.printStackTrace();
		}
	}

	public void step() {
		if (interpreter == null) {
			reset();
			boolean parsed = parse();
			return;
		}
		try {
			interpreter.step();
		} catch (Exception e) {
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage("An error occured while executing the program: \n"
					+ e);
			messageBox.open();
			e.printStackTrace();
			interpreter = null;
		}
	}

	public void reset() {
		codeView.clear();
		varView.clear();
		traceView.clear();
		interpreter = null;
	}

	private boolean parse() {
		try {
			Scanner scanner = new MWLScanner(new StringReader(codeView.getText()));
			Symbol sym = new MWLParser(scanner).parse();
			interpreter = new MWLInterpreter((Program) sym.value, getScheduler(),
					new HashMap());
			interpreter.addCommandListener(codeView);
			Trace trace = new Trace(traceView, params);
			traceView.addTrace(trace);
			interpreter.addCommandListener(trace);
			interpreter.addVariableListener(varView);
			return true;
		} catch (Exception e) {
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage("An error occured while parsing the code: \n" + e);
			messageBox.open();
			e.printStackTrace();
			return false;
		} catch (Error e) {
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage("An error occured while parsing the code: \n" + e);
			messageBox.open();
			e.printStackTrace();
			return false;
		}
	}
}
