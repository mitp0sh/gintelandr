package bw.mitp0sh.gintelandr;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GintelandrCommandLineParser {	
	
	protected static final Options OPTIONS = new Options();
	
	protected static final String OPTION_STRING_HELP = "h";
	protected static final String OPTION_STRING_HELP_LONG = "help";
	protected static final String OPTION_STRING_VERBOSE = "v";
	protected static final String OPTION_STRING_VERBOSE_LONG = "verbose";
	protected static final String OPTION_STRING_INPUT = "i";
	protected static final String OPTION_STRING_INPUT_LONG = "input";
	
	static {		
		Option helpOption = new Option(OPTION_STRING_HELP, OPTION_STRING_HELP_LONG, false, "show this help");
		helpOption.setArgs(0);
		Option verboseOption = new Option(OPTION_STRING_VERBOSE, OPTION_STRING_VERBOSE_LONG, false, "enable verbose output");
		verboseOption.setArgs(0);
		Option inputOption = new Option(OPTION_STRING_INPUT, OPTION_STRING_INPUT_LONG, false, "specify input, either file or directories, multiple seperated by ','");
		inputOption.setArgs(1);
		inputOption.setValueSeparator('|');
		
		/* register all available options */
		OPTIONS.addOption(helpOption);
		OPTIONS.addOption(verboseOption);
		OPTIONS.addOption(inputOption);
	}
	
	private CommandLineParser clp = new DefaultParser();
	
	public GintelandrCommandLine parse(String[] arguments) throws ParseException {
		return new GintelandrCommandLine(clp.parse(OPTIONS, arguments));
	}
}

class GintelandrCommandLine{
	
	private static String LAST_ERROR = "";
	
	private CommandLine cml = null;
	private ArrayList<String> inputStrList = new ArrayList<>();	
	private ArrayList<File> inputFileList = new ArrayList<>();

	public GintelandrCommandLine(CommandLine cml) throws ParseException {
		super();
		this.cml = cml;
		
		if(cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_INPUT)) {
			String optValue = cml.getOptionValue(GintelandrCommandLineParser.OPTION_STRING_INPUT);
			String[] values = optValue.split("\\;");
			if(values != null && values.length != 0) {
				for (String v : values) {
					inputStrList.add(v);
				}
			}
			
			if(inputStrList.size() != 0 && inputFileList.size() == 0) {
				Iterator<String> iter = inputStrList.iterator();
				while(iter.hasNext()) {
					try {
						File file = new File(iter.next());
						if(file.isDirectory() || file.isFile()) {
							inputFileList.add(file);
						}
					} catch (Throwable t) {
						continue;
					}
				}
			}
		}
		
		// final step - validate commandLineParameter
		if(!validateCommandLine()) {
			throw new ParseException("Validation error - " + LAST_ERROR);
		}
	}	
	
	public CommandLine getApacheCommonsCLICml() {
		return cml;
	}
	
	public boolean isHelp() {
		return cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_HELP);
	}	

	public boolean isVerbose() {
		return cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_VERBOSE);
	}
	
	public boolean isInput() {
		return cml.hasOption(GintelandrCommandLineParser.OPTION_STRING_INPUT);
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
	
	private boolean validateCommandLine() {		
		if(inputFileList.size() == 0) {
			LAST_ERROR = "specified input is neither file nor directory";
			return false;
		}
		
		return true;
	}
}