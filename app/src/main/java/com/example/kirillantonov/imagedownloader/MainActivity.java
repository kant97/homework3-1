package com.example.kirillantonov.imagedownloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String fileName = "rotator.jpg";
    public static final String bc = "Super_try_of_bc";
    ImageView image;
    TextView text;
    BroadcastReceiver pRec, qRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        final File file = new File(getFilesDir(), fileName);
        final Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        pRec = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Intent i = new Intent(context, DownloaderService.class);
                context.startService(i);
            }
        };
        qRes = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final File file = new File(getFilesDir(), fileName);
                final Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                image.setImageBitmap(bm);
                image.setVisibility(View.VISIBLE);
                text.setVisibility(View.INVISIBLE);
            }
        };
        registerReceiver(pRec, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(qRes, new IntentFilter(bc));
        if (file.exists()) {
            image.setImageBitmap(bm);
            image.setVisibility(View.VISIBLE);
            text.setVisibility(View.INVISIBLE);
        } else {
            image.setVisibility(View.INVISIBLE);
            text.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(pRec);
        unregisterReceiver(qRes);
    }

}
