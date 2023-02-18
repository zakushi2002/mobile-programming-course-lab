package com.example.fragmentcommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button firstFragmentButton, secondFragmentButton;
    TextView fragmentText;
    private ItemViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Các widget tương tác
        firstFragmentButton = findViewById(R.id.buttonFragment1);
        secondFragmentButton = findViewById(R.id.buttonFragment2);
        fragmentText = findViewById(R.id.fragmentText);
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getSelectedItem().observe(this, item -> {
            fragmentText.setText(item);
        });
        Fragment frag1 = new Fragment1();
        Fragment frag2 = new Fragment2();

        // Thiết lập Frame Layout đầu tiên
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, frag1);
        transaction.commit();
        firstFragmentButton.setOnClickListener(v -> {
            // Thiết lập transaction mới
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            // Thay thế fragment
            transaction1.replace(R.id.frameLayout, frag1);
            // Thêm vào stack
            transaction1.addToBackStack(null);
            transaction1.commit();
        });
        secondFragmentButton.setOnClickListener(v -> {
            // Thiết lập transaction mới
            FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
            // Thay thế fragment
            transaction2.replace(R.id.frameLayout, frag2);
            // Thêm vào stack
            transaction2.addToBackStack(null);
            transaction2.commit();
        });
    }

}