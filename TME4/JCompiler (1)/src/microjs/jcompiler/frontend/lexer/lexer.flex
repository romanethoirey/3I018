
/* JFlex specification for JCompiler */

package microjs.jcompiler.frontend.lexer;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import microjs.jcompiler.frontend.parser.sym;

/**
 * This class is a simple example lexer.
 */

%%

%class Lexer
%public
%unicode
%implements java_cup.runtime.Scanner
%function next_token
%type java_cup.runtime.Symbol
%line
%column

%eofval{
  return symbol("EOF", sym.EOF);
%eofval}

%{
  private ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();
  // StringBuffer string = new StringBuffer();
    
  private Symbol symbol(String name, int type) {
    return symbolFactory.newSymbol(name, type, new Location(yyline+1, yycolumn +1),  new Location(yyline+1,yycolumn+yylength()));
  }
  private Symbol symbol(String name, int type, Object value) {
    return symbolFactory.newSymbol(name, type, new Location(yyline+1, yycolumn +1),  new Location(yyline+1,yycolumn+yylength()), value);
  }
%}

Identifier = [a-zA-Z][a-zA-Z0-9]* 

Digit = [0-9]

LineTerminator = ( \u000D\u000A
	               | [\u000A\u000B\u000C\u000D\u0085\u2028\u2029] )


 
%%

{Digit}+	 { return symbol("INT", sym.INT,
				 Integer.parseInt(yytext())); }

{LineTerminator} { /* ignore */ }

[ \t\f\n]+	 { /* ignore */ }

==		 { return symbol("EQEQ", sym.EQEQ); }

[=]		 { return symbol("EQ", sym.EQ); }

[(]		 { return symbol("LPAREN", sym.LPAREN); }

[)]		 { return symbol("RPAREN", sym.RPAREN); }

[+]		 { return symbol("PLUS", sym.PLUS); }

[-]		 { return symbol("MINUS", sym.MINUS); }

[*]		 { return symbol("TIMES", sym.TIMES); }

[/]		 { return symbol("DIV", sym.DIV); }

"**"		 { return symbol("PUIS", sym.PUIS); }





{Identifier}	 { return symbol("IDENTIFIER", sym.IDENTIFIER, yytext()); }

\/\/.*\R	 { /* ignore */ }	/* commentaire en ligne */

/* error fallback */
.		 {  // very strange "bug"
		   if (yytext() == "\\u000A") {
		      /* ignore */
		      System.err.println(
		        "WARNING: strange fallback character");
		   } else {
		      throw new Error("Illegal character <" + yytext() + ">");
		   }
		 }
