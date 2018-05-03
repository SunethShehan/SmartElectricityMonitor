package mycompany.smartelectricitymonitor;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class IndustrialUserActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    //Home
    //Dashboard
    //Report
    //Profile

    HomeFragment homeFragment;
    DashboardFragment dashboardFragment;
    ReportsFragment reportsFragment;
    ProfileFragment profileFragment;

    Bundle extra;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction =  fragmentManager.beginTransaction();
            switch (item.getItemId()){

                    case R.id.navigation_home:
                    {
                        transaction.replace(R.id.content, homeFragment).commit();
                        return true;
                    }

                    case R.id.navigation_dashboard:
                    {
                        transaction.replace(R.id.content,dashboardFragment).commit();
                        return true;
                    }
//                    case R.id.navigation_reports:
//                    {
//                        transaction.replace(R.id.content,reportsFragment).commit();
//                        return true;
//                    }
                    case R.id.navigation_profile:
                    {
                        profileFragment.setArguments(extra);
                        transaction.replace(R.id.content, new ProfileFragment()).commit();
                        return true;
                    }
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industrial_user);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        transaction =  fragmentManager.beginTransaction();

        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        reportsFragment = new ReportsFragment();
        profileFragment = new ProfileFragment();

        transaction.replace(R.id.content, homeFragment).commit();

        extra = getIntent().getExtras();
        extra.putString("premisesNo",extra.getString("premisesNo"));

    }

}
