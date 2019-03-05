package ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import kast.KASTNode;

public abstract class ASTNode {
	Location startPos;  // objects with getLine and getColumn
	Location endPos;
	
	
	/* package */ ASTNode(Location startPos, Location endPos) {
		this.startPos = startPos;
		this.endPos = endPos;
	}
	
	public Location getStartPos() {
		return startPos;
	}
	
	public Location getEndPos() {
		return endPos;
	}
}
