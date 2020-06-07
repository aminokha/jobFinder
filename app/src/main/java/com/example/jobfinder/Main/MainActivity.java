package com.example.jobfinder.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jobfinder.R;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.jobfinder.Model.Constants.CHECK_USER_FOR_TYPE_REGISTER;
import static com.example.jobfinder.Model.Constants.TYPE_SEARCH_FOR_WORK;
import static com.example.jobfinder.Model.Constants.TYPE_WORKER;

public class MainActivity extends AppCompatActivity {
    Button searchForJob, Worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Toast.makeText(this, "خطينا مالتصميم شوفوا الكود ول كاين خطأ ", Toast.LENGTH_LONG).show();
        Worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                //CHECK_USER_FOR_TYPE_REGISTER this constant in Constant class i m created it
                intent.putExtra(CHECK_USER_FOR_TYPE_REGISTER, TYPE_WORKER);
                startActivity(intent);
            }
        });
        searchForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                //CHECK_USER_FOR_TYPE_REGISTER this constant in Constant class i m created it
                intent.putExtra(CHECK_USER_FOR_TYPE_REGISTER, TYPE_SEARCH_FOR_WORK);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        searchForJob = findViewById(R.id.searchforjob);
        Worker = findViewById(R.id.worker);
    }
}
