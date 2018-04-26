package mycompany.smartelectricitymonitor;

import android.Manifest;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    Button btnLogin , btnUserRegistration;
    Intent intent;
    EditText txtPremisesNo,txtPassword;

    boolean isValidUser = false,loginIsClicked;


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


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                if(txtPremisesNo.getText().equals("")||txtPassword.getText().equals(""))
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter the Credentials",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//
//                        WebAPITask webAPITask = new WebAPITask();
//                        webAPITask.execute();
//
//                }

                intent = new Intent(LoginActivity.this, DomesticUserActivity.class);
                startActivity(intent);

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


       //String getUSerUrl = "https://ereaderv10.azurewebsites.net/api/Users/P-100/TEST";
        String getUserUrl = "https://ereaderv10.azurewebsites.net/api/Users/"+txtPremisesNo.getText()+"/"+txtPassword.getText();

        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUserUrl,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        try
                        {
                           if(response.getString("login").equals("true"))
                           {

                               Bundle bundle = new Bundle();
                               bundle.putString("edttext", "From Activity");


                               intent = new Intent(LoginActivity.this, DomesticUserActivity.class);
                               startActivity(intent);
                           }
                           else
                           {
                               Toast.makeText(getApplicationContext(),"Please Check the Credentials",Toast.LENGTH_SHORT).show();

                           }

                        }
                        catch (JSONException ex)
                        {

                            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                        }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Volley Error",Toast.LENGTH_SHORT).show();
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

    }


}
