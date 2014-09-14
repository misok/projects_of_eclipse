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

import java.util.*;

/**
 * An interactive scheduler for the MWL interpreter
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class UserScheduler implements Scheduler {
	private int numThreads;
	private int currThread;

	public UserScheduler() {
		numThreads = 0;
		currThread = 0;
	}

	public void reset() {
		numThreads = 0;
		currThread = 0;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	public int getNumThreads() {
		return numThreads;
	}

	/**
	 * Must be called to change the current thread (eg. by a GUI)
	 * 
	 * @param i
	 *                The next to be executed
	 */
	public void setCurrThread(int i) {
		if (i < 0 || i >= numThreads)
			throw new RuntimeException("invalid thread selected");
		currThread = i;
	}

	public int getNextThread() {
		if (numThreads == 0)
			return -1;
		return currThread;
	}
}
