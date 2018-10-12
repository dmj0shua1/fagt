package jediarstudios.fagt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class weaponsguide extends AppCompatActivity {

    private WebView mWebview ;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weaponsguide);
        mWebview = findViewById(R.id.wvWeaponsGuide);

        MobileAds.initialize(this,
                "ca-app-pub-6773163192158273~3926576548");

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        WebView htmlWebView = findViewById(R.id.wvWeaponsGuide);
        htmlWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setBuiltInZoomControls(true);
        htmlWebView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfWqKi4nuMB0eTmCp7ZcgeaSs8icORzwsaBi1uR9I8CvWviEQ/viewform");
    }

    private class CustomWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
