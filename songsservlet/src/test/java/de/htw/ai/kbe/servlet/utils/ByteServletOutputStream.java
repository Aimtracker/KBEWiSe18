package de.htw.ai.kbe.servlet.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ByteServletOutputStream extends ServletOutputStream {

    private ByteArrayOutputStream bos;
    
    public ByteServletOutputStream() {
        bos = new ByteArrayOutputStream();
    }
    
    @Override
    public boolean isReady() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void write(int b) throws IOException {
        bos.write(b);
    }

    public ByteArrayOutputStream getByteStream() {
        return bos;
    }
}
