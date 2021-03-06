package microjs.jcompiler.frontend.ast;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KCall;
import microjs.jcompiler.middleend.kast.KEVar;
import microjs.jcompiler.middleend.kast.KExpr;

public class BinOp extends Expr {
	private String name;
	private Expr left;
	private Expr right;
	
	public BinOp(String name, Expr left, Expr right, Location startPos, Location endPos) {
		super(startPos, endPos);
		this.name = name;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public KCall expand() {
		List<KExpr> args = new ArrayList<KExpr>();
		args.add(left.expand());
		args.add(right.expand());
		return new KCall(new KEVar(name, getStartPos(), getEndPos()), args, getStartPos(), getEndPos());
	}
	
	@Override
	protected void prettyPrint(StringBuilder buf) {
		buf.append("(");
		left.prettyPrint(buf);
		buf.append(name);
		right.prettyPrint(buf);
		buf.append(")");
	}
		
}
