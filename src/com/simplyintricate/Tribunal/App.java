package com.simplyintricate.Tribunal;

import android.app.Application;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class App extends Application {
    private HttpClient httpClient;
    CookieStore cookieStore = new BasicCookieStore();

    public void onCreate() {
        super.onCreate();

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

// Create and initialize scheme registry
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(params, schemeRegistry);

        httpClient = new DefaultHttpClient(threadSafeClientConnManager, params);
    }

    public HttpClient getHttpClient()
    {
        return httpClient;
    }

    public CookieStore getCookieStore()
    {
        return cookieStore;
    }
}
