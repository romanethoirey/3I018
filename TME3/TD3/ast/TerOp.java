package ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import kast.KExpr;

public class TerOp extends Expr {
    private String name;
    private Expr test;
    private Expr altv;
    private Expr altf;
    
    public TerOp(String name, Expr test, Expr altv, Expr altf,
		 Location startPos, Location endPos) {
	super(startPos, endPos);
	this.name = name;
	this.test = test;
	this.altv = altv;
	this.altf = altf;
    }

    public String getName () { return name; }
    public Expr   getTest () { return test; }
    public Expr   getAltv () { return altv; }
    public Expr   getAltf () { return altf; }
}
