package de.htw.ai.kbe.runmerunner;

import org.apache.commons.cli.*;

public class EasyArguments {

    private String className = null;
    private String reportFile = null;

    public EasyArguments(String[] args){
        parseOptions(args);
    }

    public Options initOptions(){
        final Options options = new Options();

        final Option classNameOpt = new Option("c", true, "Class name");
        classNameOpt.setRequired(true);
        options.addOption(classNameOpt);

        final Option reportOpt = new Option("o", true, "File name");
        reportOpt.setRequired(false);
        options.addOption(reportOpt);

        return options;
    }


    public void parseOptions(String[] args){
        final Options options = initOptions();

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd;


        try {
            cmd = parser.parse(options, args);

            //Get option values, if not given they are null
            this.className = cmd.getOptionValue("c");
            this.reportFile = cmd.getOptionValue("o");

        } catch (ParseException e) {
            //if classname is not given, write report into given report file
            System.err.println("An error occured while parsing the parameters!");
            LogWriter.writeLog("report.txt", "An error occured while parsing the parameters! Check your input!");
        }

    }

    public String getClassName() {
        //if className is null, throw an exception and write a message into the report.txt file
        if(className == null){
            LogWriter.writeLog("report.txt", "No Class Name detected! Check your input!");
            throw new NoClassParameterDefinedException("No Class Name detected! Check your input!");
        }
        return className;
    }

    public String getReportFile() {
        if(reportFile == null){
            reportFile = "report.txt";
            System.out.println("No report file detected, using default 'report.txt'!");
        }
        return reportFile;
    }
}
