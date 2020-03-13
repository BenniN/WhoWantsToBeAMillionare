package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MillionareEndsceen extends Activity {

        private Button  btnshare;
        //private ImageView iv;
        private String sharePath="no";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.millionare);
            Button button1 = (Button) findViewById(R.id.buttonendscreen);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activityStart = new Intent(MillionareEndsceen.this, MainActivity.class);
                    startActivity(activityStart);
                    finish();

                }
            });


            btnshare = findViewById(R.id.buttonshare);

            btnshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeScreenshot();
                    if (!sharePath.equals("no")) {
                        share(sharePath);
                    }
                }
            });

        }

        private void takeScreenshot() {
            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

            try {
                // image naming and path  to include sd card  appending name you choose for file
                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

                // create bitmap screen capture
                View v1 = getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);

                File imageFile = new File(mPath);

                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

                //setting screenshot in imageview
                String filePath = imageFile.getPath();

                //Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                //iv.setImageBitmap(ssbitmap);
                sharePath = filePath;

            } catch (Throwable e) {
                // Several error may come out with file handling or DOM
                e.printStackTrace();
            }
        }

        private void share(String sharePath){

            Log.d("ffff",sharePath);
            File file = new File(sharePath);
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent .setType("image/*");
            intent .putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(intent );

        }
}
