package mycompany.smartelectricitymonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnUserRegistration  = (Button)findViewById(R.id.btnUserRegistration);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Redirect to the Domestic User Activity
                intent = new Intent(LoginActivity.this, IndustrialUserActivity.class);
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

        String getUSerUrl = "https://getfeed.azurewebsites.net/api/Employees";

        Map<String, String> param = new HashMap<String, String>();

//        param.put("emp_ID",txtEmpID.getText().toString());
//        param.put("emp_Name",txtFname.getText().toString()+" "+txtLname.getText().toString());
//        param.put("user_Name",txtUName.getText().toString());
//        param.put("email",txtMail.getText().toString());
//        param.put("pw",txtPassword.getText().toString());
//        param.put("employee_tp",txtTelephone.getText().toString());
//        param.put("department_ID",String.valueOf(deptPosition));
//        param.put("employee_Type",String.valueOf(empType));


        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getUSerUrl, new JSONObject(param),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(getApplicationContext(),"Registration Successfully !",Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(EmployeeRegActivity.this, LoginActivity.class);
                            //startActivity(intent);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    System.out.println("Error -->>"+error.toString());


                }
            }){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                        JSONObject result = null;

                        if (jsonString != null && jsonString.length() > 0)
                            result = new JSONObject(jsonString);

                        return Response.success(result,
                                HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    } catch (JSONException je) {
                        return Response.error(new ParseError(je));
                    }


                }
            };

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
}
