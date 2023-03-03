package com.example.startactivityforresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        setTitle("Activity 2");

        Intent intent = getIntent();
        int number_1 = intent.getIntExtra("number_1", 0);
        int number_2 = intent.getIntExtra("number_2", 0);
        TextView textViewNumbers = findViewById(R.id.textView_show);
        textViewNumbers.setText("Numbers: " + number_1 + ", " + number_2);

        Button addButton = findViewById(R.id.add_button);
        Button subtractButton = findViewById(R.id.subtract_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = number_1 + number_2;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", result);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = number_1 - number_2;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", result);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}