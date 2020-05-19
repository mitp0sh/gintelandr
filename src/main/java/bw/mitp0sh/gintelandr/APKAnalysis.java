package bw.mitp0sh.gintelandr;

import java.io.File;

public class APKAnalysis extends AbstractAnalysis {
	public APKAnalysis(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_APK;
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
		APKAnalysis tempAnalysis = new APKAnalysis(getParent());
		
		// TODO - Implement APK analysis
		
		analysis = tempAnalysis;		
		return analysis;
	}	
}
