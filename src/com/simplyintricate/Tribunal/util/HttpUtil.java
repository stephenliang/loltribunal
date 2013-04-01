package com.simplyintricate.Tribunal.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class HttpUtil {
    public static String convertInputStreamToString(InputStream stream) throws IOException {
        StringWriter writer = new StringWriter();

        IOUtils.copy(stream, writer);
        return writer.toString();
    }
}
