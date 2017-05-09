package com.example.alex.quickpark.qr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.maps.MapsActivity;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.Serializable;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class QrActivity extends AppCompatActivity implements Serializable {

    String textoqr,user;
    Boolean qrencendido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qr_screen);
        ImageButton bMaps = (ImageButton) findViewById(R.id.bMaps);
        final ImageButton bQr = (ImageButton) findViewById(R.id.bQr);
        ImageButton bAju = (ImageButton) findViewById(R.id.bPref);
        Button flecha = (Button) findViewById(R.id.button2);

        user = getIntent().getStringExtra("user");

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        Intent intent = new Intent(QrActivity.this, ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        qrencendido = true;

        flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomaps = new Intent(QrActivity.this, IniciarQR.class);
                gomaps.putExtra("user",user);
                startActivity(gomaps);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            if(data!= null)
            {
                final Barcode barcode = data.getParcelableExtra("barcode");
                textoqr = barcode.displayValue;
                Toast.makeText(QrActivity.this, "texto:"+barcode.displayValue, Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(QrActivity.this, MapsActivity.class);
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
}


