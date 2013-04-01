package com.simplyintricate.Tribunal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tribunaloverview);

        application = (App) getApplication();

        CookieStore cookieStore = application.getCookieStore();

        for ( Cookie cookie : cookieStore.getCookies() )
        {
            Log.i(TAG, "Cookie: " + cookie.getName() + " = " + cookie.getValue());
        }
    }
}
