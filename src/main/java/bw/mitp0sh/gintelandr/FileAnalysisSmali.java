package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.ArrayList;

import bw.mitp0sh.gintelandr.util.FQCNParserUtil;
import bw.mitp0sh.gintelandr.util.RecursiveFileScannerMethodD;

public class FileAnalysisSmali extends AbstractAnalysis {
	public FileAnalysisSmali(AbstractAnalysis parent) {
		super.type = AnalysisType.ANALYSIS_TYPE_FILE_SMALI;
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
		if (!isType(file, type)) {
			return new UnknownAnalysis(getParent());
		}
		
		/* fetch all lines from smali file */
		ArrayList<String> lines = RecursiveFileScannerMethodD.getInstance().scanLines(file);
		if (lines == null) {
			return new UnknownAnalysis(getParent());
		}
		
        // TODO - parse class modifier
		//System.out.println(lines.get(0));
		
		/* extract class */
		String fqcnClassStr = lines.get(0).substring(lines.get(0).indexOf(" L")+1);
		@SuppressWarnings("unused")
		FQCNParserUtil fqcnClass = new FQCNParserUtil(fqcnClassStr);		
		
		/* extract super class */
		String fqcnSuperClassStr = lines.get(1).substring(lines.get(1).indexOf(" L")+1);
		@SuppressWarnings("unused")
		FQCNParserUtil fqcnSuperClass = new FQCNParserUtil(fqcnSuperClassStr);
				
		/* extract source */
		@SuppressWarnings("unused")
		String source = "";
		if(lines.size() > 2) {
			String src = lines.get(2); 
			if(!src.equals("") && !src.equals(".source \"\"")) {
				source = src.substring(src.indexOf('"') + 1, src.length() - 1);
			}
		}
		
		// TODO - create db entry !!!
		
		/* not a file we can analyze */
		return new UnknownAnalysis(getParent());
	}
}
