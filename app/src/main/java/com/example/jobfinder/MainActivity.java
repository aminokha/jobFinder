package com.example.jobfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jobfinder.Searcher.SignInSearcher;
import com.example.jobfinder.Worker.WorkerSignup;

import androidx.appcompat.app.AppCompatActivity;

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
                Intent intent = new Intent(MainActivity.this, WorkerSignup.class);
                startActivity(intent);
            }
        });
        searchForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInSearcher.class);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        searchForJob = findViewById(R.id.searchforjob);
        Worker = findViewById(R.id.worker);
    }
}
