package bw.mitp0sh.gintelandr;

import java.io.File;

public class FileTypeAnalysis extends AbstractAnalysis {
	public FileTypeAnalysis(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_FILE_TYPE;
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
		if(file == null) {
			return null;
		}
		
		/* not a file we can analyze */
		return new UnknownAnalysis(getParent());
	}
}
