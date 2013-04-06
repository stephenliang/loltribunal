package com.simplyintricate.Tribunal.Services;

import android.os.AsyncTask;
import android.util.Log;
import com.simplyintricate.Tribunal.model.Captcha;
import com.simplyintricate.Tribunal.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Stephen
 * Date: 3/21/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadRecaptchaImageTask extends AsyncTask<String, Void, Captcha> {
    private static final String TAG = "DownloadRecaptchaImageTask";
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/noscript?k=";
    private static final String RECAPTCHA_BASE_IMG_URL = "https://www.google.com/recaptcha/api/";
    private static final String LOL_API_KEY = "6Ldhvd0SAAAAAHwXC1e_b3N_RuA7WmusxCqFnFyu";
    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext context;

    public DownloadRecaptchaImageTask()
    {
        httpClient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        context = new BasicHttpContext();
    }

    @Override
    protected Captcha doInBackground(String... strings) {
        try {
            HttpGet httpGet = new HttpGet(RECAPTCHA_URL+LOL_API_KEY);

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();

            String content = HttpUtil.convertInputStreamToString(responseEntity.getContent());

            Pattern imgReg = Pattern.compile("(<img .*src\\=\")([^\"]*)");
            Pattern chalReg = Pattern.compile("(id=\"recaptcha_challenge_field\" value=\")([^\"]*)");



            // test for regex match
            Matcher challengeMatch = chalReg.matcher(content);
            Matcher imageMatch = imgReg.matcher(content);
            boolean bChal = challengeMatch.find();
            boolean bImg = imageMatch.find();

            // make sure we got regex matches
            if (bChal && bImg)
            {
                // get challenge
                String challenge = challengeMatch.group(2);

                // get image url
                String imageUrl = RECAPTCHA_BASE_IMG_URL+imageMatch.group(2);

                Log.i(TAG, String.format("Challenge: %s, img: %s", challenge, imageUrl));
                return new Captcha(challenge, imageUrl);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
