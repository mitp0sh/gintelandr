package bw.mitp0sh.gintelandr;

import java.io.File;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectoryAnalysis extends AbstractFileAnalysis {
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
	public AbstractAnalysis analyze(File file, Object[] params) {
		if(!isType(file, type)) {
			return null;
		}		
		
		/* analysis loop */
		for(File current : file.listFiles()) {
			AbstractAnalysis currentAnalysis = null;
			
			/* setup matchers required for processing decision */
			Matcher smaliMatcher = Pattern.compile("(^(smali))$|(^(smali_classes\\d*))$").matcher(current.getName()); // construction here - https://regexr.com/55jvu
			
			/* case - smali directory processing */			
			if(smaliMatcher.matches()) {
				/* extract smali prefix, required later for referencing */
				String smaliPrefix = smaliMatcher.group(0) != null ? smaliMatcher.group(0) : smaliMatcher.group(3);
				
				int index = 0;
				if(smaliPrefix.equals("smali")) {
					index = 1;
				} else {
					index = Integer.parseInt(smaliPrefix.substring(13));
				}
				
				try {
					DatabaseHelper.insertDex(index, getParent().toString(), current.getAbsolutePath());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				params = new Object[] {index};
				
				/* create and trigger new child analysis */
				DirectoryAnalysisSmali directoryAnalysisSmali = new DirectoryAnalysisSmali(this);
				currentAnalysis = directoryAnalysisSmali.analyze(current, params);
				if(currentAnalysis != null && currentAnalysis.getClass().equals(DirectoryAnalysisSmali.class)) {						
					/* register analysis */
					addChild(directoryAnalysisSmali);
				}
			} else {
				switch(current.getName()) {
					// TODO - add other cases for extended analysis
					default: {
						break;
					}
				}
			}
		}
				
		return this;
	}
	
	class DirectoryAnalysisType {
		public static final String SMALI = "smali";
	}
}
