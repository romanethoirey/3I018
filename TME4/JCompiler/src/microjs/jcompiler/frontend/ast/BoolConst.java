package microjs.jcompiler.frontend.ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KExpr;
import microjs.jcompiler.middleend.kast.KFalse;
import microjs.jcompiler.middleend.kast.KTrue;

public class BoolConst extends Expr {
	private boolean value;
	
	public BoolConst(boolean value, Location startPos, Location endPos) {
		super(startPos, endPos);
		this.value = value;
	}
	
	@Override
	public KExpr expand() {
		if(value == true) {
			return new KTrue(getStartPos(), getEndPos());
		} else {
			return new KFalse(getStartPos(), getEndPos());			
		}
	}
	
	@Override
	protected void prettyPrint(StringBuilder buf) {
		buf.append(value);
	}
}
