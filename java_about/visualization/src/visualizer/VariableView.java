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

import interpreter.*;
import java.util.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * SWT Component that implements a VariableListener, it shows a table with the
 * current state of the memory of an interpreter object
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class VariableView extends Composite implements VariableListener {
	private Table table = null;
	private HashMap variables = null;

	/**
	 * Create a new VariableView object
	 * 
	 * @param parent
	 *            a parent widget
	 * @param style
	 *            the style of the parent widget
	 */
	public VariableView(Composite parent, int style) {
		super(parent, style);

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		setLayout(fillLayout);

		variables = new HashMap();
		table = new Table(this, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		String[] columns = { "Name", "Type", "Security", "Value" };
		for (int i = 0; i < columns.length; i++) {
			TableColumn column = new TableColumn(table, SWT.LEAD);
			column.setText(columns[i]);
			column.setResizable(true);
			column.setWidth(80);
		}
	}

	public void handleEvent(Variable variable) {
		TableItem tableItem = (TableItem) variables.get(variable
				.getIdentifier());
		if (tableItem == null) {
			tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(0, variable.getIdentifier().toString());
			tableItem.setText(1, variable.getType().toString());
			tableItem.setText(2, variable.getSecDomain().toString());
			tableItem.setText(3, variable.toString());
			variables.put(variable.getIdentifier(), tableItem);
		} else {
			tableItem.setText(3, variable.toString());
		}
	}

	/**
	 * Clear the widget and prepare it to be reused
	 */
	public void clear() {
		table.removeAll();
		variables.clear();
	}
}
