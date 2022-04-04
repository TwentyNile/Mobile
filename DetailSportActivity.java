package th.ac.kmutnb.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailSportActivity extends AppCompatActivity {


    private  String TAG = "my_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sport);
        String BASE_URL = "https://1447-49-228-17-124.ngrok.io/car/sport/";

        Intent itn = getIntent();
        int recID = itn.getIntExtra("recID", -1);
        Log.d(TAG, String.valueOf(recID));
        // Toast.makeText(this,String.valueOf(recID),Toast.LENGTH_SHORT).show();

        String urlStr2 = BASE_URL  + String.valueOf(recID+1);
//        String urlStr = "http://cs.kmutnb.ac.th";
        Log.i(TAG,urlStr2);

        WebView wv = findViewById(R.id.webview3);
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
//        wv.getSettings().setDefaultFontSize(18);
//        wv.getSettings().setBuiltInZoomControls(true);
        wv.loadUrl(urlStr2);

    }
}