package com.simplyintricate.Tribunal.Services;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import com.simplyintricate.Tribunal.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created with IntelliJ IDEA.
 * User: Stephen
 * Date: 4/3/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitializeTribunalTask extends AsyncTask<String, Void, Void> {
    private static final String TAG = "InitializeTribunalTask";
    private static final String TRIBUNAL_ACCEPT_TERMS_URL = "http://na.leagueoflegends.com/tribunal/en/accept/";
    private static final String TRIBUNAL_GET_GAME_URL = "http://na.leagueoflegends.com/tribunal/en/get_game";
    private HttpClient httpClient;
    private HttpContext context;

    public InitializeTribunalTask(CookieStore cookieStore)
    {
        httpClient = AndroidHttpClient.newInstance("");
        httpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS,
                true);
        httpClient.getParams().setBooleanParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
                true);
        context = new BasicHttpContext();

        context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    @Override
    protected Void doInBackground(String... strings) {
        acceptTribunalTerms();
        return null;
    }

    public void acceptTribunalTerms()
    {
        try
        {
            HttpGet httpGet = new HttpGet(TRIBUNAL_ACCEPT_TERMS_URL);

            httpClient.execute(httpGet, context);
        }catch(Exception e)
        {
            Log.e(TAG, "Failed to accept tribunal terms!", e);
        }
    }
}
