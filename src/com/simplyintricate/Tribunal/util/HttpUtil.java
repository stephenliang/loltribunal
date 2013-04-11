package com.simplyintricate.Tribunal.util;

import android.net.http.AndroidHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class HttpUtil {
    public static String convertInputStreamToString(InputStream stream) throws IOException {
        StringWriter writer = new StringWriter();

        IOUtils.copy(stream, writer);
        return writer.toString();
    }

    public static String getContentsUsingHttpGet(String url, HttpContext context) throws IOException {
        HttpResponse response;
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

        httpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS,
                true);
        httpClient.getParams().setBooleanParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
                true);

        HttpGet httpGet = new HttpGet(url);

        if ( context == null) {
            response = httpClient.execute(httpGet);
        } else {
            response = httpClient.execute(httpGet, context);
        }

        HttpEntity responseEntity = response.getEntity();
        String content = convertInputStreamToString(responseEntity.getContent());

        httpClient.close();
        return content;
    }

    public static String getContentsUsingHttpGet(String url) throws IOException {
        return getContentsUsingHttpGet(url, null);
    }
}
