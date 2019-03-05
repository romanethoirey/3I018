package microjs.jcompiler.frontend.ast;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KClosure;
import microjs.jcompiler.middleend.kast.KSeq;
import microjs.jcompiler.middleend.kast.KStatement;
import microjs.jcompiler.middleend.kast.KVoidExpr;

public class Let extends Statement {
    private String name;
    private Expr expr;
    private List<Statement> body;
    
    public Let(String name, Expr expr, List<Statement> body, Location startPos, Location endPos) {
    	super(startPos, endPos);		
    	this.name = name;
		this.expr = expr;
    	this.body = body;
    }
    
    @Override
    public KVoidExpr expand() {
    	List<String> params = new ArrayList<String>();
    	params.add(name);
    	
    	List<KStatement> kstmts = Statement.expandStatements(body);
    	KStatement kbody = KSeq.buildKSeq(kstmts, getStartPos(), getEndPos());
    	
    	return new KVoidExpr(
    			new KClosure(params, kbody, getStartPos(), getEndPos()),
    			getStartPos(), getEndPos());
    }
    
    @Override
    protected void prettyPrint(StringBuilder buf, int indent_level) {
    	indent(buf, indent_level);
    	buf.append("let ");
    	buf.append(name);
    	buf.append(" = ");
    	expr.prettyPrint(buf);
    	buf.append(";\n");
    	Statement.prettyPrintStatements(buf, body, indent_level);
    }
}
