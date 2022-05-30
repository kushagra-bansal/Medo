package com.example.medo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText _fullname, _username, _password, _repassword;
    String fullname, username, password, repassword;
    ImageView signup_btn;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _fullname = findViewById(R.id.fullname);
        _username = findViewById(R.id.username);
        _password = findViewById(R.id.password);
        _repassword = findViewById(R.id.repassword);

        signup_btn = findViewById(R.id.signup_btn);
        login_btn = findViewById(R.id.login_btn);

        signup_btn.setOnClickListener(view -> {

            fullname = _fullname.getText().toString().trim();
            username = _username.getText().toString().trim();
            password = _password.getText().toString().trim();
            repassword = _repassword.getText().toString().trim();

            if (validateFields(fullname, username, password, repassword)) {
                storeNewuser();
            }
        });

        login_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

    private boolean validateFields(String fullname, String username, String password, String repassword) {

        if (fullname.isEmpty()) {
            Toast.makeText(this, "Enter Your Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.isEmpty()) {
            Toast.makeText(this, "Enter Your Username!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.length()<8) {
            Toast.makeText(this, "Username Length should be Atleast 6!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Your Password!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length()<8) {
            Toast.makeText(this, "Password Length should be Atleast 8!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(repassword)) {
            Toast.makeText(this, "Passwords Do Not Match!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void storeNewuser() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://medo-e218a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser = new UserHelperClass(fullname, username, password);
        reference.child(username).setValue(addNewUser);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra("sendfullname", fullname);
        startActivity(intent);
        Toast.makeText(this, "Account Created.", Toast.LENGTH_SHORT).show();
        finish();
    }
}