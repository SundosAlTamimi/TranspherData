package com.hiaryabeer.transferapp;

//import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.hiaryabeer.transferapp.MainActivity.itemcode;
import static com.hiaryabeer.transferapp.MainActivity.zone;


public class ScanActivity extends AppCompatActivity
    implements ZXingScannerView.ResultHandler{

        private ZXingScannerView mScannerView;
        private  String type="";

        @Override
        public void onCreate(Bundle state) {
            super.onCreate(state);
            type = getIntent().getStringExtra("key");
            Log.e("typeonCreate",""+type);
            mScannerView = new ZXingScannerView(this);
            List<BarcodeFormat> formats = new ArrayList<>();
            formats.add(BarcodeFormat.CODABAR);
            formats.add(BarcodeFormat.CODE_128);
            formats.add(BarcodeFormat.MAXICODE);
            formats.add(BarcodeFormat.CODE_93);
            formats.add(BarcodeFormat.CODE_39);
//            formats.add(BarcodeFormat.QR_CODE);

            setContentView(mScannerView);// Programmatically initialize the scanner view
//            setContentView(mScannerView);                // Set the scanner view as the content view
        }

        @Override
        public void onResume() {
            super.onResume();
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }

        @Override
        public void onPause() {
            super.onPause();
            mScannerView.stopCamera();           // Stop camera on pause
        }

        @Override
        public void handleResult(Result rawResult) {
            // Do something with the result here
            // Log.v("tag", rawResult.getText()); // Prints scan results
            // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
            String valueBarcode = rawResult.getText().toString();

      if(type.equals("4")){

          zone.setText(valueBarcode);

      }
          else if(type.equals("5")){
              itemcode.setText(valueBarcode);}

            onBackPressed();

            // If you would like to resume scanning, call this method below:
            //mScannerView.resumeCameraPreview(this);
        }
}
