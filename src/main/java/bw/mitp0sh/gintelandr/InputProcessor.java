package bw.mitp0sh.gintelandr;

import java.io.File;

import bw.mitp0sh.gintelandr.AbstractAnalysis.AnalysisType;

public class InputProcessor {
	public static AbstractAnalysis analyze(File file) {
		if(file == null) {
			return null;
		}
		
		/* main analysis loop */
		AbstractAnalysis result = new UnknownAnalysis(null);
		for (AnalysisType type : AnalysisType.values()) { 
		    if(!result.getClass().getName().equals(UnknownAnalysis.class.getName())) {
		    	/* analysis came to an end - time to stop */
		    	break;
		    }
			
			boolean isOfType = AbstractAnalysis.isType(file, type);
		    if(!isOfType) {
		    	continue;
		    }
		    
		    switch(type) {
				case ANALYSIS_TYPE_APK: {
					result = new APKAnalysis(null).analyze(file);
					break;
				} 
				case ANALYSIS_TYPE_DIRECTORY: {
					result = new DirectoryAnalysis(null).analyze(file);
					break;
				} default: {
					break;
				}
			}
		}
		
		return result;
	}
}
