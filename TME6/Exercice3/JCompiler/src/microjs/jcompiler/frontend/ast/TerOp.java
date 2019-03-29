package microjs.jcompiler.frontend.ast;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KTerOp;
import microjs.jcompiler.middleend.kast.KSeq;
import microjs.jcompiler.middleend.kast.KStatement;
import microjs.jcompiler.utils.DotGraph;

public class TerOp extends Expr {
	private Expr cond;
	private List<Statement> vrai;
	private List<Statement> faux;
	
	public TerOp(Expr cond, List<Statement> vrai, List<Statement> faux, Location startPos, Location endPos) {
		super(startPos, endPos);
		this.cond = cond;
		this.vrai = vrai;
		this.faux = faux;
	}
	
	@Override
	public KTerOp expand() {

		Location VraiStartPos = getStartPos(); // XXX: good approximation ?
    		Location VraiEndPos = getStartPos();
    		List<KStatement> kvrais = Statement.expandStatements(vrai);
    		KStatement kvrai = KSeq.buildKSeq(kvrais, vraiStartPos, vraiEndPos);
    		
    		Location fauxStartPos = vraiEndPos; // XXX: good approximation ?
    		Location fauxEndPos = vraiEndPos;
    		List<KStatement> kfauxx = Statement.expandStatements(faux);
    		KStatement kfaux = KSeq.buildKSeq(kfauxx, fauxStartPos, fauxEndPos);
    		return new KTerOp(cond.expand(), kvrai, kfaux, getStartPos(), getEndPos());
		
	}
	
	@Override
	protected String buildDotGraph(DotGraph graph) {
		String teropNode = graph.addNode("Terop[" + cond + "]");

		String vraiNode = vrai.buildDotGraph(graph);
		graph.addEdge(teropNode, vraiNode, "vrai");
	
		String fauxNode = faux.buildDotGraph(graph);
		graph.addEdge(teropNode, fauxNode, "faux");

		return teropNode;
	}

	@Override
	protected void prettyPrint(StringBuilder buf) {
		cond.prettyPrint(buf);
		buf.append(cond);
		buf.append("?");
		vrai.prettyPrint(buf);
		buf.append(":");
		faux.prettyPrint(buf);
	}
		
}
