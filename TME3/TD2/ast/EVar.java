package ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import kast.KEVar;

public class EVar extends Expr {
    private String name;
    
    public EVar(String name, Location startPos, Location endPos) {
	super(startPos, endPos);
	this.name = name;
    }

    public String getName () { return name; }
}
