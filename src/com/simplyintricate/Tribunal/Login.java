package com.simplyintricate.Tribunal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.simplyintricate.Tribunal.Services.DownloadImageTask;
import com.simplyintricate.Tribunal.Services.DownloadRecaptchaImageTask;
import com.simplyintricate.Tribunal.Services.PvpLoginService;
import com.simplyintricate.Tribunal.Services.RecaptchaConfirmationTask;
import com.simplyintricate.Tribunal.dialogs.BasicMessageAlertDialog;
import com.simplyintricate.Tribunal.model.Captcha;

public class Login extends Activity {
    private static final String TAG = "TribunalLogin";
    private App application;
    private Captcha captcha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        application = (App) getApplication();
        getRecaptchaDetails();
    }

    private void getRecaptchaDetails() {
        DownloadRecaptchaImageTask downloadRecaptchaImageTask = new DownloadRecaptchaImageTask();
        AsyncTask<String, Void, Captcha> result = downloadRecaptchaImageTask.execute();

        populateRecaptchaImageView(result);
    }

    private void populateRecaptchaImageView(AsyncTask<String, Void, Captcha> result) {
        try {
            captcha = result.get();
            ImageView recaptchaImageView = (ImageView) findViewById(R.id.recaptcha);

            new DownloadImageTask(recaptchaImageView).execute(captcha.getRecaptchaImageUrl());
            hideRecaptchaLoadingText();
        } catch (Exception e) {
            BasicMessageAlertDialog.createBasicMessageDialog(this, getString(R.string.error_recaptcha_download));
        }
    }

    private void hideRecaptchaLoadingText() {
        TextView recaptchaLoadingText = (TextView) findViewById(R.id.recaptchaLoading);

        recaptchaLoadingText.setVisibility(View.GONE);
    }

    public void handleLogin(View view)
    {
        Log.i(TAG, "Clicked login");

        ProgressDialog.show(this, "Login", "Logging into the Tribunal");

        PvpLoginService loginService = new PvpLoginService(application.getCookieStore());
        RecaptchaConfirmationTask recaptchaConfirmationTask = new RecaptchaConfirmationTask(application);

        EditText recaptchaResponseWidget = (EditText) findViewById(R.id.recaptchaInput);
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);

        String recaptchaResponse = recaptchaResponseWidget.getText().toString();
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        AsyncTask<String, Void, String> result = recaptchaConfirmationTask.execute(recaptchaResponse, captcha.getRecaptchaChallenge());

        try {
            String recaptchaChallenge = result.get();

            //loginService.execute(username, password, recaptchaChallenge).get();
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred while trying to log in!", e);
            BasicMessageAlertDialog.createBasicMessageDialog(this, getString(R.string.error_login));
        }

        Intent intent = new Intent(this, TribunalOverview.class);
        startActivity(intent);
    }
}
