package de.htw.ai.kbe.servlet.marshalling;

import java.io.IOException;

public class MarshallingException extends IOException {

    private static final long serialVersionUID = 1L;

    public MarshallingException(String message) {
        super(message);
    }
}
