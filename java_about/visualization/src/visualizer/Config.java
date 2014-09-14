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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * Collection of config settings for the Information Leak Visualizer
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class Config {

	// constants
	public static final int SCHEUDLER_ROUNDROBIN = 0;
	public static final int SCHEDULER_UNIFORM = 1;
	public static final int SCHEDULER_USER = 2;

	public static final int NUM_COLORS = 16;

	// config settings
	static Display display = null;
	static Color[] threadColors = null;
	static Font codeFont = null;
	public static Color[] traceColors = null;

	static {
		display = Display.getDefault();
		threadColors = new Color[4];
		threadColors[0] = new Color(display, 153, 204, 255);
		threadColors[1] = new Color(display, 204, 204, 255);
		threadColors[2] = new Color(display, 255, 255, 204);
		threadColors[3] = new Color(display, 204, 255, 255);
		traceColors = new Color[NUM_COLORS];

		for (int i = 0; i < NUM_COLORS; i++)
			traceColors[i] = new Color(display, 127 + i * 8, 0, 0);

		traceColors[0] = display.getSystemColor(SWT.COLOR_CYAN);
		traceColors[1] = display.getSystemColor(SWT.COLOR_GREEN);
		traceColors[2] = display.getSystemColor(SWT.COLOR_MAGENTA);
		traceColors[3] = display.getSystemColor(SWT.COLOR_RED);
		traceColors[4] = display.getSystemColor(SWT.COLOR_YELLOW);
		traceColors[5] = display.getSystemColor(SWT.COLOR_GRAY);
		traceColors[6] = display.getSystemColor(SWT.COLOR_DARK_GRAY);
		traceColors[7] = display.getSystemColor(SWT.COLOR_DARK_CYAN);
		traceColors[8] = display.getSystemColor(SWT.COLOR_DARK_GREEN);
		traceColors[9] = display.getSystemColor(SWT.COLOR_DARK_MAGENTA);
		traceColors[10] = display.getSystemColor(SWT.COLOR_DARK_RED);
		traceColors[11] = display.getSystemColor(SWT.COLOR_DARK_YELLOW);
		traceColors[12] = display.getSystemColor(SWT.COLOR_RED);
		traceColors[13] = display.getSystemColor(SWT.COLOR_YELLOW);
		traceColors[14] = display.getSystemColor(SWT.COLOR_GREEN);
		traceColors[15] = display.getSystemColor(SWT.COLOR_MAGENTA);

		codeFont = new Font(display, "Courier", 12, SWT.NORMAL);
	}
	
	public static Color getThreadColor(int i) {
		if (i >= threadColors.length)
			i = threadColors.length;
		return threadColors[i];
	}

	public static void setThreadColors(Color[] threadColors) {
		Color[] old = Config.threadColors;
		Config.threadColors = threadColors;
		for (int i = 0; i < old.length; i++)
			old[i].dispose();
	}

	public static void setCodeFont(Font codeFont) {
		Font old = Config.codeFont;
		Config.codeFont = codeFont;
		old.dispose();
	}

	public static Font getCodeFont() {
		return codeFont;
	}
}
