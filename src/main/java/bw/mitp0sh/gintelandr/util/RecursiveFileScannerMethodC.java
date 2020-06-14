package bw.mitp0sh.gintelandr.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class RecursiveFileScannerMethodC implements Cloneable, Serializable, RecursiveFileScannerCallable {
    private static final long serialVersionUID = -5264124213336258176L;
    private static RecursiveFileScannerMethodC SINGLE_INSTANCE = null;
    
    private RecursiveFileScannerMethodC() {}
    
    public static RecursiveFileScannerMethodC getInstance() {
        if (SINGLE_INSTANCE == null) {
            synchronized (RecursiveFileScannerMethodC.class) {
                if (SINGLE_INSTANCE == null) {
                    SINGLE_INSTANCE = new RecursiveFileScannerMethodC();
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
		
		try (FileInputStream is = new FileInputStream(file.getAbsolutePath());
			BufferedInputStream bis = new BufferedInputStream(is)) {
			try(Scanner s = new Scanner(bis)) {  
				while(s.hasNextLine()) { 
					lines.add(s.nextLine());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
}
