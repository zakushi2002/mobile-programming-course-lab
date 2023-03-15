package com.example.backgroundservice;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("my_intent_thread");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(MyIntentService.this, "Service started", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(MyIntentService.this, "Service destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (this) {
            try {
                wait(20000);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}