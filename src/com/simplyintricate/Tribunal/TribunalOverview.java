package com.simplyintricate.Tribunal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.simplyintricate.Tribunal.Services.TribunalService;
import com.simplyintricate.Tribunal.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Created with IntelliJ IDEA.
 * User: Stephen
 * Date: 2/19/13
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TribunalOverview extends Activity {
    private App application;
    private static final String TAG = "TribunalOverview";
    private CookieStore cookieStore;
    private HttpClient httpClient;
    private HttpContext context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tribunaloverview);

        application = (App) getApplication();

        cookieStore = application.getCookieStore();
        context = new BasicHttpContext();

        TribunalService tribunalService = new TribunalService(cookieStore);

        tribunalService.execute();

    }

}
