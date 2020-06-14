package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.ArrayList;

import bw.mitp0sh.gintelandr.AbstractAnalysis.AnalysisType;

public interface Analyzable {
	public AnalysisType getType();
	public String getTypeString();
	public AbstractAnalysis getParent();
	public void addChild(AbstractAnalysis analysis);
	public boolean removeChild(AbstractAnalysis analysis);
	public ArrayList<AbstractAnalysis> getChildren();
	public boolean hasChildren();
	public int getNumChildren();
	public ArrayList<AbstractAnalysis> getChildrenKind(Class<AbstractAnalysis> clazz);
	public boolean hasChildrenKind(Class<AbstractAnalysis> clazz);
	public int getNumChildrenKind(Class<AbstractAnalysis> clazz);
	
	public AbstractAnalysis analyze(File file, Object[] params);
}
