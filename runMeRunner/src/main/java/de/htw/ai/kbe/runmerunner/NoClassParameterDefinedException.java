package de.htw.ai.kbe.runmerunner;

public class NoClassParameterDefinedException extends RuntimeException {
    public NoClassParameterDefinedException(){
        super();
    }
    public NoClassParameterDefinedException(String message){
        super(message);
    }
}
