package jediarstudios.fagt;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class usefullinks extends AppCompatActivity {

    private Button btnWeaponsGuide;
    private Button btnYouTube;
    private Button btnBugReport;
    private Button btnPrivacy;

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usefullinks);

        btnWeaponsGuide = findViewById(R.id.btnWeaponsGuide);
        btnYouTube = findViewById(R.id.btnYouTube);
        btnBugReport = findViewById(R.id.btnBugReport);
        btnPrivacy = findViewById(R.id.btnPrivacy);


        MobileAds.initialize(this,
                    "ca-app-pub-6773163192158273~3926576548");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

       btnWeaponsGuide.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               if (isOnline()){
               Intent i = new Intent(getApplicationContext(),weaponsguide.class);
               startActivity(i);}else{
                   Toast.makeText(getApplicationContext(), "Internet connection is required", Toast.LENGTH_SHORT).show();
               }
           }
       });
       btnYouTube.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               Uri uriUrl = Uri.parse("https://www.youtube.com/channel/UCf9L-cadIIFBaAAQZmR_gsQ");
               Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
               startActivity(launchBrowser);
           }
       });

       btnBugReport.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               /* Create the Intent */
               Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

               /* Fill it with Data */
               emailIntent.setType("plain/text");
               emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"jediarstudios@gmail.com"});
               emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BAGT Bug Report");
               emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

               /* Send it off to the Activity-Chooser */
               startActivity(Intent.createChooser(emailIntent, "Send mail..."));
           }
       });

        btnPrivacy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri uriUrl = Uri.parse("https://jediarstudios.wixsite.com/mysite/privacy-policy");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
