package bw.mitp0sh.gintelandr.util;

public interface FQCNParsable {
	public String getFQCN();
	public String getClassName();
	public String getPackageName();
	public String[] getPackageNameFragments();
	public int getNumPackageNameFragments();
}
