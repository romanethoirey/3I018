package microjs.jcompiler.frontend.ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KVar;

public class Var extends Statement {
    private String name;
    private Expr expr;
    
    public Var(String name, Expr expr, Location startPos, Location endPos) {
    	super(startPos, endPos);		
    	this.name = name;
		this.expr = expr;
    }
    
    @Override
    public KVar expand() {
    	return new KVar(name, expr.expand(), getStartPos(), getEndPos());
    }
    
    @Override
    protected void prettyPrint(StringBuilder buf, int indent_level) {
    	indent(buf, indent_level);
    	buf.append("var ");
    	buf.append(name);
    	buf.append(" = ");
    	expr.prettyPrint(buf);
    }
}
