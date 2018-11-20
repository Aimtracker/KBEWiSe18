package de.htw.ai.kbe.servlet.marshalling;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.htw.ai.kbe.servlet.marshalling.IMarshaller;

public final class MarshallerFactory {

    private static MarshallerFactory instance = null;

    private Map<String, IMarshaller> marshallers;

    private MarshallerFactory() {
        marshallers = Collections.synchronizedMap(new HashMap<>());
    }

    public static MarshallerFactory getInstance() {
        if (instance == null) {
            instance = new MarshallerFactory();
        }
        return instance;
    }

    public synchronized IMarshaller getMarshaller(String contentType) throws IllegalArgumentException {
        IMarshaller m = marshallers.get(contentType);

        if (m == null) {
            m = initMarshaller(contentType);
            marshallers.put(contentType, m);
        }
        return m;
    }

    private IMarshaller initMarshaller(String contentType) throws IllegalArgumentException {
        switch (contentType) {
            case "application/json":
                return new JsonHandler();
            default:
                throw new IllegalArgumentException("ContentType '" + contentType + "' is not supported!");
        }
    }
}
