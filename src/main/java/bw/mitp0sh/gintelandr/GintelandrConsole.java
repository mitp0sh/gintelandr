package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.flogger.FluentLogger;

public class GintelandrConsole {
	
	private static final FluentLogger flog = FluentLogger.forEnclosingClass();
	protected static GintelandrCommandLine cml = null;

	public static void main(String[] args) {
		GintelandrCommandLineParser commandLineParser = new GintelandrCommandLineParser();
		try {
			cml = commandLineParser.parse(args);
		} catch (org.apache.commons.cli.ParseException e) {			
			flog.atSevere().log("unable to parse specified commandline");
			GintelandrCommandLine.printHelp(null);
			return;
		}
		
		if(cml.isVersion()) {
			System.out.println(cml.getVersionInformation());
			return;
		}
		
		if(cml.isHelp()) { // if help parameter is found, just show help
			GintelandrCommandLine.printHelp(null);
			return;
		}
		
		if(!cml.isInput()) { // at least one input needs to be specified
			GintelandrCommandLine.printHelp(null);
			return;
		}
		
		/* this is the main entry point into analysis - let the game begin */
		ArrayList<AbstractAnalysis> analysisList = new ArrayList<>();
		Iterator<File> iter = cml.inputListAsFiles().iterator();
		while(iter.hasNext()) {
			File current = iter.next();
			AbstractAnalysis currentAnalysis = InputProcessor.analyze(current);
			if(!currentAnalysis.getClass().getName().equals(UnknownAnalysis.class.getName())) {
				analysisList.add(currentAnalysis);
			}
		}
	}
}
