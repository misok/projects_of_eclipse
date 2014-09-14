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
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * This class create a dialog that allows to enter visualization parameters. These value are put
 * into a Parameter object
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class RunDialog extends Dialog {
	Shell shell = null;
	Parameters params = null;
	Text turnsText = null;
	Object result = null;

	/**
	 * Create new RunDialog with parent window and style settings
	 * 
	 * @param parent Parent of the dialog box
	 * @param style Style of the dialog box
	 */
	public RunDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Set the parameter object to fill in the settings
	 * 
	 * @param params Reference to the Parameter object
	 */
	public void setParameters(Parameters params) {
		this.params = params;
	}

	/**
	 * Parse a string of format m:n
	 * 
	 * @param integer Integer object to fill in
	 * @param s String to be parsed
	 */
	public void getUserInt(Int integer, String s) {
		int middle = s.indexOf(':');
		if (middle == -1) {
			int number = Integer.parseInt(s);
			integer.setInt(number, number);
		} else {
			int number1 = Integer.parseInt(s.substring(0, middle));
			int number2 = Integer.parseInt(s.substring(middle + 1));
			integer.setInt(number1, number2);
		}
	}

	/**
	 * Launch the dialog
	 * 
	 * @return a reference to the settings of null if canceled
	 */
	public Object open() {
		// get reference to the parent window and create new shell
		Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText("Visualization Properties");

		// use a grid layout
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = true;
		shell.setLayout(layout);

		// turns label and text edit
		Label turnsLabel = new Label(shell, SWT.NONE);
		turnsLabel.setText("Turns:");
		turnsLabel.setLayoutData(new GridData(GridData.BEGINNING));

		turnsText = new Text(shell, SWT.NONE);
		turnsText.setText(new Integer(params.turns).toString());
		GridData turnsGridData = new GridData(GridData.FILL_HORIZONTAL);
		turnsGridData.horizontalSpan = 2;
		turnsText.setLayoutData(turnsGridData);

		// create group fields
		Group variablesGroup = new Group(shell, SWT.NONE);
		variablesGroup.setText("&Variables");
		variablesGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		variablesGroup.setLayout(new FillLayout(SWT.FILL));

		Group securityGroup = new Group(shell, SWT.NONE);
		securityGroup.setText("&Security");
		securityGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		securityGroup.setLayout(new FillLayout(SWT.FILL));

		Group timingGroup = new Group(shell, SWT.NONE);
		timingGroup.setText("&Timing");
		timingGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		timingGroup.setLayout(new FillLayout(SWT.FILL));

		// variable declaration table
		final Table variablesTable = new Table(variablesGroup, SWT.SINGLE);
		variablesTable.setHeaderVisible(true);
		variablesTable.setVisible(true);
		TableColumn variable = new TableColumn(variablesTable, SWT.LEFT);
		variable.setText("Variable");
		TableColumn value = new TableColumn(variablesTable, SWT.LEFT);
		value.setText("Value");
		for (Iterator iter = params.variables.iterator(); iter.hasNext();) {
			TableItem item = new TableItem(variablesTable, SWT.LEAD);
			Int i = (Int) iter.next();
			item.setText(0, i.getDescription());
			item.setText(1, i.getContent());
		}
		variable.pack();
		value.pack();

		final TableEditor variablesEditor = new TableEditor(variablesTable);
		// The editor must have the same size as the cell and must
		// not be any smaller than 50 pixels.
		variablesEditor.horizontalAlignment = SWT.LEFT;
		variablesEditor.grabHorizontal = true;
		variablesEditor.minimumWidth = 50;
		// editing the second column
		final int VARIABLES_EDITABLECOLUMN = 1;

		variablesTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = variablesEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				TableItem item = (TableItem) e.item;
				if (item == null)
					return;

				// The control that will be the editor must be a
				// child of the Table
				Text newEditor = new Text(variablesTable, SWT.NONE);
				newEditor.setText(item.getText(VARIABLES_EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						Text text = (Text) variablesEditor.getEditor();
						variablesEditor.getItem().setText(
								VARIABLES_EDITABLECOLUMN,
								text.getText());
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				variablesEditor
						.setEditor(newEditor, item,
								VARIABLES_EDITABLECOLUMN);
			}
		});

		// security properties table
		final Table securityTable = new Table(securityGroup, SWT.CHECK | SWT.HIDE_SELECTION);
		securityTable.setHeaderVisible(true);
		TableColumn capabilities = new TableColumn(securityTable, SWT.FILL);
		capabilities.setText("Attacker Capabilities");
		String secItems[] = { "Ticks", "Low Assignments", "Content", "Duration", "Threads" };
		for (int i = 0; i < secItems.length; i++) {
			TableItem item = new TableItem(securityTable, SWT.LEAD);
			item.setText(secItems[i]);
			if ((params.attackerCaps & (1 << i)) != 0)
				item.setChecked(true);
		}
		capabilities.pack();

		// timing properties table
		final Table timingTable = new Table(timingGroup, SWT.SINGLE);
		timingTable.setHeaderVisible(true);
		timingTable.setVisible(true);
		TableColumn timingCmd = new TableColumn(timingTable, SWT.LEFT);
		timingCmd.setText("Command");
		TableColumn timingDuration = new TableColumn(timingTable, SWT.LEFT);
		timingDuration.setText("Duration");
		String timingItems[] = { "skip", "if", "while", "fork", "assign", "plus/minus",
				"times/div", "mod", "boolean ops" };
		for (int i = 0; i < timingItems.length; i++) {
			TableItem item = new TableItem(timingTable, SWT.LEAD);
			item.setText(0, timingItems[i]);
			item.setText(1, new Integer(params.durations[i]).toString());
		}
		timingCmd.pack();
		timingDuration.pack();

		final TableEditor timingEditor = new TableEditor(timingTable);
		// The editor must have the same size as the cell and must
		// not be any smaller than 50 pixels.
		timingEditor.horizontalAlignment = SWT.LEFT;
		timingEditor.grabHorizontal = true;
		timingEditor.minimumWidth = 50;
		// editing the second column
		final int TIMING_EDITABLECOLUMN = 1;

		timingTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = timingEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				TableItem item = (TableItem) e.item;
				if (item == null)
					return;

				// The control that will be the editor must be a
				// child of the Table
				Text newEditor = new Text(timingTable, SWT.NONE);
				newEditor.setText(item.getText(TIMING_EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						Text text = (Text) timingEditor.getEditor();
						timingEditor.getItem().setText(
								TIMING_EDITABLECOLUMN,
								text.getText());
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				timingEditor.setEditor(newEditor, item, TIMING_EDITABLECOLUMN);
			}
		});

		// cancel button
		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("&Cancel");
		GridData cancelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		cancelGridData.horizontalSpan = 2;
		cancelButton.setLayoutData(cancelGridData);
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				shell.close();
				shell.dispose();
			}
		});

		// run button
		Button runButton = new Button(shell, SWT.PUSH);
		runButton.setText("&Run");
		GridData runGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		runGridData.horizontalSpan = 1;
		runButton.setLayoutData(runGridData);
		runButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				try {
					// variables
					int i = 0;
					for (Iterator iter = params.variables.iterator(); iter
							.hasNext();)
						getUserInt((Int) iter.next(), variablesTable
								.getItem(i++).getText(1));

					// attacker capabilites
					params.attackerCaps = 0;
					for (int j = 0; j < 5; j++)
						params.attackerCaps |= securityTable.getItem(j)
								.getChecked() ? (1 << j) : 0;

					// timings
					for (int j = 0; j < params.durations.length; j++)
						params.durations[j] = Integer.parseInt(timingTable
								.getItem(j).getText(1));

					// turns
					params.turns = Integer.parseInt(turnsText.getText());

				} catch (Exception ex) {
					// In case of worng eneterd data, print an error message
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage("Only integer values can be entered");
					messageBox.open();
					return;
				}

				shell.close();
				shell.dispose();
				result = params;
			}
		});

		// finally, open the shell
		shell.pack();
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}
}
