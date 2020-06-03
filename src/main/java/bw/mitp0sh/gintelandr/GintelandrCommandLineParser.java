package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.flogger.FluentLogger;

import bw.mitp0sh.gintelandr.GintelandrCommandLineParser.GintelandrRunMode;

public class GintelandrCommandLineParser {
	
	protected static final Options OPTIONS = new Options();
	
	protected static final String OPTION_STRING_HELP = "h";
	protected static final String OPTION_STRING_HELP_LONG = "help";
	protected static final String OPTION_STRING_VERSION = "v";
	protected static final String OPTION_STRING_VERSION_LONG = "version";
	protected static final String OPTION_STRING_INPUT = "i";
	protected static final String OPTION_STRING_INPUT_LONG = "input";	
	protected static final String OPTION_STRING_MODE = "m";
	protected static final String OPTION_STRING_MODE_LONG = "mode";
	
	static {		
		Option helpOption = new Option(OPTION_STRING_HELP, OPTION_STRING_HELP_LONG, false, "show this help");
		helpOption.setArgs(0);
		Option versionOption = new Option(OPTION_STRING_VERSION, OPTION_STRING_VERSION_LONG, false, "show version information");
		versionOption.setArgs(0);
		Option inputOption = new Option(OPTION_STRING_INPUT, OPTION_STRING_INPUT_LONG, false, "specify input, either file or directories, multiple seperated by ','");
		inputOption.setArgs(1);		
		Option modeOption = new Option(OPTION_STRING_MODE, OPTION_STRING_MODE_LONG, true, "gintelandr run mode. Choose between INTERACTIVE, PROJECT or EXPORT");
		modeOption.setArgName("MODE");		
		
		/* register all available options */
		OPTIONS.addOption(helpOption);
		OPTIONS.addOption(versionOption);
		OPTIONS.addOption(inputOption);
		OPTIONS.addOption(modeOption);
	}
	
	private CommandLineParser clp = new DefaultParser();
	
	public GintelandrCommandLine parse(String[] arguments) throws ParseException {
		return new GintelandrCommandLine(clp.parse(OPTIONS, arguments));
	}
	
	public enum GintelandrRunMode {
		UNDEFINED, INTERACTIVE, PROJECT, EXPORT;
		
		public String toString(){
	        switch(this){
        	case INTERACTIVE :
        		return "INTERACTIVE";
        	case PROJECT :
        		return "PROJECT";
        	case EXPORT :
        		return "EXPORT";
			default:
				return "UNDEFINED";
	        }
	    }
		
		public static GintelandrRunMode toRunMode(String runMode) {
			switch(runMode){
    		case "INTERACTIVE" :
    			return INTERACTIVE;
    		case "PROJECT" :
    			return PROJECT;	
    		case "EXPORT" :
    			return EXPORT;
    		default:
    			return UNDEFINED;
			}
		}
		
		public final boolean equals(String mode) {
			return toRunMode(mode).equals(UNDEFINED) ? false : true; 
		}		
	} 
}

class GintelandrCommandLine{
	
	private static final FluentLogger flog = FluentLogger.forEnclosingClass();
	
	private static String LAST_ERROR = "";
	
	private CommandLine cml = null;
	private ArrayList<String> inputStrList = new ArrayList<>();	
	private ArrayList<File> inputFileList = new ArrayList<>();
	private String versionInformation = new Versioning().toString();
	private HashMap<String, GintelandrRunMode> modeList = new HashMap<>();

	public GintelandrCommandLine(CommandLine cml) throws ParseException {
		super();
		this.cml = cml;
		
		if(cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_INPUT)) {
			String optValue = cml.getOptionValue(GintelandrCommandLineParser.OPTION_STRING_INPUT);
			String[] values = optValue.split("\\|");
			if(values != null && values.length != 0) {
				for (String v : values) {
					inputStrList.add(v);
				}
			}
			
			if(inputStrList.size() != 0 && inputFileList.size() == 0) {
				Iterator<String> iter = inputStrList.iterator();
				while(iter.hasNext()) {
					String current = iter.next();
					try {
						File file = new File(current);
						if(file.isDirectory() || file.isFile()) {
							inputFileList.add(file);
						}
					} catch (Throwable t) {
						flog.atWarning().log("invalid input source detected, ignoring...(input=\"" + current + "\")");
						continue;
					}
				}
			}
		}	
		
		if(cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_MODE)) {
			String optValue = cml.getOptionValue(GintelandrCommandLineParser.OPTION_STRING_MODE);
			String[] values = optValue.split("\\|");
			if(values != null && values.length != 0) {
				for (String v : values) {
					GintelandrRunMode runMode = GintelandrRunMode.toRunMode(v);
					if(!runMode.equals(GintelandrRunMode.UNDEFINED)) {
						modeList.put(v, runMode);
					}
				}
			}
		}
		
		// final step - validate commandLineParameter
		if(!validateCommandLine()) {
			String msg = "validation error occured, aborting...(last error=\"" + LAST_ERROR + "\")";
			flog.atSevere().log(msg);
			throw new ParseException(msg);
		}
	}	
	
	public CommandLine getApacheCommonsCLICml() {
		return cml;
	}
	
	public boolean isHelp() {
		return cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_HELP);
	}	

	public boolean isVersion() {
		return cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_VERSION);
	}
	
	public boolean isInput() {
		return cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_INPUT);
	} 
	
	public boolean isMode() {
		return cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_MODE);
	}
	
	public static void printHelp(String message) {
		new HelpFormatter().printHelp("gintelandr", GintelandrCommandLineParser.OPTIONS);
	}
	
	public ArrayList<String> inputListAsStrings() {
		return inputStrList;
	}
	
	public ArrayList<File> inputListAsFiles() {
		return inputFileList;
	}
	
	public boolean hasMode(String mode) {
		return modeList.get(mode) != null ? true : false;
	} 
	
	public HashMap<String, GintelandrRunMode> getModes() {
		return modeList;
	}
	
	public String getVersionInformation() {
		return versionInformation;
	}
	
	private boolean validateCommandLine() {		
		/* defaults which do nothing functional */
		if(isVersion() || isHelp()) {
			return true;
		}
		
		if(inputFileList.size() == 0) {
			LAST_ERROR = "specified input is neither file nor directory";
			return false;
		}
		
		/* check if all passed modes are valid */
		if(modeList.size() != 0) {
			Iterator<Entry<String, GintelandrRunMode>> runModeIter = modeList.entrySet().iterator();
			while(runModeIter.hasNext()) {
				Entry<String, GintelandrRunMode> current = runModeIter.next();
				GintelandrRunMode value = current.getValue();
				if(value.equals(GintelandrRunMode.UNDEFINED)) {
					LAST_ERROR = "GintelandrRunMode.UNDEFINED not allowed";
					return false;
				}
			}
		}
		
		LAST_ERROR = "";
		return true;
	}	
}


