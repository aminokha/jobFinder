package com.example.jobfinder.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobfinder.Model.Common;
import com.example.jobfinder.Model.Users;
import com.example.jobfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.jobfinder.Model.Constants.CHECK_USER_FOR_TYPE_REGISTER;

public class SignInActivity extends AppCompatActivity {
    // Definition object  edit text
    private MaterialEditText editTextPhoneNumber, editTextPassword;
    // Definition object button
    private Button signIn;
    // Definition object button
    private TextView toSignUp;
    // Definition Firebase and get Instance from it
    private FirebaseDatabase firebaseDatabase;
    // Definition object from firebase database
    private DatabaseReference databaseReference;

    private String typeRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // function initialize view and linking with id in activity
        initViews();
        // if text view(Already have a account) sign up clicked , start activity sign up
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define intent to start activity sign up
                Intent toSignUp = new Intent(SignInActivity.this, SignupActivity.class);
                startActivity(toSignUp);
            }
        });
        // if button sign in clicked
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this variable equal data writer in edit text box
                String phone = editTextPhoneNumber.getText().toString();
                String password = editTextPassword.getText().toString();

                // check if edit text is empty and stupid user click in sign in
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    //This is toast message
                    Toast.makeText(SignInActivity.this, "Please fill the information", Toast.LENGTH_SHORT).show();
                } else {
                    // this method connect with firebase to sign in
                    signIn(phone, password);
                }

            }
        });

    }

    private void signIn(String phone, final String password) {
        // just progress dialog waiting while get data in firebase
        final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Please waiting ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        //this method from class Database reference , if you can't understand , enter to  firebase documentation
        databaseReference.child(typeRegister).child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            // metod onDataChane when database has any changing in his children
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //this method  return boolean , check for user is exists in database
                if (dataSnapshot.exists()) {
                    // i define object for get all info for this user and pass this data to activity home
                    Users user = dataSnapshot.getValue(Users.class);
                    // this variable for get data in child password in database
                    String dataPass = dataSnapshot.child("password").getValue(String.class);
                    if (password.equals(dataPass)) {
                        // dismiss the progress dialog
                        progressDialog.dismiss();
                        // this for pass data in home
                        Common.currentUserOnline = user;
                        Intent toHome = new Intent(SignInActivity.this, SignupActivity.class);
                        startActivity(toHome);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Your password is wrong ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "This phone number is not exists , please sign up", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                // method on cancelled when firebase has error
                Toast.makeText(SignInActivity.this, "Error : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        editTextPhoneNumber = findViewById(R.id.edtPhoneIN);
        editTextPassword = findViewById(R.id.edtPasswordIN);
        signIn = findViewById(R.id.btnSignIn);
        toSignUp = findViewById(R.id.to_sign_up);
        // this for get permission or instance from firebase server
        firebaseDatabase = FirebaseDatabase.getInstance();
        // this gor get permission for enter to database ("Users" is like a column in database)
        databaseReference = firebaseDatabase.getReference().child("Users");
        // this check if intnt has data or no
        if (getIntent().getExtras() != null) {
            typeRegister = getIntent().getExtras().getString(CHECK_USER_FOR_TYPE_REGISTER);
        } else {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }
    }
}
