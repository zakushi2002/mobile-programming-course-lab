package com.mobile.machinelearning.translationapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.mobile.machinelearning.translationapp.R;
import com.mobile.machinelearning.translationapp.database.HistoryDatabase;
import com.mobile.machinelearning.translationapp.model.HistoryItem;
import com.mobile.machinelearning.translationapp.model.ModelLanguage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText etInput;
    private Button btnHistory, btnClear, btnTranslate;
    private ImageButton btnCapture;
    private TextView tvOutput;
    Uri imageUri;
    private MaterialButton btnSourceLanguage, btnTargetLanguage;
    private TranslatorOptions translatorOptions;
    private Translator translator;
    private ProgressDialog progressDialog;
    private List<ModelLanguage> languageList;
    private TextRecognizer textRecognizer;
    private static final String TAG = "MAIN_TAG";
    private String sourceLanguageCode = "en", targetLanguageCode = "vi";
    private String sourceLanguage = "English", targetLanguage = "Vietnamese";
    private String sourceText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHistory = findViewById(R.id.btn_history);
        // Go to History
        btnHistory.setOnClickListener(v -> openHistoryActivity());
        btnCapture = findViewById(R.id.btn_capture);
        btnCapture.setOnClickListener(v -> captureImage());
        etInput = findViewById(R.id.et_input);
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        btnClear = findViewById(R.id.btn_clear);

        tvOutput = findViewById(R.id.tv_output);
        tvOutput.setMovementMethod(new ScrollingMovementMethod());

        btnClear.setOnClickListener(v -> {
            if (etInput.getText().toString().isEmpty() && tvOutput.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nothing to clear", Toast.LENGTH_SHORT).show();
                return;
            }
            etInput.setText("");
            tvOutput.setText("");
        });
        loadAvailableLanguages();
        btnSourceLanguage = findViewById(R.id.btn_source_language);
        btnTargetLanguage = findViewById(R.id.btn_target_language);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        btnSourceLanguage.setOnClickListener(v -> sourceLanguageChoose());
        btnTargetLanguage.setOnClickListener(v -> targetLanguageChoose());
        btnTranslate = findViewById(R.id.btn_translate);
        btnTranslate.setOnClickListener(v -> validateData());
    }

    private void loadAvailableLanguages() {
        languageList = new ArrayList<>();
        List<String> languageCodeList = TranslateLanguage.getAllLanguages();
        for (String languageCode : languageCodeList) {
            String languageName = new Locale(languageCode).getDisplayLanguage(); // Example: en -> English

            Log.d(TAG, "loadAvailableLanguages: " + languageCode + ":" + languageName);
            ModelLanguage modelLanguage = new ModelLanguage(languageCode, languageName);
            languageList.add(modelLanguage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
                recognizeText();
            }
        } else {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }

    }

    // Recognize text from image
    private void recognizeText() {
        if (imageUri != null) {
            try {
                InputImage inputImage = InputImage.fromFilePath(MainActivity.this, imageUri);
                Task<Text> result = textRecognizer.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text text) {
                                String recognizeText = text.getText();
                                etInput.setText(recognizeText);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void captureImage() {
        ImagePicker.with(MainActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void openHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void sourceLanguageChoose() {
        PopupMenu popupMenu = new PopupMenu(this, btnSourceLanguage);
        for (int i = 0; i < languageList.size(); i++) {
            popupMenu.getMenu().add(Menu.NONE, i, i, languageList.get(i).getName());
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int position = item.getItemId(); // get clicked item position

                sourceLanguageCode = languageList.get(position).getCode();
                sourceLanguage = languageList.get(position).getName();

                btnSourceLanguage.setText(sourceLanguage);
                etInput.setHint("Enter text in " + sourceLanguage);
                Log.d(TAG, "onMenuItemClick: " + sourceLanguageCode);
                Log.d(TAG, "onMenuItemClick: " + sourceLanguage);
                return false;
            }
        });
    }

    private void targetLanguageChoose() {
        PopupMenu popupMenu = new PopupMenu(this, btnTargetLanguage);
        for (int i = 0; i < languageList.size(); i++) {
            popupMenu.getMenu().add(Menu.NONE, i, i, languageList.get(i).getName());
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            int position = item.getItemId(); // get clicked item position

            targetLanguageCode = languageList.get(position).getCode();
            targetLanguage = languageList.get(position).getName();

            btnTargetLanguage.setText(targetLanguage);
            tvOutput.setHint("Enter text in " + targetLanguage);
            Log.d(TAG, "onMenuItemClick: " + targetLanguageCode);
            Log.d(TAG, "onMenuItemClick: " + targetLanguage);
            return false;
        });
    }

    private void validateData() {
        sourceText = etInput.getText().toString().trim();
        Log.d(TAG, "validateData: " + sourceText);
        if (sourceText.isEmpty()) {
            Toast.makeText(this, "Please enter text to translate", Toast.LENGTH_SHORT).show();
        } else {
            startTranslation();
        }
    }

    private void startTranslation() {
        progressDialog.setMessage("Processing language model...");
        progressDialog.show();
        translatorOptions = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(targetLanguageCode)
                .build();
        translator = Translation.getClient(translatorOptions);

        DownloadConditions downloadConditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator.downloadModelIfNeeded(downloadConditions)
                .addOnSuccessListener(unused -> {
                    Log.d(TAG, "onSuccess: Language Model Downloaded, starting translation");
                    progressDialog.setMessage("Translating...");

                    translator.translate(sourceText)
                            .addOnSuccessListener(s -> {
                                Log.d(TAG, "onSuccess: " + s);
                                progressDialog.dismiss();
                                tvOutput.setText(s);
                                addHistoryItem();
                            })
                            .addOnFailureListener(e -> {
                                // progressDialog.setMessage("Failed to translate");
                                progressDialog.dismiss();
                                Log.d(TAG, "onFailure: " + e.getMessage());
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + e.getMessage());

                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    // add history item to database
    private void addHistoryItem() {
        String sourceText = etInput.getText().toString().trim();
        String targetText = tvOutput.getText().toString().trim();

        if (!tvOutput.getText().toString().isEmpty() && !btnSourceLanguage.getText().toString().isEmpty() && !btnTargetLanguage.getText().toString().isEmpty()) {
            HistoryItem historyItem = new HistoryItem(targetText, sourceText, btnSourceLanguage.getText().toString().trim(), btnTargetLanguage.getText().toString().trim());
            HistoryDatabase.getInstance(MainActivity.this).historyDao().insertHistory(historyItem);
            Toast.makeText(this, "Item added to history", Toast.LENGTH_SHORT).show();
        }
    }

}