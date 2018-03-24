package mycompany.smartelectricitymonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}
