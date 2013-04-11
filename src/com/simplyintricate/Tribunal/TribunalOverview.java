package com.simplyintricate.Tribunal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.simplyintricate.Tribunal.Services.InitializeTribunalTask;
import com.simplyintricate.Tribunal.Services.RetrieveTribunalGameDetailsTask;
import com.simplyintricate.Tribunal.Services.RetrieveTribunalGameTask;
import com.simplyintricate.Tribunal.model.Tribunal.GameDetail;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;

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
    private ProgressDialog progressDialog;

    public TribunalOverview()
    {

        context = new BasicHttpContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        application = (App) getApplication();
        cookieStore = application.getCookieStore();

        super.onCreate(savedInstanceState);
        progressDialog = ProgressDialog.show(this, "Tribunal", "Initializing tribunal");

        try
        {
            //Initialize the cookies
            InitializeTribunalTask initializeTribunalTask = new InitializeTribunalTask(application.getCookieStore());
            AsyncTask<String, Void, Void> tribunalTaskResult = initializeTribunalTask.execute();
            tribunalTaskResult.get();

            progressDialog.setMessage("Retrieving game id");
            //Grab the Tribunal main page and return to me the game id
            RetrieveTribunalGameTask retrieveTribunalGameTask = new RetrieveTribunalGameTask(application.getCookieStore());
            AsyncTask<String, Void, String> tribunalGameResult = retrieveTribunalGameTask.execute();
            String gameId = tribunalGameResult.get();

            progressDialog.setMessage("Retrieving game information");
            //Go out to retrieve the five different games simultaneously
            ArrayList<AsyncTask<String, Void, GameDetail>> gameDetailTasks = new ArrayList<AsyncTask<String, Void, GameDetail>>(5);

            for ( int i = 0; i < 5; i++ )
            {
                RetrieveTribunalGameDetailsTask retrieveTribunalGameDetailsTask = new RetrieveTribunalGameDetailsTask(cookieStore);

                gameDetailTasks.add( retrieveTribunalGameDetailsTask.execute(gameId, Integer.toString(i)) );
            }

            //Wait until we get all the game details before proceeding
            ArrayList<GameDetail> gameDetails = new ArrayList<GameDetail>(5);
            for ( AsyncTask<String, Void, GameDetail> currentTask: gameDetailTasks )
            {
                gameDetails.add(currentTask.get());
            }
        }catch(Exception e)
        {
            Log.e(TAG, "Caught an exception while initializing the tribunal game details!", e);
        }


        setContentView(R.layout.tribunaloverview);
    }

}
