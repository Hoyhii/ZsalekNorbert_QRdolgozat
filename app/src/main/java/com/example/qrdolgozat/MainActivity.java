package com.example.qrdolgozat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView qrSzoveg;
    private Button scanGomb,kiirGomb;
    private boolean engedely;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            engedely = false;

            String[] permissions =
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            engedely = true;
        }



        scanGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("QR Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        kiirGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (engedely) {
                    try {
                        String szoveg = qrSzoveg.getText().toString();
                        Kiiras.kiir(szoveg);
                        Toast.makeText(MainActivity.this,"Victory", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.getStackTrace();
                        Toast.makeText(MainActivity.this,"Defeat", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "You have killed Sylvanas Windrunner on mythic, WP!", Toast.LENGTH_SHORT).show();
            } else {
                qrSzoveg.setText(intentResult.getContents());
                kiirGomb.setVisibility(View.VISIBLE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            engedely =
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void init() {
        scanGomb = findViewById(R.id.scanGomb);
        kiirGomb = findViewById(R.id.kiirGomb);
        qrSzoveg = findViewById(R.id.qrSzoveg);
    }
}