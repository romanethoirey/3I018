package microjs.jcompiler.frontend.ast;

import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KWhen;
import microjs.jcompiler.middleend.kast.KSeq;
import microjs.jcompiler.middleend.kast.KStatement;
import microjs.jcompiler.utils.DotGraph;

public class When extends Statement {
    private Expr cond;
    private List<Statement> corps;
    
    public When(Expr cond, List<Statement> corps,
		 Location startPos, Location endPos) {
    	super(startPos, endPos);		
    	this.cond  = cond;
    	this.corps = corps;
    }
    
    @Override
    public KWhen expand() {
    	Location whenStartPos  = getStartPos();
    	Location corpsEndPos    = getEndPos();
    	List<KStatement> kcorps = Statement.expandStatements(corps);
    	KStatement kcorps_s = KSeq.buildKSeq(kcorps,
					     whenStartPos, corpsEndPos);
    	return new KWhen(cond.expand(), kcorps_s,
			  getStartPos(), getEndPos());
    }
    
	@Override
	protected String buildDotGraph(DotGraph graph) {
		String whenNode = graph.addNode("When");
		String condNode = cond.buildDotGraph(graph);
		graph.addEdge(whenNode, condNode, "cond");
		String corpsNode = cond.buildDotGraph(graph);
		graph.addEdge(whenNode, corpsNode, "corps");
	
		return whenNode;
	}

    
    @Override
    protected void prettyPrint(StringBuilder buf, int indent_level) {
    	indent(buf, indent_level);
    	buf.append("when (");
    	cond.prettyPrint(buf);
    	buf.append(") {\n");
    	Statement.prettyPrintStatements(buf, corps, indent_level + 1);
    	indent(buf, indent_level);
    	buf.append("}\n");
    }
}
