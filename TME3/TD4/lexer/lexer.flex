/* jflex lexer.flex */

package lexer;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import parser.sym;

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
    return symbolFactory.newSymbol(name, type,
    		new Location(yyline+1, yycolumn +1),
		new Location(yyline+1,yycolumn+yylength()));
  }
  private Symbol symbol(String name, int type, Object value) {
    return symbolFactory.newSymbol(name, type,
    	   	new Location(yyline+1, yycolumn +1),
		new Location(yyline+1,yycolumn+yylength()), value);
  }
%}

Identifier = [a-zA-Z][a-zA-Z0-9]* 

Digit = [0-9]


%%


[ \t\f\n]+  { /* ignore */ }

{Digit}+  { return symbol("INT", sym.INT, Integer.parseInt(yytext())); }

[(]  { return symbol("LPAREN", sym.LPAREN); }

[)]  { return symbol("RPAREN", sym.RPAREN); }

[+] { return symbol("PLUS", sym.PLUS); }

[-] { return symbol("MINUS", sym.MINUS); }

[*] { return symbol("TIMES", sym.TIMES); }

[/] { return symbol("DIV", sym.DIV); }

\%  { return symbol("MOD", sym.MOD); }

\?  { return symbol("POINT_INTERROGATION", sym.POINT_INTERROGATION); }

\:  { return symbol("DEUX_POINTS", sym.DEUX_POINTS); }

{Identifier} { return symbol("IDENTIFIER", sym.IDENTIFIER, yytext()); }
