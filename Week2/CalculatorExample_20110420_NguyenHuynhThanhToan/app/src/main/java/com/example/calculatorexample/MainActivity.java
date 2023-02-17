package com.example.calculatorexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tìm các widget trong screen
        Button calculate = (Button) findViewById(R.id.buttonCalculate);
        EditText inputA = (EditText) findViewById(R.id.inputTextA);
        EditText inputB = (EditText) findViewById(R.id.inputTextB);
        EditText result = (EditText) findViewById(R.id.outputResult);
        calculate.setOnClickListener(view -> {
            // Lấy dữ liệu từ EditText
            String textA = inputA.getText().toString();
            String textB = inputB.getText().toString();
            // Kiểm tra dữ liệu truyền vào
            if (!textA.isEmpty() && !textB.isEmpty()) {
                Double a = Double.parseDouble(textA);
                Double b = Double.parseDouble(textB);
                // Hàm định dạng chuỗi #.### - Example: 7.894
                DecimalFormat df = new DecimalFormat("#.###");
                String resultEnd = df.format(a+b);
                // Set Text của EditText
                result.setText(resultEnd);
            }
            else {
                Log.i("Error", "Please enter all the information!!!");
                Toast.makeText(MainActivity.this,"Please enter all the information!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}