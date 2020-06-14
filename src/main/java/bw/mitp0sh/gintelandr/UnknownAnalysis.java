package bw.mitp0sh.gintelandr;

import java.io.File;

public class UnknownAnalysis extends AbstractAnalysis {
	public UnknownAnalysis(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_UNKNOWN;
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
		return this;
	}
}
