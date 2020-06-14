package bw.mitp0sh.gintelandr;

import java.io.File;

public class DirectoryAnalysisSmali extends AbstractFileAnalysis {
	private int index = 0;
	
	public DirectoryAnalysisSmali(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_DIRECTORY_SMALI;
		this.parent = parent;
	}
	
	public int getSmaliIndex() {
		return index;
	}
	
	@Override
	public AnalysisType getType() {
		return type;
	}

	@Override
	public String getTypeString() {
		return type.toString();
	}	

	@Override
	public AbstractAnalysis analyze(File file, Object[] params) {
		if(!isType(file, type)) {
			return null;
		}
		
		if(params != null && params.length == 1) {
			index = ((Integer)params[0]).intValue();
		}

		for(File current : file.listFiles()) {
			AbstractAnalysis currentAnalysis = null;
			
			if(current.isDirectory()) {
				analyze(current, params);
			} else 
			if(current.isFile()) {			
				currentAnalysis = new FileTypeAnalysis(this).analyze(current, params);				
				if(currentAnalysis != null && currentAnalysis.getClass().equals(FileAnalysisSmali.class)) {
					addChild(currentAnalysis);
				}
			}
		}
		
		return this;
	}
}
