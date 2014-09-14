JAVA = java

all: lexer parser

lexer: MWLScanner.lex
	$(JAVA) -cp "../../bin" JLex.Main MWLScanner.lex
	mv MWLScanner.lex.java MWLScanner.java
parser: MWLParser.cup
	$(JAVA) -cp "../../bin" java_cup.Main -package mwlParser -parser MWLParser < MWLParser.cup
clean:
	rm -f MWLScanner.java MWLParser.java sym.java

