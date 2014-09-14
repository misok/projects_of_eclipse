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

package checker;

/**
 * The analyzer for the security checks.
 *
 * @author       Christina P&ouml;pper
 * @version      1.0
 */
public class Analyzer {
    /** 
     * Main method for calling concrete <code>Checker</code> instances.
     *
     * @param argv Array of String. Should contain the name of the MWL file to
     * 		   be checked as first element. 
     */
    public static void main (String[] argv) throws Exception {
	if (argv.length < 1) 
	     throw new Exception("Wrong number of arguments: >java Test "+
				 "<mwl-filename>");
	String filename = argv[0];
 	Checker checker = new CheckerNoTransform(filename);
 	boolean res = checker.check();
 	System.out.println("==> The program "+filename+" is "+
 			   boolToString(res)+"secure.");
 	if (! res) {
	    Checker checkerTr = new CheckerTransform(argv[0]);
	    boolean res2 = checkerTr.check();
	    System.out.println("==> The program "+argv[0]+" can "+
			       boolToString(res2)+
			       "be transformed into a secure program.");
	    if (res2) 
		System.out.println("The transformed program can be found in "+ 
			       filename+"_secure");
 	}
    }

    /** 
     * Helper method returning the String "NOT " in case of a false
     * boolean value and the empty String otherwise. 
     *
     * @param	b	a truth value
     * @return		the String
     */
    private static String boolToString (boolean b) {
	if (! b)
	    return "NOT ";
	return "";
    }
}
