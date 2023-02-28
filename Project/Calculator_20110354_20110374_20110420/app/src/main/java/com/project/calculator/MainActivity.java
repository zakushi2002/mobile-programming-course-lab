package com.project.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ast.Scope;

import java.util.Objects;


public class MainActivity extends AppCompatActivity{
    TextView result, expression;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnAdd, btnSub, btnMul, btnDiv, btnPer;
    Button btnDot, btnEq, btnBracket;
    Button btnAC, btnBackspace;
    String output, newOutput;
    String process = "";
    boolean checkBrackets = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Number
        btn0 = findViewById(R.id.button_digit_0);
        btn1 = findViewById(R.id.button_digit_1);
        btn2 = findViewById(R.id.button_digit_2);
        btn3 = findViewById(R.id.button_digit_3);
        btn4 = findViewById(R.id.button_digit_4);
        btn5 = findViewById(R.id.button_digit_5);
        btn6 = findViewById(R.id.button_digit_6);
        btn7 = findViewById(R.id.button_digit_7);
        btn8 = findViewById(R.id.button_digit_8);
        btn9 = findViewById(R.id.button_digit_9);
        // Calculations
        btnAdd = findViewById(R.id.button_addition);
        btnSub = findViewById(R.id.button_subtraction);
        btnMul = findViewById(R.id.button_multiplication);
        btnDiv = findViewById(R.id.button_division);
        btnPer = findViewById(R.id.button_percentage);
        btnDot = findViewById(R.id.button_dot);
        btnEq = findViewById(R.id.button_equal);
        btnBracket = findViewById(R.id.button_brackets);
        // Feature
        btnAC = findViewById(R.id.button_ac);
        btnBackspace = findViewById(R.id.button_backspace);
        // View
        result = findViewById(R.id.textView_result);
        expression = findViewById(R.id.textView_expression);
        expression.setText("");

        // Feature and Calculation - Event
        btnAC.setOnClickListener(this::onClickCheck);
        btnAdd.setOnClickListener(this::onClickCheck);
        btnSub.setOnClickListener(this::onClickCheck);
        btnMul.setOnClickListener(this::onClickCheck);
        btnDiv.setOnClickListener(this::onClickCheck);
        btnPer.setOnClickListener(this::onClickCheck);
        btnEq.setOnClickListener(this::onClickCheck);
        btnBracket.setOnClickListener(this::onClickCheck);
        btnBackspace.setOnClickListener(this::onClickCheck);
        // Number - Event
        btn1.setOnClickListener(this::onClickCheck);
        btn2.setOnClickListener(this::onClickCheck);
        btn3.setOnClickListener(this::onClickCheck);
        btn4.setOnClickListener(this::onClickCheck);
        btn5.setOnClickListener(this::onClickCheck);
        btn6.setOnClickListener(this::onClickCheck);
        btn7.setOnClickListener(this::onClickCheck);
        btn8.setOnClickListener(this::onClickCheck);
        btn9.setOnClickListener(this::onClickCheck);
        btn0.setOnClickListener(this::onClickCheck);
        btnDot.setOnClickListener(this::onClickCheck);
    }
    // Xử lý lúc nhấn các Button trên giao diện
    /*
        Requirements for functions of this calculator are at least:
        1) Add, minus, multiply, divide (+,-,*,/)
        2) Works on double values. (.)
        3) Can delete each wrong number. (<-)
        4) Can delete current result and return blank screen (C)
    */
    public void onClickCheck(View view) {
        Button button = (Button) view;
        String dataClick = button.getText().toString();
        switch (dataClick) {
            case "AC":
                // 4) Can delete current result and return blank screen (AC)
                process = null;
                output = null;
                result.setText("0");
                checkBrackets = false;
                break;
            // 1) Add, minus, multiply, divide (+,-,*,/)
            case "+":
                process+="+";
                break;
            case "-":
                process+="-";
                break;
            case "x":
                process+="x";
                break;
            case "/":
                process+="/";
                break;
            case "%":
                process+="%";
                /*double change = Double.parseDouble(expression.getText().toString()) / 100;
                result.setText(String.valueOf(change));*/
                break;
            case ".":
                if (process == null) {
                    process = "0.";
                    break;
                }
                process+=".";
                break;
            case "=":
                process = expression.getText().toString();
                // Thay thế các kí tự để xử lý các con số như phép nhân(x), tính phần trăm => "*", "/100"
                process = process.replaceAll("x", "*");
                process = process.replaceAll("%", "/100");
                // 2) Works on double values. (.)
                // Xử lý tính toán thông qua javascript - com.faendir.rhino:rhino-android:1.5.2
                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);
                String finalResult = "";
                try {
                    Scriptable scriptable = rhino.initStandardObjects();
                    finalResult = rhino.evaluateString(scriptable, process, "javascript", 1, null).toString();
                    if (finalResult.endsWith(".0")) {
                        // Bỏ phần thừa số nguyên ".0"
                        finalResult = finalResult.replaceAll(".0","");
                    }
                    else if (finalResult.equals("org.mozilla.javascript.Undefined@0")) {
                        finalResult = "0";
                    }
                }
                // Trả về lỗi khi xảy ra Exception
                catch (Exception e) {
                    Log.i("Error", "Error!!!");
                    // Toast.makeText(MainActivity.this,"Error!!!", Toast.LENGTH_SHORT).show();
                    finalResult = "Error!!!";
                }
                result.setText(finalResult);
                break;
            case "backspace":
                // 3) Can delete each wrong number.
                if (expression.getText() != "" && expression.getText().length() != 0) {

                    if (expression.getText().toString().endsWith(")")) {
                        checkBrackets = true;
                    }
                    else if (expression.getText().toString().endsWith("(")) {
                            checkBrackets = false;
                    }
                    process = process.substring(0, process.length() - 1);
                }
                break;
            case "()":
                if (checkBrackets) {
                    process+=")";
                    checkBrackets = false;
                }
                else {
                    process+="(";
                    checkBrackets = true;
                }
                break;
            default:
                if (process == null) {
                    process = "";
                }
                process+=dataClick;
        }
        if (process == null) {
            process = "";
        }
        expression.setText(process);
    }
}