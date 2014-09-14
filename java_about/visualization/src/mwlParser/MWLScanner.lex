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

package mwlParser;

import java.lang.System;
import java_cup.runtime.Symbol;

%%

%cup
%class MWLScanner
%public
%char
%line

%{
	Symbol makeSymbol(int symNum) {
		return new Symbol(symNum, yyline, yychar);
	}

	Symbol makeSymbol(int symNum, String text) {
		return new Symbol(symNum, yyline, yychar, text);
	}
	
	Symbol makeSymbol(int symNum, Integer integer) {
		return new Symbol(symNum, yyline, yychar, integer);
	}
%}

ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]
WHITE_SPACE_CHAR=[\n\ \t\b\012]
STRING_TEXT= {ALPHA}({ALPHA}|{DIGIT})*

%% 

<YYINITIAL> ";" { return (makeSymbol(sym.SEMI)); }
<YYINITIAL> "," { return (makeSymbol(sym.COMMA)); }
<YYINITIAL> "(" { return (makeSymbol(sym.LPAREN)); }
<YYINITIAL> ")" { return (makeSymbol(sym.RPAREN)); }
<YYINITIAL> "[" { return (makeSymbol(sym.LSQBRACK)); }
<YYINITIAL> "]" { return (makeSymbol(sym.RSQBRACK)); }
<YYINITIAL> "{" { return (makeSymbol(sym.LBRACE)); }
<YYINITIAL> "}" { return (makeSymbol(sym.RBRACE)); }
<YYINITIAL> "+" { return (makeSymbol(sym.PLUS)); }
<YYINITIAL> "-" { return (makeSymbol(sym.MINUS)); }
<YYINITIAL> "*" { return (makeSymbol(sym.TIMES)); }
<YYINITIAL> "mod" { return (makeSymbol(sym.MOD)); }
<YYINITIAL> "div" { return (makeSymbol(sym.DIV)); }
<YYINITIAL> "==" { return (makeSymbol(sym.EQUAL_EQUAL)); }
<YYINITIAL> "!=" { return (makeSymbol(sym.NOT_EQUAL)); }
<YYINITIAL> "<"  { return (makeSymbol(sym.LESS)); }
<YYINITIAL> "<=" { return (makeSymbol(sym.LESS_EQUAL)); }
<YYINITIAL> ">"  { return (makeSymbol(sym.GREATER)); }
<YYINITIAL> ">=" { return (makeSymbol(sym.GREATER_EQUAL)); }
<YYINITIAL> "&&"  { return (makeSymbol(sym.AND)); }
<YYINITIAL> "||" { return (makeSymbol(sym.OR)); }
<YYINITIAL> ":=" { return (makeSymbol(sym.ASSIGN)); }
<YYINITIAL> ":" { return (makeSymbol(sym.COLON)); }
<YYINITIAL> "." { return (makeSymbol(sym.PERIOD)); }
<YYINITIAL> "skip" { return (makeSymbol(sym.SKIP)); }
<YYINITIAL> "if" { return (makeSymbol(sym.IF)); }
<YYINITIAL> "then" { return (makeSymbol(sym.THEN)); }
<YYINITIAL> "else" { return (makeSymbol(sym.ELSE)); }
<YYINITIAL> "end" { return (makeSymbol(sym.END)); }
<YYINITIAL> "while" { return (makeSymbol(sym.WHILE)); }
<YYINITIAL> "do" { return (makeSymbol(sym.DO)); }
<YYINITIAL> "fork" { return (makeSymbol(sym.FORK)); }
<YYINITIAL> "true" { return (makeSymbol(sym.TRUE)); }
<YYINITIAL> "false" { return (makeSymbol(sym.FALSE)); }
<YYINITIAL> "int" { return (makeSymbol(sym.INT)); }
<YYINITIAL> "bool" { return (makeSymbol(sym.BOOL)); }
<YYINITIAL> "high" { return (makeSymbol(sym.HIGH)); }
<YYINITIAL> "low" { return (makeSymbol(sym.LOW)); }
<YYINITIAL> "length" {return (makeSymbol(sym.LENGTH)); }
<YYINITIAL> "user" {return (makeSymbol(sym.USER)); }

<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ { }

<YYINITIAL> {DIGIT}+ { return (makeSymbol(sym.NUMBER, new Integer(yytext())));}

<YYINITIAL> {STRING_TEXT} {
	return (makeSymbol(sym.IDENTIFIER, yytext()));
}
