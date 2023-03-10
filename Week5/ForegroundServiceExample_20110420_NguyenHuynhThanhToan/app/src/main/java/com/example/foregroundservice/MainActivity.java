package com.example.foregroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editData;
    private Button buttonStart, buttonStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editData = findViewById(R.id.edit_data_intent);
        buttonStart = findViewById(R.id.button_start_service);
        buttonStop = findViewById(R.id.button_stop_service);

        buttonStart.setOnClickListener(v -> clickStartService());
        buttonStop.setOnClickListener(v -> clickStopService());
    }

    private void clickStopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    private void clickStartService() {
        Song song = new Song("Khuất Lối", "H-Kray", R.drawable.khuatloihkrayzingmp3, R.raw.khuatloihkrayofficiallyricsvideo);
        Intent intent = new Intent(this, MyService.class);
        // intent.putExtra("key_data", editData.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song", song);
        intent.putExtras(bundle);

        startService(intent);
    }
}