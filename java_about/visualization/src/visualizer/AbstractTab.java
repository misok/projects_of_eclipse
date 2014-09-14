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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;

/**
 * Abstract class that all classes for the use in the main class must implement
 * TODO: factor out the action menu
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public abstract class AbstractTab extends Composite {
	private String filename = null;
	private Scheduler scheduler = null;
	private boolean running = false;
	private boolean changed = false;

	/**
	 * Create a new AbstractTab object with parent and style
	 * 
	 * @param parent
	 *                Parent widget
	 * @param style
	 *                Style of parent widget
	 */
	public AbstractTab(Composite parent, int style) {
		super(parent, style);
		scheduler = new RoundRobinScheduler();
	}

	/**
	 * Set the code of this tab
	 * 
	 * @param program
	 *                string containing the code
	 */
	public abstract void setCode(String program);

	/**
	 * Get the code from the tab
	 * 
	 * @return the code as string
	 */
	public abstract String getCode();

	/**
	 * implements the copy operation on the code
	 */
	public abstract void copy();

	/**
	 * implements the paste operation on the code
	 */
	public abstract void paste();

	/**
	 * implements the cut operation on the code
	 */
	public abstract void cut();

	/**
	 * Close the tab and return true if possible else return false
	 * 
	 * @return success
	 */
	public abstract boolean close();

	/**
	 * Execute the simulation
	 */
	public abstract void simulate();

	/**
	 * Execute a single step
	 */
	public abstract void step();

	/**
	 * Reset the interpreter
	 */
	public abstract void reset();

	/**
	 * Apply the checking type system
	 */
	public abstract void check();

	/**
	 * Apply the transforming type system
	 */
	public abstract void transform();

	/**
	 * Set the changed flag
	 * 
	 * @param changed
	 *                changed flag
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * Get the changed flag
	 * 
	 * @return changed flag
	 */
	public boolean getChanged() {
		return changed;
	}

	/**
	 * Set the scheduler
	 * 
	 * @param scheduler
	 *                instance of a scheduler
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * Get the current scheduler object
	 * 
	 * @return scheduler instance
	 */
	public Scheduler getScheduler() {
		return scheduler;
	}

	/**
	 * Set the file name for this tab
	 * 
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Get the filename for this tab
	 * 
	 * @return filename
	 */
	public String getFilename() {
		return filename;
	}
}
