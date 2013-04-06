package com.simplyintricate.Tribunal.Services;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import com.simplyintricate.Tribunal.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PvpLoginService extends AsyncTask<String, Void, Void> {
    private static final String TAG = "TribunalLoginService";
    private static final String PVP_LOGIN_URL = "https://na.leagueoflegends.com/user/login";
    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext context;

    public PvpLoginService(CookieStore cookieStore)
    {
        httpClient = new DefaultHttpClient();
        this.cookieStore = cookieStore;
        context = new BasicHttpContext();
        context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    @Override
    protected Void doInBackground(String... strings) {
        login(strings[0], strings[1], strings[2]);
        return null;
    }

    public void login(String username, String password, String recaptchaChallenge)
    {
        Log.d(TAG, "Attempting to log in to pvp.net");
        try
        {
            populateCookies();
            HttpPost httpPost = new HttpPost(PVP_LOGIN_URL);

            List<NameValuePair> loginDetails = createLoginInformationPostValues(username, password, recaptchaChallenge);
            httpPost.setEntity(new UrlEncodedFormEntity(loginDetails));

            HttpResponse response = httpClient.execute(httpPost, context);
            HttpEntity responseEntity = response.getEntity();

            String result = HttpUtil.convertInputStreamToString(responseEntity.getContent());

            Log.i(TAG, "Login service Response: " + result);
            CookieStore localCookieStore = (CookieStore) context.getAttribute(ClientContext.COOKIE_STORE);

            List<Cookie> cookies = localCookieStore.getCookies();

            for ( Cookie cookie : cookies )
            {
                Log.i(TAG, "Added cookie: " + cookie);
            }

            Log.i(TAG, "Cookies: " + context.getAttribute(ClientContext.COOKIE_STORE));

            //save cookie
        }catch(Exception e)
        {
            Log.e(TAG, "Failed to login to PVP.net!", e);
        }
    }


    private void populateCookies()
    {
        BasicClientCookie testCookie = new BasicClientCookie("testcookie", "expanded");
        testCookie.setDomain("na.leagueoflegends.com");
        testCookie.setPath("/");
       cookieStore.addCookie(testCookie);
    }

    private List<NameValuePair> createLoginInformationPostValues(String username, String password, String recaptchaChallenge)
    {
        List<NameValuePair> loginInformation = new ArrayList<NameValuePair>();

        loginInformation.add(new BasicNameValuePair("name", username));
        loginInformation.add(new BasicNameValuePair("pass", password));
        loginInformation.add(new BasicNameValuePair("recaptcha_challenge_field", recaptchaChallenge));
        loginInformation.add(new BasicNameValuePair("recaptcha_response_field", "manual_challenge"));
        loginInformation.add(new BasicNameValuePair("form_id", "user_login"));

        return loginInformation;
    }
}
