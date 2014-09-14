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

import java.util.*;

public class Parameters {
	// attacker capabilites
	public final static int TICKS		= 1;
	public final static int LOWASSIGN	= 2;
	public final static int CONTENT		= 4;
	public final static int DURATION	= 8;
	public final static int THREADS		= 16;
	
	// command durations
	public final static int SKIP		= 0;
	public final static int IF		= 1;
	public final static int WHILE		= 2;
	public final static int FORK		= 3;
	public final static int ASSIGN		= 4;
	public final static int PLUS_MINUS	= 5;
	public final static int TIMES_DIV	= 6;
	public final static int MOD		= 7;
	public final static int BOOL_OP		= 8;
	
	public int turns;
	public int attackerCaps;
	public int[] durations;
	public Vector variables;
	
	public Parameters() {
		turns = 1;
		attackerCaps = TICKS | LOWASSIGN| CONTENT | DURATION | THREADS;
		durations = new int[9];
		for(int i = 0; i < PLUS_MINUS; i++)
			durations[i] = 15;
		for(int i = PLUS_MINUS; i < durations.length; i++)
			durations[i] = 0;
	}
}
