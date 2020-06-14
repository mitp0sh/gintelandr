package bw.mitp0sh.gintelandr;

import java.io.File;

import bw.mitp0sh.gintelandr.AbstractAnalysis.AnalysisType;

public class InputProcessor {
	
	public static final boolean BENCHMARKING_ENABLED = true;
	
	public static AbstractAnalysis analyze(File file) {
		if(file == null) {
			return null;
		}
		
		/* main analysis loop */
		AbstractAnalysis result = new UnknownAnalysis(null);
		for (AnalysisType type : AnalysisType.values()) { 
			if(BENCHMARKING_ENABLED) {
				Util.startBenchmarkRun();
			}
			
		    if(!result.getClass().getName().equals(UnknownAnalysis.class.getName())) {
		    	if(BENCHMARKING_ENABLED) {
					//long seconds = Util.getBenchmarkResultInSeconds();
					//System.out.println("BENCHMARK TEST(ANALYSIS_TYPE_UNKNOWN) IN SECONDS = " + seconds + "s, file=" + file);
				}
		    	
		    	/* analysis came to an end - time to stop */
		    	break;
		    }
			
			boolean isOfType = AbstractAnalysis.isType(file, type);
		    if(!isOfType) {
		    	continue;
		    }
		    
		    switch(type) {
				case ANALYSIS_TYPE_APK: {
					result = new APKAnalysis(new APKAnalysis(null)).analyze(file, null);
					
					if(BENCHMARKING_ENABLED) {
						long seconds = Util.getBenchmarkResultInSeconds();
						System.out.println("BENCHMARK TEST(ANALYSIS_TYPE_APK) IN SECONDS = " + seconds + "s, file=" + file);
					}
					
					break;
				} 
				case ANALYSIS_TYPE_DIRECTORY: {
					result = new DirectoryAnalysis(new DirectoryAnalysis(null)).analyze(file, null);
					
					if(BENCHMARKING_ENABLED) {
						long seconds = Util.getBenchmarkResultInSeconds();
						System.out.println("BENCHMARK TEST(ANALYSIS_TYPE_DIRECTORY) IN SECONDS = " + seconds + "s, file=" + file);
					}
					
					break;
				} default: {
					if(BENCHMARKING_ENABLED) {
						//long seconds = Util.getBenchmarkResultInSeconds();
						//System.out.println("BENCHMARK TEST(DEFAULT) IN SECONDS = " + seconds + "s, file=" + file);
					}
					
					break;
				}
			}
		}
		
		return result;
	}
}
