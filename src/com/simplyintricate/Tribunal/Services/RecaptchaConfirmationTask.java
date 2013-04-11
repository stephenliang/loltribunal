package com.simplyintricate.Tribunal.Services;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import com.simplyintricate.Tribunal.App;
import com.simplyintricate.Tribunal.dialogs.BasicMessageAlertDialog;
import com.simplyintricate.Tribunal.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.ArrayList;
import java.util.List;

public class RecaptchaConfirmationTask extends AsyncTask<String, Void, String> {
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/noscript?k=";
    private static final String LOL_API_KEY = "6Ldhvd0SAAAAAHwXC1e_b3N_RuA7WmusxCqFnFyu";
    private static final String TAG = "TribunalConfirmationTask";
    private HttpClient httpClient;
    private Context context;

    public RecaptchaConfirmationTask(Context context)
    {
        this.context = context;
        httpClient = AndroidHttpClient.newInstance("");
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

        } catch (Exception e)
        {
            Log.e(TAG, "Caught exception while submitting data to recaptcha!", e);
            BasicMessageAlertDialog.createBasicMessageDialog(context, "An error ocurred while logging into the tribunal. Please try again later.");
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
