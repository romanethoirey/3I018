package kast;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class KEVar extends KExpr {
    private String name;
    
    public KEVar(String name, Location startPos, Location endPos) {
	super(startPos, endPos);
	this.name = name;
    }
    
    public String getName() {
    	return name;
    }
}
