package mycompany.smartelectricitymonitor;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class UserRegistrationActivity extends Activity {

    EditText txtFName,txtLName,txtAddress,txtTelephone,txtUnitNo,txtPassword,txtConfirmPassword,txtPremisesNoReg,txtAccountNo,txtMail;
    Spinner spnUserType;
    Button btnRegister,btnClear;

    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }


        /*Initialize*/
        // TextFields
        txtFName = (EditText)findViewById(R.id.txtFName);
        txtLName = (EditText)findViewById(R.id.txtLName);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtPremisesNoReg = (EditText)findViewById(R.id.txtPremisesNo);
        txtAccountNo = (EditText)findViewById(R.id.txtAccountNo);
        txtTelephone = (EditText)findViewById(R.id.txtTelephone);
        txtUnitNo = (EditText)findViewById(R.id.txtUnitNo);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        txtMail = (EditText)findViewById(R.id.txtmail);



        // Spinner
        spnUserType = (Spinner) findViewById(R.id.spnUserType);

        // Buttons
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnClear = (Button) findViewById(R.id.btnClear);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate the User Inputs and Save in the database

                //saveUser();
                //setNotification();

                // Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();

                //WebAPITask webAPITask = new WebAPITask();
                //webAPITask.execute();

               //saveUserTest();

               saveUser();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


          spnUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            userType =  String.valueOf(spnUserType.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void saveUser()
    {



       String saveUrl = "https://ereaderv10.azurewebsites.net/api/Users/";

        Map<String, String> param = new HashMap<String, String>();
//
//         param.put("User_Name",txtFName.getText().toString()+txtLName.getText().toString());
//         param.put("User_Password",txtPassword.getText().toString());
//         param.put("Premises_no",txtPremisesNoReg.getText().toString());
//         param.put("Account_no",txtAccountNo.getText().toString());
//         param.put("Name",txtLName.getText().toString());
//         param.put("Address",txtAddress.getText().toString());
//         param.put("Telephone_decimal",txtTelephone.getText().toString());
//         param.put("Email",txtMail.getText().toString());
//         param.put("User_Type","Domestic");
//         param.put("Unit_Schema_Id","1001");
//         param.put("ModuleId","1002");



        param.put("User_Name","TestUser6");
        param.put("User_Password","123");
        param.put("Premises_no","P-109");
        param.put("Account_no","256478g2953");
        param.put("Name","TestUser6");
        param.put("Address","Colombo");
        param.put("Telephone_decimal","test");
        param.put("Email","TestMail@mail.com");
        param.put("User_Type","Domestic");
        param.put("Unit_Schema_Id","1000");
        param.put("ModuleId","1000");


        try {

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, saveUrl, new JSONObject(param),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    System.out.println("Error -->>"+error.toString());


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
            Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();

        }

        //Toast.makeText(getApplicationContext(),param.toString(),Toast.LENGTH_SHORT).show();

        //  pDialog.cancel();
        //  pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        //  pDialog.setTitleText("Registration Successfully !");
        //  pDialog.show();
}


    private void saveUserTest()
    {
        String saveUrl = "https://ereaderv10.azurewebsites.net/api/Users";

        Map<String, String> param = new HashMap<String, String>();

        param.put("User_Id","TestUser1");
        param.put("User_Name","TestUser1");
        param.put("User_Password","abc123");
        param.put("Premises_no","P-103");
        param.put("Account_no","10212454236");
        param.put("Name","TestUser2");
        param.put("Address","Colombo");
        param.put("Telephone_decimal","01125536986");
        param.put("Email","TestMail@mail.com");
        param.put("User_Type","DomesticUser");
        param.put("Unit_Schema_Id","1000");
        param.put("ModuleId","1004");

        try {

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, saveUrl, new JSONObject(param),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    System.out.println("Error -->>"+error.toString());


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


    public void setNotification()
    {
        Toast.makeText(getApplicationContext(),"In the Notification Function",Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, getClass());
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        Notification n  = new Notification.Builder(this)
                .setContentTitle(txtFName.getText().toString()+ "Welcome to Smart Electricity Monitor !")
                .setContentText("You are Successfully Registered with"+"Module No")
                .setSmallIcon(R.drawable.ic_correct)
                .setContentIntent(pIntent)
                .setSound(uri)
                .setAutoCancel(true).build();

        n.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, n);

    }

    private class WebAPITask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {

           // saveUser();

            return null;
        }



    }






}


