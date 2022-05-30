package com.example.medo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button login_btn, new_account_btn;
    EditText _username, _password;
    String username, password;
    TextView forgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login_btn);
        new_account_btn = findViewById(R.id.new_account_btn);

        forgetpass = findViewById(R.id.forgetPass);

        _username = findViewById(R.id.username);
        _password = findViewById(R.id.password);

        login_btn.setOnClickListener(view -> {

            username = _username.getText().toString().trim();
            password = _password.getText().toString().trim();

            if (!validateFields(username, password)) {
                return;
            }

            Query checkuser = FirebaseDatabase.getInstance("https://medo-e218a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").orderByChild("username").equalTo(username);

            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String systempass = snapshot.child(username).child("password").getValue(String.class);
                        if (systempass.equals(password)) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                            String fullname = snapshot.child(username).child("fullname").getValue(String.class);

                            SessionManager sessionManager = new SessionManager(LoginActivity.this);
                            sessionManager.createLoginSession(fullname, username, password);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            intent.putExtra("sendfullname", fullname);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "User does not Exist!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });

        new_account_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            finish();
        });

        forgetpass.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
            finish();
        });
    }

    private boolean validateFields(String username, String password) {

        if (username.isEmpty()) {
            Toast.makeText(this, "Enter Your Username!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.length() < 8) {
            Toast.makeText(this, "Username Length should be Atleast 6!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Your Password!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password Length should be Atleast 8!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}