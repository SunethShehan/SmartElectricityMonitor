package mycompany.smartelectricitymonitor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        transaction =  fragmentManager.beginTransaction();


        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        profileFragment = new ProfileFragment();

        transaction.add(R.id.content,homeFragment).commit();

    }

}
