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
import java.io.*;
import java.util.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;



/**
 * Main class, implements a window with a menu 
 * and a cardbox for holding the AbstractTab items
 * 
 * @author Martin B&auml;ttig
 * @version 1.0
 */
public class Main {
	private Shell shell = null;
	private Menu menu = null;
	private TabFolder tabFolder = null;
	private MenuItem roundRobinItem = null;
	private MenuItem uniformItem = null;
	private MenuItem userItem = null;
	boolean firstOpen = true;

	private void menuFileNew() {
		TabItem[] tabItem = new TabItem[1];
		tabItem[0] = new TabItem(tabFolder, SWT.NONE);
		tabItem[0].setControl(new VisualizerTab(tabFolder, SWT.NONE));
		tabItem[0].setText("Unnamed.mwl");
		((AbstractTab) tabItem[0].getControl()).setFilename("Unnamed.mwl");
		tabFolder.setSelection(tabItem);
		firstOpen = false;
	}

	private void menuFileOpen() {
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		fileDialog.setFilterExtensions(new String[] { "*.mwl" });
		if (fileDialog.open() == null)
			return;
		String fileSeparator = System.getProperty("file.separator");
		String filename = fileDialog.getFilterPath() + fileSeparator
				+ fileDialog.getFileName();

		String program = getStringFromFile(filename);
		if (program == null)
			return;

		TabItem[] tabItem = new TabItem[1];
		tabItem[0] = null;
		if (firstOpen && !getCurrentTab().getChanged()) {
			tabItem[0] = tabFolder.getItem(0);
		} else {
			tabItem[0] = new TabItem(tabFolder, SWT.NONE);
			tabItem[0].setControl(new VisualizerTab(tabFolder, SWT.NONE));
		}
		tabItem[0].setText(fileDialog.getFileName());
		AbstractTab exploit = (AbstractTab) tabItem[0].getControl();
		exploit.setFilename(filename);
		exploit.setCode(program);
		tabFolder.setSelection(tabItem);
		firstOpen = false;
	}

	private void menuFileClose() {
		TabItem[] tabItems = tabFolder.getSelection();
		if (tabItems.length != 1)
			return;
		AbstractTab exploit = (AbstractTab) tabItems[0].getControl();
		if (!exploit.close())
			return;
		tabItems[0].dispose();
	}

	private void menuFileSave() {
		AbstractTab tab = getCurrentTab();
		writeStringToFile(tab.getFilename(), tab.getCode());
	}

	private void menuFileSaveAs() {
		// get the filename
		AbstractTab tab = getCurrentTab();
		int index = tabFolder.getSelectionIndex();
		if (index == -1)
			return;
		TabItem tabItem = tabFolder.getItem(index);
		String filename = tabItem.getText();

		// prepare save dialog
		FileDialog saveDialog = new FileDialog(shell, SWT.SAVE);
		saveDialog.setFileName(getCurrentTab().getFilename());
		if (saveDialog.open() == null)
			return;
		String fileSeparator = System.getProperty("file.separator");

		// execute dialog, save file and refresh filename fields
		filename = saveDialog.getFilterPath() + fileSeparator + saveDialog.getFileName();
		writeStringToFile(filename, tab.getCode());
		tabItem.setText(saveDialog.getFileName());
		tab.setFilename(filename);

	}

	private void menuFileExit() {
		// only close the shell if all tabs have been closed
		TabItem[] tabItems = tabFolder.getItems();
		for (int i = 0; i < tabItems.length; i++) {
			AbstractTab exploit = (AbstractTab) tabItems[i].getControl();
			if (!exploit.close())
				return;
		}
		shell.close();
	}

	private AbstractTab getCurrentTab() {
		int index = tabFolder.getSelectionIndex();
		if (index == -1)
			return null;
		TabItem tabItem = tabFolder.getItem(index);
		return (AbstractTab) tabItem.getControl();
	}

