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
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * SWT Component that implements the visualization of information flows known as
 * traces. For more information on these, take a look at the development
 * documentation.
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class TraceView extends Canvas {
	private Vector traces = null;
	public int maxThreads;

	/**
	 * Create a new TraceView object
	 * 
	 * @param parent
	 *                the parent widget
	 * @param style
	 *                the style of the parent widget
	 */
	public TraceView(Composite parent, int style) {
		super(parent, style | SWT.H_SCROLL | SWT.V_SCROLL);
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				TraceView.this.paintControl(e);
			}
		});
		traces = new Vector();
		maxThreads = 1;
	}

	/**
	 * Implements the drawing function of the widget
	 * 
	 * @param e
	 */
	void paintControl(PaintEvent e) {
		// get viewport
		Display d = Display.getDefault();
		GC gc = e.gc;
		Point viewsize = this.getSize();
		int spacing = 20;

		// get dimensions from all traces w x h
		Point size = new Point(0, spacing);
		for (Iterator iter = traces.iterator(); iter.hasNext();) {
			Trace trace = (Trace) iter.next();
			Point extent = trace.getExtent(gc);
			if (extent.x > size.x)
				size.x = extent.x;
			size.y += extent.y + spacing;
		}
		size.x += 2 * spacing;

		// set scrollbar for w & h, update current pos
		ScrollBar hbar = this.getHorizontalBar();
		ScrollBar vbar = this.getVerticalBar();
		hbar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				redraw();
			}
		});
		hbar.setVisible(viewsize.x <= size.x);
		hbar.setValues(hbar.getSelection(), 0, size.x, viewsize.x, 1, 1);
		vbar.setVisible(viewsize.y <= size.y);
		vbar.setValues(vbar.getSelection(), 0, size.y, viewsize.y, 1, 1);
		vbar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				redraw();
			}
		});

		// set viewport
		Rectangle viewport = new Rectangle(hbar.getSelection(), vbar.getSelection(),
				viewsize.x, viewsize.y);

		// clear screen
		gc.setBackground(d.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		gc.fillRectangle(0, 0, viewsize.x, viewsize.y);

		// draw all visible traces
		Point pos = new Point(spacing - hbar.getSelection(), spacing - vbar.getSelection());
		for (Iterator iter = traces.iterator(); iter.hasNext();) {
			Trace trace = (Trace) iter.next();
			Point extent = trace.getExtent(gc);
			trace.draw(gc, pos, viewport);
			pos.y += spacing;
		}
	}

	/**
	 * Add a trace to the widget, it will be draw below a previously added
	 * traces
	 * 
	 * @param trace
	 */
	public void addTrace(Trace trace) {
		traces.add(trace);
	}

	
	/**
	 * Clear the widget and prepare for reuse 
	 */
	public void clear() {
		traces.clear();
		redraw();
	}
}
