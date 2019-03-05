/*
	Generer l'analyseur lexical :   jflex Exo1.jflex
	Compiler avec :                 javac Exo1.java
	Executer avec :                 java Exo1
*/

/* (I) codes utilisateur */

class Token {
      int    id;
      Object valeur_associee;

      Token (int id) {
      	    this.id         = id;
	    valeur_associee = null;
      }
      
      Token (int id, Object valeur_associee) {
      	    this.id              = id;
	    this.valeur_associee = valeur_associee;
      }

      int getId () {
      	  return id;
      }

      Object getValeur () {
      	  return valeur_associee;
      }
}

%%

          /* (II) Options et declarations */

%class Q7
%unicode
%line
%column
%type Token

%{

static final int ID_FIN     = 0;
static final int ID_IDENT   = 1;
static final int ID_ENTIER  = 2;
//static final int ID_REFUS   = 3;
static final int ID_ARITHMETIQUE = 4;
static final int ID_COMB = 5;
static final int ID_COML = 6;
%}

%{
public static void main(String argv[]) {
  Q7 analyseur= new Q7(new java.io.InputStreamReader(System.in));

  try {
    Token lu = analyseur.yylex();

    while (lu.getId() != YYEOF) {
      switch (lu.getId()) {
        case ID_FIN :
		System.exit(0);
	case ID_ENTIER :
		System.out.println(lu.getValeur() + " est un entier.");
        	break;
	case ID_IDENT :
		System.out.println(lu.getValeur() + " est un identificateur.");
		break;
	case ID_ARITHMETIQUE:
		System.out.println(lu.getValeur() + "est un opérateur arithmétique.");
	case ID_COMB:
		System.out.println(lu.getValeur() + "est un commentaire /*.");
	case ID_COML:
		System.out.println(lu.getValeur() + "est un commentaire //.");
	/*case ID_REFUS :
		System.out.println("Cette valeur n'est pas accepté.");*/
	default :;
	  
      }
      lu = analyseur.yylex();
    }
  } catch (Exception e) {}
}
%}

SEPARATEUR = [\n \t]
ENTIER = -?[0-9]+
IDENT = [a-zA-Z][a-zA-Z0-9]*
//REFUS = [^ENTIER][^IDENT]
ARITHMETIQUE = [\+\-\*\/\(\)]
COMB = "/*".*"*/"
COML = "//".*\n

%%
			/* (III) règles d'analyse et actions */

{SEPARATEUR}+ {}

{ENTIER} { /* le cas plus general */
  return new Token(ID_ENTIER, new String(yytext()));
}

{IDENT} { /* cas particulier avant le cas plus general */
  return new Token(ID_IDENT, new String(yytext()));
}

/*{REFUS} {
  return new Token(ID_REFUS, new String(yytext()));
}*/

{ARITHMETIQUE} {
	return new Token(ID_ARITHMETIQUE, new String(yytext()));
}

{COMB} {
	return new Token(ID_COMB, new String(yytext()));
}

{COML} {
	return new Token(ID_COML, new String(yytext()));
}

<<EOF>> {
  return new Token(ID_FIN);
}

