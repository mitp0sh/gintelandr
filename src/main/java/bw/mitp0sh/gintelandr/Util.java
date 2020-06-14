package bw.mitp0sh.gintelandr;

import java.io.File;

public class Util {
	
	public static final String FILE_EXT_NONE = ".";
	public static final String FILE_EXT_APK = ".apk";
	
	public static long BM_START = 0;	
	
	public static String getFileExtension(File file) {
        String extension = "";
 
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = FILE_EXT_NONE;
        }
 
        return extension;
    }
	
	public static void startBenchmarkRun() {
		BM_START = (System.currentTimeMillis()/1000);
	}
	
	public static long getBenchmarkResultInSeconds() {
		return (System.currentTimeMillis()/1000) - BM_START;
	}
}
