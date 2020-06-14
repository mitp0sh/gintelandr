package bw.mitp0sh.gintelandr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class RecursiveFileScannerMethodA implements Cloneable, Serializable, RecursiveFileScannerCallable {
    private static final long serialVersionUID = -5664123213376258176L;
    private static RecursiveFileScannerMethodA SINGLE_INSTANCE = null;
    
    private RecursiveFileScannerMethodA() {}
    
    public static RecursiveFileScannerMethodA getInstance() {
        if (SINGLE_INSTANCE == null) {
            synchronized (RecursiveFileScannerMethodA.class) {
                if (SINGLE_INSTANCE == null) {
                    SINGLE_INSTANCE = new RecursiveFileScannerMethodA();
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
		
		try (FileReader reader = new FileReader(file.getAbsolutePath());
			     BufferedReader bufferedReader = new BufferedReader((reader))) {			  
			Iterator<String> iter = bufferedReader.lines().iterator();
			while(iter.hasNext()) {
				lines.add(iter.next());				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
}
