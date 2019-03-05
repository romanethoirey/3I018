/*
	Generer l'analyseur lexical :   jflex Exercice4_3.jflex
	Compiler avec :                 javac Exercice4_3.java
	Executer avec :                 java Exercice4_3

La grammaire utilisee : LL(1)

S  -> a S b
S  ->

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

%class Exercice4_3
%unicode
%line
%column
%type Token

%{
static final int FIN = 0;
static final int A   = 1;
static final int B   = 2;

Token lu;

%}

%{

void S (Exercice4_3 analyseur) {
  Token x;
  
  try {
     switch (analyseur.lu.getId()) {
         case A :
	   analyseur.lu = analyseur.yylex();
	   S(analyseur);
	   x = analyseur.lu;
	   analyseur.lu = analyseur.yylex();
	   if (x.getId() != B) {
	      System.out.println("Erreur"); System.exit(0);
	   }
	   break;
        case B :
	case FIN :
	   break;
	default : System.out.println("Erreur"); System.exit(0);
     }
  } catch (Exception e) { System.out.println("Erreur"); System.exit(0); }
}

public static void main(String argv[]) {
  Exercice4_3 analyseur =
    new Exercice4_3(new java.io.InputStreamReader(System.in));

  try {
    analyseur.lu = analyseur.yylex();

    analyseur.S(analyseur);

    if (analyseur.lu.getId() != FIN) {	// il y en a trop  aabbbb
       System.out.println("Erreur"); System.exit(0);
    }
    System.out.println();
  } catch (Exception e) { System.out.println("Erreur"); System.exit(0); }
}

%}


%%
			/* (III) r�gles d'analyse et actions */

[ \t\n]+ {}

a {
  return new Token(A);
}

b {
  return new Token(B);
}

<<EOF>> {
  return new Token(FIN);
}


/**********

Penser a terminer la phrase avec la touche <Enter> et deux <Ctrl-D>.


**********/
