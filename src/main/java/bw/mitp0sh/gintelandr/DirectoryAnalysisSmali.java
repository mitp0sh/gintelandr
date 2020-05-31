package bw.mitp0sh.gintelandr;

import java.io.File;

public class DirectoryAnalysisSmali extends AbstractFileAnalysis {
	private String smaliPrefix = "";
	
	public DirectoryAnalysisSmali(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_DIRECTORY_SMALI;
		this.parent = parent;
	}
	
	public DirectoryAnalysisSmali(AbstractAnalysis parent, String smaliPrefix) {
		this(parent);
		this.smaliPrefix = smaliPrefix;
	}
	
	public String getSmaliPrefix() {
		return smaliPrefix;
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
	public AbstractAnalysis analyze(File file) {
		if(!isType(file, type)) {
			return null;
		}

		for(File current : file.listFiles()) {
			AbstractAnalysis currentAnalysis = null;
			
			if(current.isDirectory()) {
				analyze(current);
			} else 
			if(current.isFile()) {			
				currentAnalysis = new FileTypeAnalysis(this).analyze(current);				
				if(currentAnalysis != null && currentAnalysis.getClass().equals(FileAnalysisSmali.class)) {
					addChild(currentAnalysis);
				}
			}
		}
		
		return this;
	}
}
