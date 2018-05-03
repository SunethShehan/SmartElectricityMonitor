package mycompany.smartelectricitymonitor;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by suneth on 5/1/18.
 */

public class WebAPITask extends AsyncTask<String,Void,JSONObject>
{
    // GET
    // POST

    private String method;
    private String url;



    @Override
    protected JSONObject doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
