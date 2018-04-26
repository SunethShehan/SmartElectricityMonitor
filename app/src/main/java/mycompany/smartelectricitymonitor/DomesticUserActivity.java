package mycompany.smartelectricitymonitor;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class DomesticUserActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    HomeFragment homeFragment;
    DashboardFragment dashboardFragment;
    ProfileFragment profileFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            transaction =  fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                    case R.id.navigation_home:
                    {

                        transaction.replace(R.id.content, homeFragment).commit();
                        return true;
                    }
                    case R.id.navigation_dashboard:
                    {
                        transaction.replace(R.id.content, dashboardFragment).commit();
                        return true;
                    }
                    case R.id.navigation_profile:
                    {
                        transaction.replace(R.id.content, profileFragment).commit();
                        return true;
                    }
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_user);

        // implement the registration form with modern controls

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        transaction =  fragmentManager.beginTransaction();


        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        profileFragment = new ProfileFragment();

        transaction.add(R.id.content,homeFragment).commit();

    }

    private void getUserDetails()
    {


        String getUserDetailsUrl = "https://ereaderv10.azurewebsites.net/api/Users/P-100/";
        //String getUserUrl = "https://ereaderv10.azurewebsites.net/api/Users/"+txtPremisesNo.getText()+"/"+txtPassword.getText();

        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUserDetailsUrl,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try
                            {

                                //break json into values
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.get("Test");

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


}
