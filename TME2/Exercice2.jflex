/*
	Generer l'analyseur lexical :   jflex Exercice2.jflex
	Compiler avec :                 javac Exercice2.java
	Executer avec :                 java Exercice2

La grammaire utilisee : LL(1)

E  -> + E E | - E E | * E E | / E E  
E  -> constante
E  -> identificateur

*/

/* (I) codes utilisateur */

enum EType {
     CONSTANTE, VARIABLE, BINAIRE
}


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


class Expr {
      EType  type;
      Object valeur;
      String op;
      Expr   g, d;

      Expr (Integer x) {
           type   = EType.CONSTANTE;
           valeur = x;
      }

      Expr (String x) {
           type   = EType.VARIABLE;
           valeur = x;
      }

      Expr (String op, Expr g, Expr d) {
           type    = EType.BINAIRE;
           this.op = op;
           this.g  = g;
           this.d  = d;
      }

      EType getType () { return type; }

      Object getValeur () { return valeur; }

      String getOp () { return op; }

      Expr getG () { return g; }

      Expr getD () { return d; }
}

%%

          /* (II) Options et declarations */

%class Exercice2
%unicode
%line
%column
%type Token

%{
static final int FIN            = 0;
static final int ENTIER         = 1;
static final int IDENTIFICATEUR = 2;
static final int OPERATEUR      = 3;

Token lu;

%}

%{

Expr E (Exercice2 analyseur) {
Token x;

  try {
     switch (analyseur.lu.getId()) {
         case ENTIER :
	   x = analyseur.lu;
	   analyseur.lu = analyseur.yylex();
	   return new Expr((Integer)(x.getValeur()));
        case IDENTIFICATEUR :
	   x = analyseur.lu;
	   analyseur.lu = analyseur.yylex();
 	   return new Expr((String)(x.getValeur()));
	case OPERATEUR :
	   x = analyseur.lu;
	   analyseur.lu = analyseur.yylex();

	   Expr g = E(analyseur);
	   Expr d = E(analyseur);
	   return new Expr((String)(x.getValeur()), g, d);
	default : System.out.println("Erreur"); System.exit(0);
     }
  } catch (Exception e) { System.out.println("Erreur"); System.exit(0); }

return null;
}

public void imprimer_prefixe (Expr e) {
       switch (e.getType()) {
          case CONSTANTE :
	    System.out.print((Integer)e.getValeur());
	    break;
          case VARIABLE :
	    System.out.print((String)e.getValeur());
	    break;
          default :
	    System.out.print(e.getOp() + " ");
	    imprimer_prefixe(e.getG());
	    System.out.print(" ");
	    imprimer_prefixe(e.getD());
	    break;
       }
}

public void imprimer_postfixe (Expr e) {
       switch (e.getType()) {
          case CONSTANTE :
	    System.out.print((Integer)e.getValeur());
	    break;
          case VARIABLE :
	    System.out.print((String)e.getValeur());
	    break;
          default :
	    imprimer_postfixe(e.getG());
	    System.out.print(" ");
	    imprimer_postfixe(e.getD());
	    System.out.print(" ");
	    System.out.print(e.getOp());
	    break;
       }
}

public void imprimer_infixe_parenthese (Expr e) {
       switch (e.getType()) {
          case CONSTANTE :
	    System.out.print((Integer)e.getValeur());
	    break;
          case VARIABLE :
	    System.out.print((String)e.getValeur());
	    break;
          default :
	    System.out.print("(");
	    imprimer_infixe_parenthese(e.getG());
	    System.out.print(" " + e.getOp() + " ");
	    imprimer_infixe_parenthese(e.getD());
	    System.out.print(")");
	    break;
       }
}

public static void main(String argv[]) {
  Exercice2 analyseur =
    new Exercice2(new java.io.InputStreamReader(System.in));

  try {
    analyseur.lu = analyseur.yylex();

    Expr e = analyseur.E(analyseur);

    analyseur.imprimer_prefixe(e);
    System.out.println();
    analyseur.imprimer_postfixe(e);
    System.out.println();
    analyseur.imprimer_infixe_parenthese(e);
    System.out.println();
  } catch (Exception e) { System.out.println("Erreur"); System.exit(0); }
}

%}

ENTIER_RELATIF = -?[0-9]+
IDENTIFICATEUR = [a-zA-Z][a-zA-Z0-9]*
OPERATEUR      = [\+\*\-\/]


%%
			/* (III) r�gles d'analyse et actions */

[ \t\n]+ {}

{ENTIER_RELATIF} {
  return new Token(ENTIER, new Integer(yytext()));
}

{IDENTIFICATEUR} {
  return new Token(IDENTIFICATEUR, new String(yytext()));
}

{OPERATEUR} {
  return new Token(OPERATEUR, new String(yytext()));
}

<<EOF>> {
  return new Token(FIN);
}


/**********

Penser a terminer la phrase avec la touche <Enter> et deux <Ctrl-D>.

$ java Exercice2
+ - a 451 * gdikd 78
+ - a 451 * gdikd 78
a 451 - gdikd 78 * +
((a - 451) + (gdikd * 78))

**********/
