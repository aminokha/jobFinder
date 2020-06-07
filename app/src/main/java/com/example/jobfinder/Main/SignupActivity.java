package com.example.jobfinder.Main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobfinder.Model.Users;
import com.example.jobfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.jobfinder.Model.Constants.CHECK_USER_FOR_TYPE_REGISTER;

public class SignupActivity extends AppCompatActivity {
    // Definition object  edit text
    private MaterialEditText editTextPhoneNumber, editTextFirstAndLastName, editTextPassword;
    // Definition object button
    private Button signUp;
    // Definition object button
    private TextView toSignIn;
    // Definition Firebase and get Instance from it
    private FirebaseDatabase firebaseDatabase;
    // Definition object from firebase database
    private DatabaseReference databaseReference;

    private String typeRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // function initialize view and linking with id in activity
        initViews();
        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this method start activity same thing with create object from intent
                startActivity(new Intent(SignupActivity.this, SignInActivity.class).putExtra(CHECK_USER_FOR_TYPE_REGISTER, typeRegister));
                //this method finish current activity finally
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextFirstAndLastName.getText().toString();
                final String phone = editTextPhoneNumber.getText().toString();
                final String password = editTextPassword.getText().toString();
                // text utils is class helping me for check the edit text is empty or no
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(SignupActivity.this, "Please write name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(SignupActivity.this, "Please write phone number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "Please write password", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(typeRegister).child(phone).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(SignupActivity.this, "This phone number already exists . you can sign in", Toast.LENGTH_SHORT).show();
                            } else {
                                // this method for register user in database
                                saveUser(name, phone, password);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(SignupActivity.this, "Error : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void saveUser(String name, String phone, String password) {
        Users user = new Users(name, password, "");
        databaseReference.child(typeRegister).child(phone).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Successfully register , you can sign in now", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, SignInActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Error : \n " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, "Error : \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        editTextFirstAndLastName = findViewById(R.id.edtNameUP);
        editTextPhoneNumber = findViewById(R.id.edtPhoneUP);
        editTextPassword = findViewById(R.id.edtPasswordUP);
        signUp = findViewById(R.id.btnSignUP);
        toSignIn = findViewById(R.id.to_sign_in);
        // this for get permission or instance from firebase server
        firebaseDatabase = FirebaseDatabase.getInstance();
        // this gor get permission for enter to database ("Users" is like a column in database)
        databaseReference = firebaseDatabase.getReference().child("Users");

        if (getIntent().getExtras() == null) {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        } else {
            typeRegister = getIntent().getExtras().getString(CHECK_USER_FOR_TYPE_REGISTER);
        }
    }
}
