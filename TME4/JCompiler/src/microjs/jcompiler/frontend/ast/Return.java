package microjs.jcompiler.frontend.ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KReturn;

public class Return extends Statement {
    private Expr expr;
    
    public Return(Expr expr, Location startPos, Location endPos) {
    	super(startPos, endPos);		
		this.expr = expr;
    }
    
    @Override
    public KReturn expand() {
    	return new KReturn(expr.expand(), getStartPos(), getEndPos());
    }

    
    @Override
    protected void prettyPrint(StringBuilder buf, int indent_level) {
    	indent(buf, indent_level);
    	buf.append("return ");
    	expr.prettyPrint(buf);
    }
}
