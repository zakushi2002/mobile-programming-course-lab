package com.example.webpagelauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent receivedIntent = getIntent();
        Uri uri = receivedIntent.getData();
        URL webPageURL = null;
        if (uri != null) {
            try {
                webPageURL = new URL(uri.getScheme(), uri.getHost(), uri.getPath());
            }
            catch (Exception ex) {
                Toast.makeText(this, "Intent exception occurred", Toast.LENGTH_SHORT).show();
            }
            WebView webView = (WebView) findViewById(R.id.web_view);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(webPageURL.toString());
        }
    }
}