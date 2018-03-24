package mycompany.smartelectricitymonitor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class DomesticUserActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                    case R.id.navigation_home:
                    {

                        return true;
                    }
                    case R.id.navigation_dashboard:
                    {

                        return true;
                    }
                    case R.id.navigation_profile:
                    {
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
        setContentView(R.layout.activity_domestic_user);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        transaction =  fragmentManager.beginTransaction();

        ProfileFragment profileFragment = new ProfileFragment();

        //transaction.add(R.id.content,profileFragment).commit();
    }

}
