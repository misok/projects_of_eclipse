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
import interpreter.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * This class is a SWT component, that implements a CommandListener, it is
 * designed for the use with the MWL interpreter. On each event the Code View
 * shows the current line number by high lighting it (for each thread).
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class CodeView extends Composite implements CommandListener {
	StyledText text = null;
	Vector lastLines = null;

	/**
	 * Create a new instance of the a Code View
	 * 
	 * @param parent
	 *                parent of the Code View component
	 * @param style
	 *                style of the parent of the Code View component
	 */
	public CodeView(Composite parent, int style) {
		super(parent, style);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		setLayout(fillLayout);
		text = new StyledText(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		text.setFont(new Font(Display.getDefault(), "Courier", 12, SWT.NORMAL));
		lastLines = new Vector();
	}

	public void handleEvent(Command command, Map variables, int threadID, int numThreads) {
		if (command == null)
			return;
		if (threadID >= lastLines.size())
			lastLines.setSize(threadID + 1);
		Integer lastLine = (Integer) lastLines.get(threadID);
		if (lastLine != null)
			text.setLineBackground(lastLine.intValue(), 1, Display.getDefault()
					.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		text.setLineBackground(command.getLineNumber(), 1, Config.getThreadColor(threadID));
		lastLines.set(threadID, new Integer(command.getLineNumber()));
	}

	/**
	 * Set the content of the Code View
	 * 
	 * @param s
	 *                new content
	 */
	public void setText(String s) {
		text.setText(s);
	}

	/**
	 * Get the current content of the Code View
	 * 
	 * @return current content
	 */
	public String getText() {
		return text.getText();
	}

	/**
	 * Cut the currently marked text
	 */
	public void cut() {
		text.cut();
	}

	/**
	 * Copy the currently marked text
	 */
	public void copy() {
		text.copy();
	}

	/**
	 * Paste the currently marked text
	 */
	public void paste() {
		text.paste();
	}

	/**
	 * Remove the high lighting from the Code View
	 */
	public void clear() {
		for (Iterator iter = lastLines.iterator(); iter.hasNext();) {
			Integer line = (Integer) iter.next();
			if (line != null)
				text.setLineBackground(line.intValue(), 1, Display.getDefault()
						.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		}
		lastLines.clear();
	}
}
