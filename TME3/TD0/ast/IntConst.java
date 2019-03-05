package ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import kast.KInt;

public class IntConst extends Expr {
    private int value;
    
    public IntConst(int value, Location startPos, Location endPos) {
	super(startPos, endPos);
	this.value = value;
    }

    public int getValue () { return value; }
}
