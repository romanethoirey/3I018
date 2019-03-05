package microjs.jcompiler.frontend.ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KInt;

public class IntConst extends Expr {
	private int value;
	
	public IntConst(int value, Location startPos, Location endPos) {
		super(startPos, endPos);
		this.value = value;
	}
	
	@Override
	public KInt expand() {
		return new KInt(value, getStartPos(), getEndPos());
	}
	
	@Override
	protected void prettyPrint(StringBuilder buf) {
		buf.append(value);
	}
}
