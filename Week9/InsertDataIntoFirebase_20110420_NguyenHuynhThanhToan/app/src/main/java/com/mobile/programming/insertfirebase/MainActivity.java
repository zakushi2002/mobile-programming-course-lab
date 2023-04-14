package com.mobile.programming.insertfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.programming.insertfirebase.entity.User;

public class MainActivity extends AppCompatActivity {
    private EditText edtUsername, edtAddress;
    private Button btnAddUser;
    User user;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsername = findViewById(R.id.edt_username);
        edtAddress = findViewById(R.id.edt_address);
        btnAddUser = findViewById(R.id.btn_add_user);
        reference = FirebaseDatabase.getInstance().getReference().child("User");
        btnAddUser.setOnClickListener(view -> {
            String username = edtUsername.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(address)) {
                return;
            }
            user = new User(username, address);
            reference.push().setValue(user);
            Toast.makeText(MainActivity.this, "Add user successfully", Toast.LENGTH_SHORT).show();
            edtUsername.setText("");
            edtAddress.setText("");
            hideSoftKeyboard();
        });
    }
    public void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}