package com.simplyintricate.Tribunal.util;

import android.net.http.AndroidHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class HttpUtil {
    public static String convertInputStreamToString(InputStream stream) throws IOException {
        StringWriter writer = new StringWriter();

        IOUtils.copy(stream, writer);
        return writer.toString();
    }

    public static String getContentsUsingHttpGet(String url) throws IOException {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        String content = convertInputStreamToString(responseEntity.getContent());

        httpClient.close();
        return content;
    }
}
