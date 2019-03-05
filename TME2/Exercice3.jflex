/*
	Generer l'analyseur lexical :   jflex Exercice3.jflex
	Compiler avec :                 javac Exercice3.java
	Executer avec :                 java Exercice3

La grammaire utilisee : LL(1)

Attention : Pas de sur-parenthesage.

E  -> (E O E)
E  -> constante
E  -> identificateur
O  -> + | - | * | /

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

%class Exercice3
%unicode
%line
%column
%type Token

%{
static final int FIN            = 0;
static final int ENTIER         = 1;
static final int IDENTIFICATEUR = 2;
static final int OPERATEUR      = 3;
static final int OUVRANTE       = 4;
static final int FERMANTE       = 5;

Token lu;

%}

%{

Expr E (Exercice3 analyseur) {
Token x, op;

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
	case OUVRANTE :
	   analyseur.lu = analyseur.yylex();
	   Expr g = E(analyseur);

	   x = analyseur.lu;
	   if (x.getId() != OPERATEUR) {
	      System.out.println("Erreur"); System.exit(0);
	   }
	   op = x;
	   
	   analyseur.lu = analyseur.yylex();
	   Expr d = E(analyseur);

	   x = analyseur.lu;
	   analyseur.lu = analyseur.yylex();
	   if (x.getId() != FERMANTE) {
	      System.out.println("Erreur"); System.exit(0);
	   }

	   return new Expr((String)(op.getValeur()), g, d);
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
  Exercice3 analyseur =
    new Exercice3(new java.io.InputStreamReader(System.in));

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

[\(] {
  return new Token(OUVRANTE);
}

[\)] {
  return new Token(FERMANTE);
}

{OPERATEUR} {
  return new Token(OPERATEUR, new String(yytext()));
}

<<EOF>> {
  return new Token(FIN);
}


/**********

Penser a terminer la phrase avec la touche <Enter> et deux <Ctrl-D>.

$ java Exercice3
((a - 451) + (gdikd * 78))
+ - a 451 * gdikd 78
a 451 - gdikd 78 * +
((a - 451) + (gdikd * 78))

**********/
