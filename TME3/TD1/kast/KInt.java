package kast;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class KInt extends KExpr {
    private int value;
    
    public KInt(int value, Location startPos, Location endPos) {
	super(startPos, endPos);
	this.value = value;
    }
    
    public int getValue() {
    	return value;
    }
}
