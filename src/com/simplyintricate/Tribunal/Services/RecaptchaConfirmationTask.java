package com.simplyintricate.Tribunal.Services;

import android.os.AsyncTask;
import android.util.Log;
import com.simplyintricate.Tribunal.model.Captcha;
import com.simplyintricate.Tribunal.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Stephen
 * Date: 3/21/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecaptchaConfirmationTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "TribunalRecaptchaConfirmationTask";
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/noscript?k=";
    private static final String RECAPTCHA_BASE_IMG_URL = "https://www.google.com/recaptcha/api/";
    private static final String LOL_API_KEY = "6Ldhvd0SAAAAAHwXC1e_b3N_RuA7WmusxCqFnFyu";
    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext context;

    public RecaptchaConfirmationTask()
    {
        httpClient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        context = new BasicHttpContext();
    }

    @Override
    protected String doInBackground(String... strings) {
        String recaptchaResponse = strings[0];
        String recaptchaChallenge = strings[1];

        try {
            HttpPost httpPost = new HttpPost(RECAPTCHA_URL+LOL_API_KEY);

            List<NameValuePair> loginDetails = createPostHeader(recaptchaResponse, recaptchaChallenge);
            httpPost.setEntity(new UrlEncodedFormEntity(loginDetails));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            String content = HttpUtil.convertInputStreamToString(responseEntity.getContent());

            Document doc = Jsoup.parse(content);

            return doc.select("textarea").val();

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private List<NameValuePair> createPostHeader(String recaptchaResponse, String recaptchaChallenge)
    {
        List<NameValuePair> loginInformation = new ArrayList<NameValuePair>();

        loginInformation.add(new BasicNameValuePair("recaptcha_challenge_field", recaptchaChallenge));
        loginInformation.add(new BasicNameValuePair("recaptcha_response_field", recaptchaResponse));
        loginInformation.add(new BasicNameValuePair("submit", "I%27m+a+human"));

        return loginInformation;
    }
}
