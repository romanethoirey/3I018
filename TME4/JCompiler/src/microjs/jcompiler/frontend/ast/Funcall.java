package microjs.jcompiler.frontend.ast;

import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KCall;
import microjs.jcompiler.middleend.kast.KExpr;

public class Funcall extends Expr {
    private Expr fun;
    private List<Expr> arguments;
    
    public Funcall(Expr fun, List<Expr> arguments, Location startPos, Location endPos) {
    	super(startPos, endPos);		
		this.fun = fun;
		this.arguments = arguments;
    }
    
    @Override
    public KCall expand() {
    	List<KExpr> kargs = Expr.expandExprs(arguments);
    	return new KCall(fun.expand(), kargs, getStartPos(), getEndPos());
    }
    
    @Override
    protected void prettyPrint(StringBuilder buf) {
    	fun.prettyPrint(buf);
    	buf.append("(");
    	String sep = "";
    	for(Expr arg : arguments) {
    		buf.append(sep);
    		arg.prettyPrint(buf);
    		if(sep.equals("")) {
    			sep = ", ";
    		}
    	}
    	buf.append(")");
    }
}
