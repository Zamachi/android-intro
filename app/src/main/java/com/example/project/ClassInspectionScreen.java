package com.example.project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassInspectionScreen extends AppCompatActivity {

    private String url = "https://en.uesp.net/wiki/Morrowind:Classes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_inspection_screen);

        WebView klase= (WebView) (findViewById(R.id.klase));
        klase.getSettings().setJavaScriptEnabled(true);
        klase.getSettings().setDomStorageEnabled(true);
        klase.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
               String izvrsiti = "javascript: (function(){" +
                       "var x = document.querySelector(\"#Pre-defined_Classes\").parentElement; " +
                       "var y = x.nextElementSibling;" +
                       "document.body.innerHTML = \"\";" +
                       "document.body.append(x);" +
                       "document.body.append(y);" +
                       "}) ();";
               klase.loadUrl(izvrsiti);
            }
        });
        klase.loadUrl(url);
    }
}
