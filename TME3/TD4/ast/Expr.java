package ast;

import java_cup.runtime.ComplexSymbolFactory.Location;
import kast.KExpr;

public class Expr extends ASTNode {

    /* package */ Expr(Location startPos, Location endPos) {
	super(startPos, endPos);
    }
}
