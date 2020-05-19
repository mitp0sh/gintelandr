package bw.mitp0sh.gintelandr;

import java.io.File;

public abstract class AbstractAnalysis implements Analyzable {
	public AnalysisType type = AnalysisType.ANALYSIS_TYPE_UNKNOWN;
	protected AbstractAnalysis parent = null;
	
	protected enum AnalysisType {
		ANALYSIS_TYPE_UNKNOWN,
		ANALYSIS_TYPE_FILE_TYPE,
		ANALYSIS_TYPE_APK,
		ANALYSIS_TYPE_DIRECTORY;

		@Override
		public String toString() {
			if(this == ANALYSIS_TYPE_APK) {
				return "AT_APK";
			} else
			if(this.equals(ANALYSIS_TYPE_DIRECTORY)) {
				return "AT_DIRECTORY";
			} else
			if(this.equals(ANALYSIS_TYPE_FILE_TYPE)) {
				return "AT_FILE_TYPE";
			} else {
				return "AT_UNKNOWN";
			}
		}
	};
	
	@Override
	public AbstractAnalysis getParent() {
		return parent;
	}
	
	public static boolean isType(File file, AnalysisType analysisType) {
		boolean type = false;
		
		/* large heuristic switch to identify analysable files */
		switch(analysisType) {
			case ANALYSIS_TYPE_UNKNOWN:
			case ANALYSIS_TYPE_FILE_TYPE: {
				/* nothing to do here... */				
				break;
			} 
			case ANALYSIS_TYPE_APK: {
				if(!Util.getFileExtension(file).equals(Util.FILE_EXT_APK)) {
					/* does not have ".apk" extension - no apk! */
					break;
				}	
				
				/* apk file successfully identified */
				type = true;
				break;
			} 
			case ANALYSIS_TYPE_DIRECTORY: {
				if(!file.isDirectory()) {
					/* not a directory, byebye! */
					break;
				}	
				
				/* directory successfully identified */
				type = true;
				break;
			} default: {
				/* nothing to do here as well... */
				break;
			}
		}
		
		return type;
	}
}
