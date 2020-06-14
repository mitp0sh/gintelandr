package bw.mitp0sh.gintelandr.util;

public class FQCNParserUtil implements FQCNParsable {
	private String fqcn = null;
	private String className = "";
	private String packageName = "";
	private String[] packageNameFragments = null;
	
	public FQCNParserUtil(String fqcn) {
		this.fqcn = fqcn;		
		fqcn.substring(fqcn.indexOf(" L")+1);
		if (fqcn.indexOf('/') != -1) {
			String[] splitParts = fqcn.split("\\/");
			className = splitParts[splitParts.length -1];
			int numFragments = splitParts.length - 1;
			packageNameFragments = new String[numFragments];
			for (int i = 0; i < numFragments; i++) {
				packageName += splitParts[i] + ".";				
				packageNameFragments[i] = splitParts[i];				
			}
			packageNameFragments[0] = packageNameFragments[0].substring(1, packageNameFragments[0].length());
		} else {
			className = fqcn;
			packageName += ".";
			packageNameFragments = new String[0];
		}
		
		className = className.substring(0, className.length() - 1);
		packageName = packageName.length() == 1 ? "" : packageName.substring(1, packageName.length() - 1);
	}

	@Override
	public String getFQCN() {
		return fqcn;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public String[] getPackageNameFragments() {
		return packageNameFragments;
	}

	@Override
	public int getNumPackageNameFragments() {		
		return packageNameFragments.length;
	}
	
	@Override
	public String toString() {
		String string = "";		
		string += "fqcn="+fqcn;
		string += ", className="+className;
		string += ", packageName="+packageName;
		string += ", packageNameFragments=";
		for(int i = 0; i < packageNameFragments.length; i++) {
			string += packageNameFragments[i] + "|";
		}
		string += "(numPackageNameFragments=" + packageNameFragments.length + ")";		
		return string;
	}
}
