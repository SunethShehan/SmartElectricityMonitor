package mycompany.smartelectricitymonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Should be redirect to the Dashboard
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Redirect to the Domestic User Activity
                Intent intent = new Intent(LoginActivity.this, IndustrialUserActivity.class);
                startActivity(intent);


            }
        });



    }
}
