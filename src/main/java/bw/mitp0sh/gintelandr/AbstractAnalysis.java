package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractAnalysis implements Analyzable {
	public AnalysisType type = AnalysisType.ANALYSIS_TYPE_UNKNOWN;
	protected AbstractAnalysis parent = null;
	public ArrayList<AbstractAnalysis> children = new ArrayList<>();
	protected HashMap<Class<AbstractAnalysis>, ArrayList<AbstractAnalysis>> orderedChildren = new HashMap<Class<AbstractAnalysis>, ArrayList<AbstractAnalysis>>();
	
	protected enum AnalysisType {
		ANALYSIS_TYPE_UNKNOWN,
		ANALYSIS_TYPE_FILE_TYPE,
		ANALYSIS_TYPE_APK,
		ANALYSIS_TYPE_DIRECTORY,
		ANALYSIS_TYPE_DIRECTORY_SMALI,
		ANALYSIS_TYPE_FILE_SMALI;

		@Override
		public String toString() {
			if(this == ANALYSIS_TYPE_APK) {
				return "AT_APK";
			} else
			if(this.equals(ANALYSIS_TYPE_DIRECTORY)) {
				return "AT_DIRECTORY";
			} else
			if(this.equals(ANALYSIS_TYPE_DIRECTORY_SMALI)) {
				return "AT_DIRECTORY_SMALI";
			} else
			if(this.equals(ANALYSIS_TYPE_FILE_SMALI)) {
				return "AT_FILE_SMALI";
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
	
	@Override
	public void addChild(AbstractAnalysis analysis) {
		@SuppressWarnings("unchecked")
		Class<AbstractAnalysis> clazz = (Class<AbstractAnalysis>)analysis.getClass();
		ArrayList<AbstractAnalysis> kind = orderedChildren.get(clazz);
		if(kind == null) {
			kind = new ArrayList<>();
			orderedChildren.put(clazz, kind);
		}
		kind.add(analysis);
		children.add(analysis);
	}
	
	@Override
	public boolean removeChild(AbstractAnalysis analysis) {
		@SuppressWarnings("unchecked")
		Class<AbstractAnalysis> clazz = (Class<AbstractAnalysis>)analysis.getClass();
		ArrayList<AbstractAnalysis> kind = orderedChildren.get(clazz);
		if(kind != null && kind.size() != 0) {
			if(kind.size() == 1 && kind.get(0).equals(analysis)) {
				orderedChildren.remove(clazz);
			} else {
				int index = 0;
				for(AbstractAnalysis current : kind) {
					if(current.equals(analysis)) {
						break;
					}
					index++;
				}
				kind.remove(index);
			}
		}
		
		return false;
	}
	
	@Override
	public ArrayList<AbstractAnalysis> getChildren() {
		return children;
	}
	
	@Override
	public boolean hasChildren() {
		return children.size() != 0;
	}
	
	@Override
	public int getNumChildren() {
		return children.size();
	}
	
	@Override
	public ArrayList<AbstractAnalysis> getChildrenKind(Class<AbstractAnalysis> clazz) {
		return orderedChildren.get(clazz);
	}
	
	@Override
	public boolean hasChildrenKind(Class<AbstractAnalysis> clazz) {
		ArrayList<AbstractAnalysis> list = orderedChildren.get(clazz);
		if(list != null && list.size() != 0) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public int getNumChildrenKind(Class<AbstractAnalysis> clazz) {
		ArrayList<AbstractAnalysis> list = orderedChildren.get(clazz);
		if(list != null) {
			return list.size();
		}
		
		return 0;
	}
	
	public static boolean isType(File file, AnalysisType analysisType) {
		boolean type = false;
		
		if(file == null) {
			return type;
		}
		
		/* large heuristic switch to identify analysable files */
		switch(analysisType) {
			case ANALYSIS_TYPE_UNKNOWN:
			case ANALYSIS_TYPE_FILE_TYPE: {
				if(file.isFile()) {					
					type = true;
				}				
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
			} 
			case ANALYSIS_TYPE_DIRECTORY_SMALI: {
				if(!file.isDirectory()) {
					/* not a directory, byebye! */
					break;
				}	
				
				/* directory successfully identified */
				type = true;
				break;
			} 
			case ANALYSIS_TYPE_FILE_SMALI: {				
				Matcher smaliFileExtMatcher = Pattern.compile(".*((\\.smali))$").matcher(file.getName());	
				if(!smaliFileExtMatcher.matches()) {					
					break;
				}				
				
				/* smali file successfully identified */
				type = true;
				
				break;
			} default: {
				/* nothing to do here as well... */
				break;
			}
		}
		
		return type;
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
