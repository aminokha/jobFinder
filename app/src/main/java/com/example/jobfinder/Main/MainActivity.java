package com.example.jobfinder.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jobfinder.R;

import static com.example.jobfinder.Model.Constants.SPLASH_DISPLAY_LENGTH;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // I will added new handler to start the Login activity and close splash screen after some second
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Create an Intent that will start the Sign in-Activity.
                Intent toSignIn = new Intent(MainActivity.this,SignInActivity.class);
                //this method for start activity SignInActivity
                startActivity(toSignIn);

                //this method for finish MainActivity
                finish();
            }

        }, /* This constant is length display*/SPLASH_DISPLAY_LENGTH);
    }
}
