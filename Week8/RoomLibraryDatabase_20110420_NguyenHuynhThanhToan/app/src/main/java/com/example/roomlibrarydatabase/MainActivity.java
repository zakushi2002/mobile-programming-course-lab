package com.example.roomlibrarydatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.roomlibrarydatabase.adapter.UserAdapter;
import com.example.roomlibrarydatabase.database.UserDatabase;
import com.example.roomlibrarydatabase.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtUsername, edtAddress;
    private Button btnAddUser;
    private RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        userAdapter = new UserAdapter();
        userList = new ArrayList<>();
        userAdapter.setData(userList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);

        rcvUser.setAdapter(userAdapter);
        btnAddUser.setOnClickListener(view -> addUser());

    }

    private void addUser() {
        String username = edtUsername.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(address)) {
            return;
        }
        User user = new User(username, address);
        UserDatabase.getInstance(this).userDAO().insertUser(user);
        Toast.makeText(this, "Add user successfully", Toast.LENGTH_SHORT).show();
        edtUsername.setText("");
        edtAddress.setText("");

        hideSoftKeyboard();

        userList = UserDatabase.getInstance(this).userDAO().findAll();
        userAdapter.setData(userList);

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

    private void initUI() {
        edtUsername = findViewById(R.id.edt_username);
        edtAddress = findViewById(R.id.edt_address);
        btnAddUser = findViewById(R.id.btn_add_user);
        rcvUser = findViewById(R.id.rcv_user);
    }
}