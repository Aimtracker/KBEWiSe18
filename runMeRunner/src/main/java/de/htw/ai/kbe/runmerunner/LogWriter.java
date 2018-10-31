package de.htw.ai.kbe.runmerunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class LogWriter {
    public static void writeLog(String filename, String text){
        try (PrintStream out = new PrintStream(new FileOutputStream(filename, true))) {
            //Write current Date into report File have a better overview
            out.println(new Date());
            out.println(text);
        } catch (FileNotFoundException e) {
            System.err.println("An error occured while writing the log file! File was not found!");
            e.printStackTrace();
        }
    }
}
