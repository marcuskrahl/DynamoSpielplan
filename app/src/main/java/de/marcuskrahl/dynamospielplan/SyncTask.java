package de.marcuskrahl.dynamospielplan;

import android.content.Context;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class SyncTask extends AsyncTask<Void,Void,Void> {

    private final Context context;
    private final URL url;
    private final SyncEventListener eventListener;

    private Exception syncException = null;

    public SyncTask(Context context) {
        this(context,null);
    }

    public SyncTask(Context context,SyncEventListener eventListener) {
        this.context = context;
        URL siteURL = null;
        try {
            siteURL = new URL("http://www.dynamo-dresden.de/saison/spielplan/2015-2016.html");
        } catch (MalformedURLException ex) {
            //The catch block is never called because the URL is fixed and well formed
        }
        this.url = siteURL;
        this.eventListener = eventListener;
    }

    @Override
    protected Void doInBackground(Void... params2) {
        CalendarAdapter adapter = new CalendarAdapterImplementation(context);
        SyncRun run = new SyncRun(new HtmlMatchPlanRetriever(new DynamoMatchPlanURL(this.url)), new CalendarSync(adapter), adapter);
        try {
            run.run();
        } catch (Exception ex) {
            Log.e("sync run", ex.getMessage());
            syncException = ex;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if ((syncException != null) && (eventListener != null)) {
            eventListener.onSyncError(syncException.getMessage());
        }
    }

    public interface SyncEventListener {
        void onSyncError(String errorMessage);
    }
}
