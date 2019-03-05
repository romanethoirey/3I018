package microjs.jcompiler.frontend.ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KVoidExpr;

public class VoidExpr extends Statement {
	private Expr expr;
	
	public VoidExpr(Expr expr, Location startPos, Location endPos) {
		super(startPos, endPos);
		this.expr = expr;
	}
	
	@Override
	public KVoidExpr expand() {
		return new KVoidExpr(expr.expand(), getStartPos(), getEndPos());
	}
	
	@Override
	protected void prettyPrint(StringBuilder buf, int indent_level) {
		indent(buf, indent_level);
		expr.prettyPrint(buf);
	}

}
