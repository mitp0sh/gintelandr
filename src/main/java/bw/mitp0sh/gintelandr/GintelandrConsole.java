package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.flogger.FluentLogger;

import bw.mitp0sh.gintelandr.GintelandrCommandLineParser.GintelandrRunMode;

public class GintelandrConsole {
	
	private static final FluentLogger flog = FluentLogger.forEnclosingClass();
	protected static GintelandrCommandLine cml = null;

	public static void main(String[] args) {
		flog.atInfo().log("Gintelandr is warming-up! Please wait...");
		
		/* register shutdown hook for required clean-up */
		Runtime.getRuntime().addShutdownHook(new GintelandrShutdownHookThread());
		
		/* start commandline parsing */
		GintelandrCommandLineParser commandLineParser = new GintelandrCommandLineParser();
		try {
			cml = commandLineParser.parse(args);
		} catch (org.apache.commons.cli.ParseException e) {			
			e.printStackTrace();
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
		
		if(!cml.isMode()) { // mode is optional and if not specified default to INTERACTIVE
			cml.getModes().put(GintelandrRunMode.INTERACTIVE.toString(), GintelandrRunMode.INTERACTIVE);
		}
		
		if(cml.getModes().get(GintelandrRunMode.INTERACTIVE.toString()) != null) {
			DatabaseHelper.initDB(true, null);
		} else {
			// TODO - MAYBE SOME CASE HANDLING WILL BE NEEDED HERE !!!			
			DatabaseHelper.initDB(false, /* TODO - WE NEED TO SET THE DB PATH HERE !!! */ null);
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
	
	static class GintelandrShutdownHookThread extends Thread implements Runnable {
		@Override
		public void run() {
			flog.atInfo().log("shutdown hook entered. Starting clean-up now...");
			DatabaseHelper.finalizeDB();
		}		
	}
}
