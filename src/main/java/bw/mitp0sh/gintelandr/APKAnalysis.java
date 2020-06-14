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
	public AbstractAnalysis analyze(File file, Object[] params) {
		if(!isType(file, type)) {
			return null;
		}
		
		// TODO - Implement APK analysis
				
		return this;
	}	
}
