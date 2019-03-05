package microjs.jcompiler.frontend.ast;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;
import microjs.jcompiler.middleend.kast.KProg;
import microjs.jcompiler.middleend.kast.KSeq;
import microjs.jcompiler.middleend.kast.KStatement;

public class Prog extends ASTNode {
	private String filename;
	private List<Statement> body;
	
	public Prog(String filename, List<Statement> body, Location startPos, Location endPos) {
		super(startPos, endPos);
		this.filename = filename;
		this.body = body;
	}
	
	@Override
	public KProg expand() {
		List<KStatement> kseq = Statement.expandStatements(body);
		KStatement kbody = KSeq.buildKSeq(kseq, getStartPos(), getEndPos());
		return new KProg(filename, kbody, getStartPos(), getEndPos());
	}
	
	@Override
	protected void prettyPrint(StringBuilder buf) {
		Statement.prettyPrintStatements(buf, body, 0);
	}
}
