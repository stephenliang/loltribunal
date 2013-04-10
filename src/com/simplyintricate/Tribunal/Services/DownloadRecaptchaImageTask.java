package com.simplyintricate.Tribunal.Services;

import android.net.http.AndroidHttpClient;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadRecaptchaImageTask extends AsyncTask<String, Void, Captcha> {
    private static final String TAG = "DownloadRecaptchaImageTask";
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/noscript?k=";
    private static final String RECAPTCHA_BASE_IMG_URL = "https://www.google.com/recaptcha/api/";
    private static final String LOL_API_KEY = "6Ldhvd0SAAAAAHwXC1e_b3N_RuA7WmusxCqFnFyu";

    @Override
    protected Captcha doInBackground(String... strings) {
        try {
            String content = HttpUtil.getContentsUsingHttpGet(RECAPTCHA_URL + LOL_API_KEY);

            Document doc = Jsoup.parse(content);

            String imageUrl = RECAPTCHA_BASE_IMG_URL + doc.getElementsByTag("img").get(0).attr("src");
            String recaptchaChallenge = doc.getElementById("recaptcha_challenge_field").attr("value");

            if (imageUrl != null && recaptchaChallenge != null)
            {
                Log.i(TAG, String.format("Challenge: %s, img: %s", recaptchaChallenge, imageUrl));
                return new Captcha(recaptchaChallenge, imageUrl);
            } else {
                Log.e(TAG, "Failed to retrieve the recaptcha image or recaptcha challenge!");
            }
        } catch (Exception e) {
            Log.e(TAG, "Caught an exception while getting the Recaptcha Image!", e);
        }

        return null;
    }
}
