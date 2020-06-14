package bw.mitp0sh.gintelandr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class RecursiveFileScannerMethodB implements Cloneable, Serializable, RecursiveFileScannerCallable {
    private static final long serialVersionUID = -5664124213336258176L;
    private static RecursiveFileScannerMethodB SINGLE_INSTANCE = null;
    
    private RecursiveFileScannerMethodB() {}
    
    public static RecursiveFileScannerMethodB getInstance() {
        if (SINGLE_INSTANCE == null) {
            synchronized (RecursiveFileScannerMethodB.class) {
                if (SINGLE_INSTANCE == null) {
                    SINGLE_INSTANCE = new RecursiveFileScannerMethodB();
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
		
		try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(file.getAbsolutePath()))) {
			Iterator<String> iter = bufferedReader.lines().iterator();
			while(iter.hasNext()) {
				lines.add(iter.next());				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
}
