package bw.mitp0sh.gintelandr;

import java.io.File;

import bw.mitp0sh.gintelandr.AbstractAnalysis.AnalysisType;

public interface Analyzable {
	public AnalysisType getType();
	public String getTypeString();
	public AbstractAnalysis getParent();
	
	public AbstractAnalysis analyze(File file);
}
