package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		if(!isType(file, type)) {
			return new UnknownAnalysis(getParent());
		}
		
		Matcher matcher = Pattern.compile(".*((\\.smali))$").matcher(file.getName()); // generated and taken here: https://regexr.com/55lk2
		if(matcher.matches()) {			
			return new FileAnalysisSmali(getParent()).analyze(file);
		}
		
		/* not a file we can analyse */
		return new UnknownAnalysis(getParent());
	}
}
