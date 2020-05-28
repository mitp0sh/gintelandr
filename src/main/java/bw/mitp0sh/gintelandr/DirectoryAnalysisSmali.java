package bw.mitp0sh.gintelandr;

import java.io.File;

public class DirectoryAnalysisSmali extends AbstractAnalysis {
	public DirectoryAnalysisSmali(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_DIRECTORY_SMALI;
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
			// TODO - analyze class files
			System.out.println(current.getName());
		}
		
		return this;
	}
}
