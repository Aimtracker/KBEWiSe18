package de.htw.ai.kbe.runmerunner;

import org.apache.commons.cli.*;;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class RunMe {
    public static void main(String[] args) throws Exception {
        Options options = new Options();

        Option classNameOpt = new Option("c", true, "Class name");
        classNameOpt.setRequired(true);
        options.addOption(classNameOpt);

        Option reportOpt = new Option("o", true, "File name");
        reportOpt.setRequired(false);
        options.addOption(reportOpt);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        String className = "";
        String report = "";

        try {
            cmd = parser.parse(options, args);

            className = cmd.getOptionValue("c");
            report = cmd.getOptionValue("o");

            System.out.println(className);
            System.out.println(report);
        } catch (ParseException e) {
            System.err.println("An error occured while parsing!");
            writeLog("f.txt", "nicht ok");
        }

    }



    public static void writeLog(String filename, String text){
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(text);
        } catch (FileNotFoundException e) {
            System.err.println("An error occured while writing the log file!");
            e.printStackTrace();
        }

    }
}
