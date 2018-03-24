package mycompany.smartelectricitymonitor;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserRegistrationActivity extends Activity {

    EditText txtFName,txtLName,txtAddress,txtTelephone,txtUnitNo,txtPassword,txtConfirmPassword;
    Spinner spnUserType;
    Button btnRegister,btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        /*Initialize*/
        // TextFields
        txtFName = (EditText)findViewById(R.id.txtFName);
        txtLName = (EditText)findViewById(R.id.txtLName);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtTelephone = (EditText)findViewById(R.id.txtTelephone);
        txtUnitNo = (EditText)findViewById(R.id.txtUnitNo);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText)findViewById(R.id.txtConfirmPassword);

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

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void saveUser()
    {




       String saveUrl = "";

        Map<String, String> param = new HashMap<String, String>();

        // param.put("emp_ID",txtEmpID.getText().toString());
        // param.put("emp_Name",txtFname.getText().toString()+" "+txtLname.getText().toString());
        // param.put("user_Name",txtUName.getText().toString());
        // param.put("email",txtMail.getText().toString());
        // param.put("pw",txtPassword.getText().toString());
        // param.put("employee_tp",txtTelephone.getText().toString());
        // param.put("department_ID",String.valueOf(deptPosition));
        // param.put("employee_Type",String.valueOf(empType));


        try {

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, saveUrl, new JSONObject(param),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(),"Registration Successfully !",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserRegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
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

        //  pDialog.cancel();
        //  pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        //  pDialog.setTitleText("Registration Successfully !");
        //  pDialog.show();





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


}
