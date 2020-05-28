package bw.mitp0sh.gintelandr;

import java.io.File;

public class DirectoryAnalysis extends AbstractAnalysis {
	public DirectoryAnalysis(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_DIRECTORY;
		this.parent = parent;
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
			switch(current.getName()) {
				case DirectoryAnalysisType.SMALI: {					
					DirectoryAnalysisSmali directoryAnalysisSmali = new DirectoryAnalysisSmali(this);
					currentAnalysis = directoryAnalysisSmali.analyze(current);
					if(currentAnalysis != null && currentAnalysis.getClass().equals(DirectoryAnalysisSmali.class)) {						
						addChild(directoryAnalysisSmali);
					}
					break;
				}
				default: {
					break;
				}
			}
		}
				
		return this;
	}
	
	class DirectoryAnalysisType {
		public static final String SMALI = "smali";
	}
}
