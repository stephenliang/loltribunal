package com.simplyintricate.Tribunal.Services;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import com.simplyintricate.Tribunal.model.Tribunal.GameDetail;
import com.simplyintricate.Tribunal.util.HttpUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Created with IntelliJ IDEA.
 * User: Stephen
 * Date: 4/3/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveTribunalGameDetailsTask extends AsyncTask<String, Void, GameDetail> {
    private static final String TAG = "TribunalRetrieveGameDetailsTask";
    private static final String TRIBUNAL_GET_GAME_URL = "http://na.leagueoflegends.com/tribunal/en/get_game";
    private HttpClient httpClient;
    private HttpContext context;

    public RetrieveTribunalGameDetailsTask(CookieStore cookieStore)
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
    protected GameDetail doInBackground(String... strings) {
        return getGameInformationJson(strings[0], Integer.valueOf(strings[1]));
    }

    private GameDetail getGameInformationJson(String gameId, int gameNumber) {
        try {

            String json = HttpUtil.getContentsUsingHttpGet(TRIBUNAL_GET_GAME_URL+"/"+gameId+"/"+gameNumber, context);

            return new GameDetail(new GameDetail.Builder());
        }catch(Exception e)
        {
            Log.e(TAG, "Caught exception while getting game info", e);
            return null;
        }
    }
}
