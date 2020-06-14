package bw.mitp0sh.gintelandr;

import java.io.File;

public abstract class AbstractFileAnalysis extends AbstractAnalysis implements AnalyzableFilebased {
	private File file = null;
	
	@Override
	public File getFile() {
		return file;
	}
}
