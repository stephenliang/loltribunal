package com.simplyintricate.Tribunal;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.simplyintricate.Tribunal.Services.DownloadImageTask;
import com.simplyintricate.Tribunal.Services.DownloadRecaptchaImageTask;
import com.simplyintricate.Tribunal.Services.PvpLoginService;
import com.simplyintricate.Tribunal.Services.RecaptchaConfirmationTask;
import com.simplyintricate.Tribunal.model.Captcha;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.concurrent.ExecutionException;

public class Login extends Activity {
    private static final String TAG = "TribunalLogin";
    private DownloadRecaptchaImageTask downloadRecaptchaImageTask;
    private Captcha captcha;
    private App application;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        application = (App) getApplication();
        downloadRecaptchaImageTask = new DownloadRecaptchaImageTask();
        AsyncTask<String, Void, Captcha> result = downloadRecaptchaImageTask.execute("asdf");

        try {
            captcha = result.get();

            new DownloadImageTask((ImageView) findViewById(R.id.recaptcha)).execute(captcha.getRecaptchaImageUrl());

        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void handleLogin(View view)
    {
        Log.i(TAG, "Clicked login");
        Intent intent = new Intent(this, TribunalOverview.class);

        PvpLoginService loginService = new PvpLoginService(application.getCookieStore());
        RecaptchaConfirmationTask recaptchaConfirmationTask = new RecaptchaConfirmationTask();

        EditText recaptchaResponseWidget = (EditText) findViewById(R.id.recaptchaInput);
        String recaptchaResponse = recaptchaResponseWidget.getText().toString();

        AsyncTask<String, Void, String> result = recaptchaConfirmationTask.execute(recaptchaResponse, captcha.getRecaptchaChallenge());
        try {
            String recaptchaChallenge = result.get();
            loginService.execute("angrywalls", "CQGAC9036xGUc56Zeb5Q", recaptchaChallenge).get();
        } catch (Exception e) {
            Log.e(TAG, "Exception occured!", e);
        }

        startActivity(intent);
    }
}
