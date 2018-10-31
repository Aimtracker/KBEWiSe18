package de.htw.ai.kbe.runmerunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class LogWriter {
    public static void writeLog(String filename, String text){
        try (PrintStream out = new PrintStream(new FileOutputStream(filename, true))) {
            out.println(text);
        } catch (FileNotFoundException e) {
            System.err.println("An error occured while writing the log file!");
            e.printStackTrace();
        }
    }
}
