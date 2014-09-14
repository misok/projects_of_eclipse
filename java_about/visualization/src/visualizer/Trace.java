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
import java.util.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * This class represents a trace, it implements the CommandListener interface so it can work
 * together with the interpreter. This class provides a method to display the trace.
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class Trace implements CommandListener {
	private Vector events = null;
	private DurationVisitor durationVisitor = null;
	private int size;
	private TraceView tw = null;
	private Parameters params = null;
	private int spacing = 15;
	private Integer maxThreads = new Integer(1);

	/**
	 * Create a new trace object
	 * 
	 * @param tw
	 *                TraceView, this trace belongs to
	 * @param params
	 *                Parameters for the visualization (observer, durations)
	 */
	public Trace(TraceView tw, Parameters params) {
		this.tw = tw;
		this.params = params;
		size = 1;
		events = new Vector();
		durationVisitor = new DurationVisitor(params);
	}

	/**
	 * A helper function to calculate an expression given in the MWL abstract syntax tree
	 * 
	 * @param the
	 *                expression to be evaluated
	 * @param variables
	 *                a map containing the variables
	 * @return
	 */
	private Integer getExpValue(Exp e, Map variables) {
		MWLInterpreter interpreter = new MWLInterpreter(null, null, variables);
		Object obj = (Object) e.accept(interpreter);
		if (!(obj instanceof Integer)) {
			if (obj instanceof Boolean)
				obj = new Integer(((Boolean) obj).booleanValue() ? 1 : 0);
			else
				throw new RuntimeException("Assigned a not valid Expression");
		}
		return (Integer) obj;
	}

	public void handleEvent(Command command, Map variables, int threadID, int numThreads) {
		if (command == null)
			return;
		command.accept(durationVisitor);
		int duration = ((Integer) durationVisitor.getResult()).intValue();
		if (command instanceof Assign) {
			Assign assign = (Assign) command;
			Variable variable = (Variable) variables
					.get(assign.getVariable().getName());
			if (variable.getSecDomain() instanceof LowSec) {
				addEvent(getExpValue(assign.getExp(), variables), duration,
						numThreads);
			} else
				addEvent(null, duration, numThreads);
		} else
			addEvent(null, duration, numThreads);
	}

	/**
	 * Add a new event to the trace
	 * 
	 * @param content
	 *                content represented through a color
	 * @param length
	 *                duration of the command
	 * @param size
	 *                number of threads running at the time of the event
	 */
	public void addEvent(Integer content, int length, int size) {
		events.add(new TraceEvent(content, length, size));
		if (size > tw.maxThreads)
			tw.maxThreads = size;
		tw.redraw();
	}

	/**
	 * Get the extent of the trace on a given context
	 * 
	 * @param gc
	 *                context for the calculation
	 * @return
	 */
	public Point getExtent(GC gc) {
		Point extent = new Point(0, 0);
		for (Iterator iter = events.iterator(); iter.hasNext();) {
			// get next event from list
			TraceEvent event = (TraceEvent) iter.next();

			// set duration
			if ((params.attackerCaps & Parameters.DURATION) != 0)
				extent.x += event.length;
			else
				extent.x += spacing;

			if (extent.y < spacing)
				extent.y = spacing;
		}
		return extent;
	}

	/**
	 * Draw a trace on the GC at position pos clipped by a given viewport
	 * 
	 * @param gc
	 *                context, where trace is to be drawn
	 * @param pos
	 *                position inside the context
	 * @param viewport
	 *                clipping rectangle inside the context
	 */
	public void draw(GC gc, Point pos, Rectangle viewport) {
		Display d = Display.getDefault();
		Rectangle rect = new Rectangle(pos.x, pos.y, pos.x, spacing);
		gc.setLineStyle(SWT.LINE_SOLID);
		gc.setLineWidth(1);
		gc.setBackground(d.getSystemColor(SWT.COLOR_TITLE_BACKGROUND));

		for (Iterator iter = events.iterator(); iter.hasNext();) {
			// get next event from list
			TraceEvent event = (TraceEvent) iter.next();

			// set duration
			if ((params.attackerCaps & Parameters.DURATION) != 0)
				rect.width = event.length;
			else
				rect.width = spacing;

			// if is a a low and we want to see low assignments
			// color = red else color = blue
			if (event.content == null
					|| ((params.attackerCaps & Parameters.LOWASSIGN) == 0))
				gc.setBackground(d.getSystemColor(SWT.COLOR_BLUE));
			else
			// if we can see content, adjust color according to it
			if ((params.attackerCaps & Parameters.CONTENT) != 0) {
				int value = event.content.intValue() % Config.NUM_COLORS;
				if (value < 0)
					value += Config.NUM_COLORS;
				gc.setBackground(Config.traceColors[value]);
			} else
				gc.setBackground(d.getSystemColor(SWT.COLOR_RED));

			// if we want to see threads divide height by the number
			// of threads
			if ((params.attackerCaps & Parameters.THREADS) != 0)
				rect.height = spacing / (tw.maxThreads - event.size + 1);

			else
				rect.height = spacing;

			// finally draw the rectangle
			gc.fillRectangle(rect);

			// if we want to see ticks draw a line
			if ((params.attackerCaps & Parameters.TICKS) != 0)
				gc.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width
						- 1, rect.y + rect.height - 1);

			rect.x += rect.width;
		}
	}

	/**
	 * Clear the trace
	 */
	public void clear() {
		events.clear();
	}
}
