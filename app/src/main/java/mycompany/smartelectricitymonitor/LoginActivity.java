package mycompany.smartelectricitymonitor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin , btnUserRegistration;
    Intent intent;
    EditText txtPremisesNo,txtPassword;

    boolean isValidUser = false,loginIsClicked;

    SweetAlertDialog pDialog;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }

        // Initialize
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnUserRegistration  = (Button)findViewById(R.id.btnUserRegistration);

        txtPremisesNo = (EditText)findViewById(R.id.txtPremisesNo);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Signing in..");
        pDialog.setCancelable(false);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtPremisesNo.getText().toString().equals("")||txtPassword.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter the Credentials",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(isNetworkAvailable()) {

                        pDialog.show();

                        WebAPITask webAPITask = new WebAPITask();
                        webAPITask.execute();
                    }
                    else
                    {
                        pDialog.cancel();
                        Toast.makeText(getApplicationContext(),"Make sure your internet connection is established !",Toast.LENGTH_SHORT).show();

                    }

                }

//                intent = new Intent(LoginActivity.this, DomesticUserActivity.class);
//                startActivity(intent);

            }
        });


        btnUserRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent = new Intent(LoginActivity.this, UserRegistrationActivity.class);
                startActivity(intent);


            }
        });

    }

    private void getUser()
    {


       //String getUserUrl = "https://ereaderv10.azurewebsites.net/api/Users/P-101/123";
      String getUserUrl = "https://ereaderv10.azurewebsites.net/api/Users/"+txtPremisesNo.getText().toString()+"/"+txtPassword.getText().toString();

        try {

             JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUserUrl,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        try
                        {
                           if(response.getString("login").equals("true"))
                           {

                               if(response.getString("User_Type").equals("Domestic")) {
                                   intent = new Intent(LoginActivity.this, DomesticUserActivity.class);
                                 //  intent.putExtra("userName",txtPremisesNo.getText().toString().trim());
                                   intent.putExtra("premisesNo",txtPremisesNo.getText().toString());
                                   intent.putExtra("isDomestic",true);
                                   pDialog.cancel();
                                   startActivity(intent);


                               }
                               else
                                   {
                                       intent = new Intent(LoginActivity.this, IndustrialUserActivity.class);
                                       pDialog.cancel();
                                       startActivity(intent);
                                   }

                           }

                        }
                        catch (JSONException ex)
                        {
                            pDialog.cancel();
                            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                        }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Please Check the Credentials",Toast.LENGTH_SHORT).show();
                    System.out.println("Error -->>"+error.toString());
                    isValidUser = false;

                }
            });

            request.setRetryPolicy(new

                    DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            Volley.newRequestQueue(getApplicationContext()).add(request);

        }
        catch (Exception ex)
        {
            pDialog.cancel();
            System.out.print("Exception"+ex.toString());

        }

    }


    private class WebAPITask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                getUser();
            }
            catch (Exception ex)
            {

                Toast.makeText(getApplicationContext(),"Please turn on the Mobile Data",Toast.LENGTH_SHORT).show();
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            Toast.makeText(getApplicationContext(),"Signing in..",Toast.LENGTH_SHORT).show();

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
