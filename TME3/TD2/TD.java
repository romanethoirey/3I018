import ast.*;
import lexer.Lexer;
import parser.parser;
import java.io.*;
import java_cup.runtime.*;

/*****

 on teste avec un fichier contenant l'expression par

     java TD nom_fichier

*****/

public class TD {

    public static String getClassName (Expr expr) {
	return expr.getClass().getName().substring(4);
    }

    public static void imprimerPrefixe (Expr expr) {
	if (getClassName(expr).equals("IntConst")) {
	    System.out.print(((IntConst)expr).getValue());
	} else if (getClassName(expr).equals("EVar")) {
	    System.out.print(((EVar)expr).getName());
	} else {// (getClassName(expr).equals("BinOp"))
	    System.out.print(((BinOp)expr).getName() + " ");
	    imprimerPrefixe(((BinOp)expr).getLeft());
	    System.out.print(" ");
	    imprimerPrefixe(((BinOp)expr).getRight());
	}
    }
    
    public static void imprimerPostfixe (Expr expr) {
	if (getClassName(expr).equals("IntConst")) {
	    System.out.print(((IntConst)expr).getValue());
	} else if (getClassName(expr).equals("EVar")) {
	    System.out.print(((EVar)expr).getName());
	} else {// (expr.getClassName().equals("BinOp"))
	    imprimerPostfixe(((BinOp)expr).getLeft());
	    System.out.print(" ");
	    imprimerPostfixe(((BinOp)expr).getRight());
	    System.out.print(" ");
	    System.out.print(((BinOp)expr).getName() + " ");
	}
    }
    
    public static void imprimerInfixeParenthese (Expr expr) {
	if (getClassName(expr).equals("IntConst")) {
	    System.out.print(((IntConst)expr).getValue());
	} else if (getClassName(expr).equals("EVar")) {
	    System.out.print(((EVar)expr).getName());
	} else {// (expr.getClassName().equals("BinOp"))
	    System.out.print("(");
	    imprimerInfixeParenthese(((BinOp)expr).getLeft());
	    System.out.print(" ");
	    System.out.print(((BinOp)expr).getName());
	    System.out.print(" ");
	    imprimerInfixeParenthese(((BinOp)expr).getRight());
	    System.out.print(")");
	}
    }
    
    public static void main(String args[]) {
	FileInputStream entree = null;
	parser          analyseur_synt;
	Lexer           analyseur_lex;

	try {
	    entree = new java.io.FileInputStream(args[0]);
	} catch (java.io.FileNotFoundException e) {
	    System.out.println("Fichier " + args[0] + " non trouve.");
	    System.exit(0);
	}

	analyseur_lex  = new Lexer(new java.io.InputStreamReader(entree));
	analyseur_synt = new parser(analyseur_lex, new ComplexSymbolFactory());

	try {
	    // Symbol arbre = analyseur_synt.parse();
	    Expr expr = (Expr)(analyseur_synt.parse().value);

	    System.out.println("Impression en prefixe :");
	    imprimerPrefixe(expr);
	    System.out.println();
	    System.out.println("Impression en postfixe :");
	    imprimerPostfixe(expr);
	    System.out.println();
	    System.out.println("Impression en infixe parenthese :");
	    imprimerInfixeParenthese(expr);
	    System.out.println();

	} catch (Exception e) {
	    System.out.println("Erreur d'analyse." + e);
	    System.exit(0);
	}
    }
}
