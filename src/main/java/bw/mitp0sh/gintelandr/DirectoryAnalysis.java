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
		
		AbstractAnalysis analysis = new UnknownAnalysis(getParent());
		DirectoryAnalysis tempAnalysis = new DirectoryAnalysis(getParent());
		
		// TODO - Implement directory analysis
		
		analysis = tempAnalysis;		
		return analysis;
	}
}
