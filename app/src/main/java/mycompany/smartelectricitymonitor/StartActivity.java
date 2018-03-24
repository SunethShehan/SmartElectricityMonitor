package mycompany.smartelectricitymonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        textView = (TextView)findViewById(R.id.startTextView);
        imageView = (ImageView)findViewById(R.id.startImgView);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);
        final Intent intentstart = new Intent(this,LoginActivity.class);
        Thread timer = new Thread(){

            public void run(){
                try{
                    sleep(5000);

                } catch (InterruptedException e) {
//                    e.printStackTrace();

                    Toast.makeText(StartActivity.this,"Error"+e,Toast.LENGTH_LONG).show();


                } finally {
                    startActivity(intentstart);
                    finish();
                }
            }

        };

        timer.start();

    }
}
