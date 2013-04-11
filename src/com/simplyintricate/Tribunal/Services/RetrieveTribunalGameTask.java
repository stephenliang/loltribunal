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

public class RetrieveTribunalGameTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "InitializeTribunalTask";
    private static final String TRIBUNAL_ACCEPT_TERMS_URL = "http://na.leagueoflegends.com/tribunal/en/accept/";
    private HttpClient httpClient;
    private HttpContext context;

    public RetrieveTribunalGameTask(CookieStore cookieStore)
    {
        httpClient = AndroidHttpClient.newInstance("");

        context = new BasicHttpContext();

        context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    @Override
    protected String doInBackground(String... strings) {
        return retrieveGameInformation();
    }

    public String retrieveGameInformation()
    {
        try
        {
            String result = HttpUtil.getContentsUsingHttpGet(TRIBUNAL_ACCEPT_TERMS_URL, context);
            Document doc = Jsoup.parse(result);
            String gameId = doc.getElementsByClass("raw-case-number").get(0).text();

            Log.i(TAG, "Found game id: " + gameId);
            return gameId;
        }catch(Exception e)
        {
            Log.i(TAG, "Failed to retrieve tribunal game data!", e);
        }

        return null;
    }
}