	public String getStringFromFile(String filename) {
		String code = "";
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(filename));
			char[] buf = new char[1000];
			int num = 0;
			while (true) {
				num = in.read(buf);
				if (num < 0)
					break;
				code += new String(buf, 0, num);
			}
		} catch (FileNotFoundException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("File: " + filename + " not found");
			messageBox.open();
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Error while reading from file: " + filename);
			messageBox.open();
		}
		return code;
	}

	public void writeStringToFile(String filename, String text) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
					filename));
			out.write(text);
			out.flush();
		} catch (FileNotFoundException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("File: " + filename + " not found");
			messageBox.open();
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Error while writing to file: " + filename);
			messageBox.open();
		}
	}

	private void createMenu() {
		menu = new Menu(shell, SWT.BAR);

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
		fileItem.setText("&File");
		fileItem.setMenu(fileMenu);

		MenuItem newItem = new MenuItem(fileMenu, SWT.PUSH);
		newItem.setText("&New\tCrtl+N");
		newItem.setAccelerator(SWT.CTRL + 'N');
		newItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileNew();
			}
		});

		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("&Open\tCrtl+O");
		openItem.setAccelerator(SWT.CTRL + 'O');
		openItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileOpen();
			}
		});

		MenuItem closeItem = new MenuItem(fileMenu, SWT.PUSH);
		closeItem.setText("&Close\tCrtl+C");
		closeItem.setAccelerator(SWT.CTRL + 'C');
		closeItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileClose();
			}
		});

		MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setText("&Save\tCrtl+S");
		saveItem.setAccelerator(SWT.CTRL + 'S');
		saveItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileSave();
			}
		});

		MenuItem saveAsItem = new MenuItem(fileMenu, SWT.PUSH);
		saveAsItem.setText("&Save as...\tCrtl+A");
		saveAsItem.setAccelerator(SWT.CTRL + 'A');
		saveAsItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileSaveAs();
			}
		});

		new MenuItem(fileMenu, SWT.SEPARATOR);

		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("Exit\tCrtl+E");
		exitItem.setAccelerator(SWT.CTRL + 'E');
		exitItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileExit();
			}
		});

		Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
		MenuItem editItem = new MenuItem(menu, SWT.CASCADE);
		editItem.setText("&Edit");
		editItem.setMenu(editMenu);

		MenuItem cutItem = new MenuItem(editMenu, SWT.PUSH);
		cutItem.setText("Cu&t\tShift+Delete");
		cutItem.setAccelerator(SWT.SHIFT + SWT.DEL);
		cutItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().cut();
			}
		});

		MenuItem copyItem = new MenuItem(editMenu, SWT.PUSH);
		copyItem.setText("&Copy\tCrtl+Insert");
		copyItem.setAccelerator(SWT.CTRL + SWT.INSERT);
		copyItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().copy();
			}
		});

		MenuItem pasteItem = new MenuItem(editMenu, SWT.PUSH);
		pasteItem.setText("&Paste\tShift+Insert");
		pasteItem.setAccelerator(SWT.SHIFT + SWT.INSERT);
		pasteItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().paste();
			}
		});

		Menu actionMenu = new Menu(shell, SWT.DROP_DOWN);
		MenuItem actionItem = new MenuItem(menu, SWT.CASCADE);
		actionItem.setText("&Action");
		actionItem.setMenu(actionMenu);

		MenuItem runItem = new MenuItem(actionMenu, SWT.PUSH);
		runItem.setText("&Visualize\tF1");
		runItem.setAccelerator(SWT.F1);
		runItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().simulate();
			}
		});

		MenuItem stepItem = new MenuItem(actionMenu, SWT.PUSH);
		stepItem.setText("&Step\tF2");
		stepItem.setAccelerator(SWT.F2);
		stepItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().step();
			}
		});

		MenuItem resetItem = new MenuItem(actionMenu, SWT.PUSH);
		resetItem.setText("&Reset\tF3");
		resetItem.setAccelerator(SWT.F3);
		resetItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().reset();
			}
		});

		MenuItem checkItem = new MenuItem(actionMenu, SWT.PUSH);
		checkItem.setText("&Check\tF4");
		checkItem.setAccelerator(SWT.F4);
		checkItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().check();
			}
		});

		MenuItem transformItem = new MenuItem(actionMenu, SWT.PUSH);
		transformItem.setText("&Transform\tF5");
		transformItem.setAccelerator(SWT.F5);
		transformItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().transform();
			}
		});

		Menu schedulerMenu = new Menu(shell, SWT.DROP_DOWN);
		MenuItem schedulerItem = new MenuItem(menu, SWT.CASCADE);
		schedulerItem.setText("&Scheduler");
		schedulerItem.setMenu(schedulerMenu);

		roundRobinItem = new MenuItem(schedulerMenu, SWT.RADIO);
		roundRobinItem.setText("&Round Robin");
		roundRobinItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().setScheduler(new RoundRobinScheduler());
			}
		});
		roundRobinItem.setSelection(true);

		uniformItem = new MenuItem(schedulerMenu, SWT.RADIO);
		uniformItem.setText("&Uniform");
		uniformItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				getCurrentTab().setScheduler(new UniformScheduler());
			}
		});
	}

	private void setScheduler() {
		AbstractTab exploit = getCurrentTab();
		if (exploit == null)
			return;
		Scheduler scheduler = exploit.getScheduler();
		roundRobinItem.setSelection(false);
		uniformItem.setSelection(false);
		if (scheduler instanceof RoundRobinScheduler)
			roundRobinItem.setSelection(true);
		else if (scheduler instanceof UniformScheduler)
			uniformItem.setSelection(true);
		else
			roundRobinItem.setSelection(true);
	}

	private void createShell() {
		shell = new Shell();
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		shell.setLayout(fillLayout);
		tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				setScheduler();
			}
		});
		createMenu();
		shell.setMenuBar(menu);
		menuFileNew();
		shell.setText("Information Leak Visualizer");
		shell.setSize(new Point(1024, 768));
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Main test = new Main();
		test.createShell();
		test.shell.open();
		while (!test.shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}