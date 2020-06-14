package bw.mitp0sh.gintelandr.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public class RecursiveFileScannerMethodD implements Cloneable, Serializable, RecursiveFileScannerCallable {
    private static final long serialVersionUID = -5664124213336258076L;
    private static RecursiveFileScannerMethodD SINGLE_INSTANCE = null;
    
    private RecursiveFileScannerMethodD() {}
    
    public static RecursiveFileScannerMethodD getInstance() {
        if (SINGLE_INSTANCE == null) {
            synchronized (RecursiveFileScannerMethodD.class) {
                if (SINGLE_INSTANCE == null) {
                    SINGLE_INSTANCE = new RecursiveFileScannerMethodD();
                }
            }
        }
        
        return SINGLE_INSTANCE;
    }
    
    @Override 
    protected Object clone() throws CloneNotSupportedException {
    	return SINGLE_INSTANCE;
    }
    
    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }

	@Override
	public ArrayList<String> scanLines(File file) {		
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			Stream<String> slines = Files.lines(Path.of(file.getAbsolutePath()));
			Iterator<String> iter = slines.iterator();
			while(iter.hasNext()) {
				lines.add(iter.next());
			}
			slines.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
}
