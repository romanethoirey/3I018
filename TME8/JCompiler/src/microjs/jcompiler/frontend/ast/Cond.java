package microjs.jcompiler.frontend.ast;

import java.util.*;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.*;
import microjs.jcompiler.utils.DotGraph;

public class Cond extends Expr {
    private Expr cond, altv, altf;
	
    public Cond (Expr cond, Expr altv, Expr altf,
		 Location startPos, Location endPos) {
	super(startPos, endPos);
	this.cond = cond;
	this.altv = altv;
	this.altf = altf;
    }
	
    @Override
    public KCall expand() {
	KStatement         kaltv;
	KStatement         kaltf;
	KStatement         kbody;
	LinkedList<String> params = new LinkedList<String>();
	List<KExpr>        kcond  = new LinkedList<KExpr>();
	KClosure           klambda;
	
	kaltv = new KReturn(altv.expand(), getStartPos(), getEndPos());
	kaltf = new KReturn(altf.expand(), getStartPos(), getEndPos());
	kbody = new KIf(new KEVar("$x$", getStartPos(), getEndPos()),
			kaltv, kaltf, getStartPos(), getEndPos());
	params.add("$x$");
	klambda = new KClosure(params, kbody, getStartPos(), getEndPos());
	kcond.add(cond.expand());
	return new KCall(klambda, kcond, getStartPos(), getEndPos());
    }
	
    @Override
    protected String buildDotGraph(DotGraph graph) {
	String topNode = graph.addNode("Cond");
	
	String condNode = cond.buildDotGraph(graph);
	graph.addEdge(topNode, condNode, "cond");
	
	String altvNode = altv.buildDotGraph(graph);
	graph.addEdge(topNode, altvNode, "altv");
	
	String altfNode = altf.buildDotGraph(graph);
	graph.addEdge(topNode, altfNode, "altf");
	
	return topNode;
    }

    @Override
    protected void prettyPrint(StringBuilder buf) {
	buf.append("(");
	cond.prettyPrint(buf);
	buf.append(" ? ");
	altv.prettyPrint(buf);
	buf.append(" : ");
	altf.prettyPrint(buf);
	buf.append(")");
    }
}
