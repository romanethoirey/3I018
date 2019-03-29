package microjs.jcompiler.middleend.kast;

import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class KTerOp extends KStatement {
    private KExpr cond;
    private KStatement kvrai;
    private KStatement kfaux;
    
    public KTerOp(KExpr cond, KStatement vrai, KStatement faux, Location startPos, Location endPos) {
    	super(startPos, endPos);		
    	this.cond = cond;
    	this.kvrai = vrai;
    	this.kfaux = faux;
    }
    
    @Override
    public void accept(KASTVisitor visitor) {
    	visitor.visit(this);
    }

	public KExpr getCond() {
		return cond;
	}
	
	public KStatement getVrai() {
		return kvrai;
	}
	
	public KStatement getFaux() {
		return kfaux;
	}
}
