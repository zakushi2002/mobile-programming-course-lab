package com.example.startactivityforresult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private EditText input_1, input_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.textView_result);
        input_1 = findViewById(R.id.edit_text_input_1);
        input_2 = findViewById(R.id.edit_text_input_2);

        Button openActivity2 = findViewById(R.id.button_open_activity_2);
        openActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input_1.getText().toString().equals("") || input_2.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please insert numbers", Toast.LENGTH_SHORT).show();
                }
                else {
                    int number_1 = Integer.parseInt(input_1.getText().toString());
                    int number_2 = Integer.parseInt(input_2.getText().toString());
                    Intent intent = new Intent(MainActivity.this, Activity2.class);
                    intent.putExtra("number_1", number_1);
                    intent.putExtra("number_2", number_2);
                    startActivityForResult(intent, 1);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("result", 0);
                resultTextView.setText("" + result);
            }
            if (requestCode == RESULT_CANCELED) {
                resultTextView.setText("Nothing selected");
            }
        }
    }
}