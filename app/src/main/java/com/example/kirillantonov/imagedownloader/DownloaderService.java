package com.example.kirillantonov.imagedownloader;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloaderService extends Service implements Runnable {
    @Nullable

    private static final String url =
            "http://i.dailymail.co.uk/i/pix/2015/09/28/08/2CD1E26200000578-0-image-a-312_1443424459664.jpg";
    private File file;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)  {
        file = new File(getFilesDir(), MainActivity.fileName);
        if (!file.exists()) {
            new Thread(this).start();
        }
        return START_STICKY;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(new URL(url).openStream());
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int x;
            while ((x = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, x);
            }

            sendBroadcast(new Intent(MainActivity.bc));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
