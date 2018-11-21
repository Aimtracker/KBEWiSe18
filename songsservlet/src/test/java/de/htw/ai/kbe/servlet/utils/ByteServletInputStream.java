package de.htw.ai.kbe.servlet.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;

public class ByteServletInputStream extends ServletInputStream {

    private ByteArrayInputStream bis;

    public ByteServletInputStream(byte[] data) {
        bis = new ByteArrayInputStream(data);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }

    @Override
    public int read() {
        return bis.read();
    }

    public ByteArrayInputStream getBis() {
        return bis;
    }
}
