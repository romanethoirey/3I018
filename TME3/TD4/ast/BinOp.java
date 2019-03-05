package ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import kast.KExpr;

public class BinOp extends Expr {
    private String name;
    private Expr left;
    private Expr right;
    
    public BinOp(String name, Expr left, Expr right,
		 Location startPos, Location endPos) {
	super(startPos, endPos);
	this.name = name;
	this.left = left;
	this.right = right;
    }

    public String getName () { return name; }
    public Expr   getLeft () { return left; }
    public Expr   getRight () { return right; }
}
