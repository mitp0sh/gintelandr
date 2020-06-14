package bw.mitp0sh.gintelandr.util;

import java.io.File;
import java.util.ArrayList;

public interface RecursiveFileScannerCallable {
	public ArrayList<String> scanLines(File file);
}
