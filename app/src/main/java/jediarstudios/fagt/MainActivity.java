package jediarstudios.fagt;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements donation_dialog.OnFragmentInteractionListener {

    private Button btnBuild;
    private Button btnInfo;
    private Button btnRevert;
    private Button btnSave;
    private Button btnDonate;

    private Spinner sprGraphicsQuality;
    private Spinner sprResolution;
    private Spinner sprColor;
    private Spinner sprFps;
    private Spinner sprShadowQuality;
    private Spinner sprShadowDistance;
    private Spinner sprRender;
    private Spinner sprMsaa;
    private Spinner sprLightFx;
    private Spinner sprVersion;
    private Spinner sprGraphApi;
    private Spinner sprTextureQuality;

    private InterstitialAd mInterstitialAd;

    private Boolean ConfigApplied = false;

    private String adUnitIDInterstitial = "ca-app-pub-6773163192158273/8243915828";

    FragmentManager fm = getSupportFragmentManager();

    public String strUserQuality = "49",
            strFps = "4A49",
            strScaleFactor = "49574E4C",
            strHdr = "49",
            strColorStyle = "48",
            strTonemapper = "49",
            strLightFx1 = "49", strLightFx2 = "49",
            strMsaa1 = "49", strMsaa2 = "49",
            strMaterialQuality = "49",
            strShadows1 = "48", strShadows2 = "4D", strShadows3 = "4B",
            strShadowDistance = "495748",
            strGraphApi1 = "48", strGraphApi2 = "48",
            strTextureQuality = "484949";

    private String strPubgmVersion = "com.tencent.ig";


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String file_path;
    private String config;


    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        btnBuild = findViewById(R.id.btnBuild);
        btnInfo = findViewById(R.id.btnInfo);
        btnRevert = findViewById(R.id.btnRevert);
        btnSave = findViewById(R.id.btnSave);
        btnDonate = findViewById(R.id.btnDonate);

        sprGraphicsQuality = findViewById(R.id.sprGraphicsQuality);
        sprFps = findViewById(R.id.sprFps);
        sprResolution = findViewById(R.id.sprResolution);
        sprColor = findViewById(R.id.sprColor);
        sprRender = findViewById(R.id.sprRender);
        sprShadowQuality = findViewById(R.id.sprShadowQuality);
        sprShadowDistance = findViewById(R.id.sprShadowDistance);
        sprMsaa = findViewById(R.id.sprMsaa);
        sprLightFx = findViewById(R.id.sprLightFx);
        sprVersion = findViewById(R.id.sprVersion);
        sprGraphApi = findViewById(R.id.sprGraphApi);
        sprTextureQuality = findViewById(R.id.sprTextureQuality);

        btnDonate.setPaintFlags(btnDonate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Toast.makeText(this, "Make sure to close the game before using this app.", Toast.LENGTH_LONG).show();

        if (!legitcheck.L(null).booleanValue()) {
            throw new RuntimeException("Fake BAGT detected, search JEDIAR STUDIOS on Playstore");
        }

        MobileAds.initialize(this,
                "ca-app-pub-6773163192158273~3926576548");

        //load ad
        loadInterstitialAd();
       /* ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!mInterstitialAd.isLoaded()) {
                            loadInterstitialAd();
                        }
                    }
                });

            }
        }, 30, 30, TimeUnit.SECONDS);*/


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                loadInterstitialAd();
            }
        });

        btnBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildConfig();
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), usefullinks.class);
                startActivity(i);
            }
        });
        btnRevert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revertConfig();
                ConfigApplied = false;
                btnBuild.setText("Apply");
                Toast.makeText(MainActivity.this, "Config reverted!", Toast.LENGTH_SHORT).show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveConfig();
                Toast.makeText(MainActivity.this, "Config saved!", Toast.LENGTH_SHORT).show();
            }
        });
        // Capture button clicks
        btnDonate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                donation_dialog dFragment = new donation_dialog();
                // Show DialogFragment
                dFragment.show(fm, "Dialog Fragment");

            }
        });


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.qualitysetting, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.fps, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.resolution, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.colorstyle, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.materialquality, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this,
                R.array.shadowquality, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this,
                R.array.shadowdistance, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(this,
                R.array.msaa, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(this,
                R.array.lightfx, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter10 = ArrayAdapter.createFromResource(this,
                R.array.pubgmversion, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(this,
                R.array.graphapi, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter12 = ArrayAdapter.createFromResource(this,
                R.array.texturequality, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        sprGraphicsQuality.setAdapter(adapter1);
        sprFps.setAdapter(adapter2);
        sprResolution.setAdapter(adapter3);
        sprColor.setAdapter(adapter4);
        sprRender.setAdapter(adapter5);
        sprShadowQuality.setAdapter(adapter6);
        sprShadowDistance.setAdapter(adapter7);
        sprMsaa.setAdapter(adapter8);
        sprLightFx.setAdapter(adapter9);
        sprVersion.setAdapter(adapter10);
        sprGraphApi.setAdapter(adapter11);
        sprTextureQuality.setAdapter(adapter12);

        revertConfig();

        sprGraphicsQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprGraphicsQuality.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Smooth":
                        strUserQuality = "49";
                        strHdr = "49";
                        sprRender.setSelection(0);
                        sprLightFx.setSelection(0);
                        sprRender.setEnabled(false);
                        sprLightFx.setEnabled(false);
                        break;
                    case "Balanced":
                        strUserQuality = "48";
                        strHdr = "49";
                        sprRender.setSelection(1);
                        sprRender.setEnabled(true);
                        sprLightFx.setEnabled(true);
                        break;
                    case "HD":
                        strUserQuality = "4B";
                        strHdr = "49";
                        sprRender.setSelection(2);
                        sprRender.setEnabled(true);
                        sprLightFx.setEnabled(true);
                        break;
                    case "HDR":
                        strUserQuality = "4B";
                        strHdr = "48";
                        sprRender.setSelection(2);
                        sprRender.setEnabled(true);
                        sprLightFx.setEnabled(true);
                        break;

                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprFps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprFps.getSelectedItem().toString();

                switch (selectedItem) {
                    case "30fps":
                        strFps = "4A49";
                        break;
                    case "40fps":
                        strFps = "4D49";
                        break;
                    case "60fps":
                        strFps = "4F49";
                        break;

                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprResolution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprResolution.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Low":
                        strScaleFactor = "49574E4C";
                        break;
                    case "Medium":
                        strScaleFactor = "4957414F";
                        break;
                    case "High":
                        strScaleFactor = "48";
                        break;
                    case "Ultra (Tablets)":
                        strScaleFactor = "48574C";
                        break;

                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprColor.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Classic":
                        strColorStyle = "48";
                        strTonemapper = "49";
                        break;
                    case "Colorful":
                        strColorStyle = "4B";
                        strTonemapper = "49";
                        break;
                    case "Realistic":
                        strColorStyle = "4A";
                        strTonemapper = "48";
                        break;
                    case "Soft":
                        strColorStyle = "4D";
                        strTonemapper = "49";
                        break;
                    case "Film":
                        if (sprVersion.getSelectedItemPosition() == 1 || sprVersion.getSelectedItemPosition() == 3) {
                            strColorStyle = "4F";
                            strTonemapper = "48";
                            break;
                        } else {
                            sprColor.setSelection(0);
                            Toast.makeText(getApplicationContext(), "Film Color style is only available for 0.10.9 Ch and 0.9.0 En beta", Toast.LENGTH_SHORT).show();
                        }

                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprRender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprRender.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Low":
                        strMaterialQuality = "49";
                        sprShadowQuality.setSelection(0);
                        sprShadowQuality.setEnabled(false);
                        break;
                    case "Medium":
                        strMaterialQuality = "4B";
                        sprShadowQuality.setEnabled(true);
                        break;
                    case "High":
                        strMaterialQuality = "48";
                        sprShadowQuality.setEnabled(true);
                        break;


                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprShadowQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprShadowQuality.getSelectedItem().toString();

                switch (selectedItem) {
                    case "None":
                        strShadows1 = "49";
                        strShadows2 = "4D";
                        strShadows3 = "4B";
                        strShadowDistance = "49";
                        sprShadowDistance.setEnabled(false);
                        break;
                    case "Low":
                        strShadows1 = "48";
                        strShadows2 = "484B41";
                        strShadows3 = "4B";
                        if (!sprShadowDistance.isEnabled()) {
                            sprShadowDistance.setSelection(0);
                            sprShadowDistance.setEnabled(true);
                        }
                        break;
                    case "Medium":
                        strShadows1 = "48";
                        strShadows2 = "4C484B";
                        strShadows3 = "4B";
                        if (!sprShadowDistance.isEnabled()) {
                            sprShadowDistance.setSelection(0);
                            sprShadowDistance.setEnabled(true);
                        }
                        break;
                    case "High":
                        strShadows1 = "48";
                        strShadows2 = "48494B4D";
                        strShadows3 = "4B";
                        if (!sprShadowDistance.isEnabled()) {
                            sprShadowDistance.setSelection(0);
                            sprShadowDistance.setEnabled(true);
                        }
                        break;
                    case "Ultra":
                        strShadows1 = "48";
                        strShadows2 = "4B494D41";
                        strShadows3 = "4B";
                        if (!sprShadowDistance.isEnabled()) {
                            sprShadowDistance.setSelection(0);
                            sprShadowDistance.setEnabled(true);
                        }
                        break;

                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprShadowDistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprShadowDistance.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Low":
                        strShadowDistance = "495748";
                        break;
                    case "Medium":
                        strShadowDistance = "49574A";
                        break;
                    case "High":
                        strShadowDistance = "49574E";
                        break;
                    case "Ultra":
                        strShadowDistance = "48";
                        break;
                    case "Extreme":
                        strShadowDistance = "4B";
                        break;

                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprMsaa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprMsaa.getSelectedItem().toString();

                switch (selectedItem) {
                    case "None":
                        strMsaa1 = "49";
                        strMsaa2 = "49";
                        break;
                    case "1x":
                        strMsaa1 = "48";
                        strMsaa2 = "48";
                        break;
                    case "2x":
                        strMsaa1 = "48";
                        strMsaa2 = "4B";
                        break;
                    case "4x":
                        strMsaa1 = "48";
                        strMsaa2 = "4D";
                        break;


                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprLightFx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprLightFx.getSelectedItem().toString();

                switch (selectedItem) {
                    case "None":
                        strLightFx1 = "49";
                        strLightFx2 = "49";
                        break;
                    case "Low":
                        strLightFx1 = "48";
                        strLightFx2 = "48";
                        break;
                    case "Medium":
                        strLightFx1 = "4B";
                        strLightFx2 = "48";
                        break;
                    case "High":
                        strLightFx1 = "4A";
                        strLightFx2 = "48";
                        break;


                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprVersion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                int selectedItem = sprVersion.getSelectedItemPosition();

                switch (selectedItem) {

                    case 0:
                        strPubgmVersion = "com.tencent.ig";

                        if (sprColor.getSelectedItemPosition() == 4) {
                            sprColor.setSelection(0);
                            Toast.makeText(getApplicationContext(), "Film Color style is only available for 0.10.9 Ch and 0.9.0 En beta", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 1:
                        strPubgmVersion = "com.tencent.tmgp.pubgmhd";
                       break;

                    case 2:
                        strPubgmVersion = "com.pubg.krmobile";

                        if (sprColor.getSelectedItemPosition() == 4) {
                            sprColor.setSelection(0);
                            Toast.makeText(getApplicationContext(), "Film Color style is only available for 0.10.9 Ch and 0.9.0 En beta", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 3:
                        strPubgmVersion = "com.tencent.igce";
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprGraphApi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = sprGraphApi.getSelectedItem().toString();
                String androidVersion = Build.VERSION.RELEASE;
                int androidOS = Integer.parseInt(androidVersion.substring(0, 1));
                switch (selectedItem) {
                    case "Default":
                        break;
                    case "OpenGL 2.0":
                        strGraphApi1 = "48";
                        strGraphApi2 = "48";
                        break;
                    case "OpenGL 3.1":
                        if (androidOS >= 5) {
                            strGraphApi1 = "49";
                            strGraphApi2 = "48";
                        } else {
                            sprGraphApi.setSelection(0);
                            Toast.makeText(getApplicationContext(), "Your android version does not support this option.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Vulkan":
                        if (androidOS >= 7) {
                            strGraphApi1 = "48";
                            strGraphApi2 = "49";
                        } else {
                            sprGraphApi.setSelection(0);
                            Toast.makeText(getApplicationContext(), "Your android version does not support this option.", Toast.LENGTH_SHORT).show();
                        }
                        break;


                }

                Log.e("Selected item : ", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprTextureQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                int selectedItem = sprTextureQuality.getSelectedItemPosition();

                switch (selectedItem) {

                    case 0:
                        strTextureQuality = "484949";
                        break;

                    case 1:
                        strTextureQuality = "4B4C49";
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


    }

    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(adUnitIDInterstitial);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void writeToFile() {
        if (checkPermission()) {
            String getString = config;
            if (!file_path.isEmpty() && !getString.isEmpty() && legitcheck.L(null).booleanValue()) {
                try {
                    FileOutputStream overWrite = new FileOutputStream(file_path, false);
                    overWrite.write(getString.getBytes());
                    overWrite.flush();
                    overWrite.close();
                    //  Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.i("PUBGMCT", e.toString());
                }
            }
        } else {
            newAppDetailsIntent(this,this.getPackageName());
            Toast.makeText(getApplicationContext(), "Please allow storage permission first", Toast.LENGTH_LONG).show();
        }
    }

   /*public void verifyStoragePermissions(Activity activity) {

            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            }

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {

                requestPermission(); // Code for permission
            }
        } else {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }*/

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermission();
                return false;
            }
        }
        return true;
    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getApplicationContext(), "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void newAppDetailsIntent(Context context, String packageName) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        startActivity(intent);
    }

    private void buildConfig() {
        if (checkPermission()) {
            if (!ConfigApplied && legitcheck.L(null).booleanValue()) {
                String defaults;
                if (sprVersion.getSelectedItemPosition() == 0 || sprVersion.getSelectedItemPosition() == 2) {
                    defaults = FR.C(R.raw.config_defaults_enkr, this);
                } else {
                    defaults = FR.C(R.raw.config_defaults_ch, this);
                }

                String userquality = "+CVars=0B572C0A1C0B280C1815100D002A1C0D0D10171E44" + strUserQuality;
                String fps = "\n+CVars=0B57292C3B3E3D1C0F101A1C3F292A35160E44" + strFps +
                        "\n+CVars=0B57292C3B3E3D1C0F101A1C3F292A34101D44" + strFps +
                        "\n+CVars=0B57292C3B3E3D1C0F101A1C3F292A31101E1144" + strFps +
                        "\n+CVars=0B57292C3B3E3D1C0F101A1C3F292A313D2B44" + strFps;
                String scalefactor = "\n+CVars=0B5734161B10151C3A16170D1C170D2A1A18151C3F181A0D160B44" + strScaleFactor;

                String hdr = "\n+CVars=0B5734161B10151C313D2B44" + strHdr +
                        "\n+CVars=0B57292C3B3E353D2B44" + strHdr;

                String colorstyle = "\n+CVars=0B572C0A1C0B313D2B2A1C0D0D10171E44" + strColorStyle +
                        "\n+CVars=0B57383A3C2A2A0D00151C44" + strColorStyle;
                String tonemapper = "\n+CVars=0B5734161B10151C572D16171C141809091C0B3F10151444" + strTonemapper;
                String lighting = "\n+CVars=0B573B15161614280C1815100D0044" + strLightFx1 +
                        "\n+CVars=0B5735101E110D2A11181F0D280C1815100D0044" + strLightFx2;
                String msaa = "\n+CVars=0B572C0A1C0B342A38382A1C0D0D10171E44" + strMsaa1 +
                        "\n+CVars=0B5734161B10151C342A383844" + strMsaa2;
                String materialquality = "\n+CVars=0B5734180D1C0B101815280C1815100D00351C0F1C1544" + strMaterialQuality;
                String shadows = "\n+CVars=0B572A11181D160E280C1815100D0044" + strShadows1 +
                        "\n+CVars=0B572A11181D160E573418013A2A342B1C0A16150C0D10161744" + strShadows2 +
                        "\n+CVars=0B572A11181D160E573A2A345734180134161B10151C3A180A1A181D1C0A44" + strShadows3;

                if (sprVersion.getSelectedItemPosition() == 1 || sprVersion.getSelectedItemPosition() == 3){
                    shadows = "\n+CVars=0B572C0A1C0B2A11181D160E2A0E100D1A1144" + strShadows1 +
                            "\n+CVars=0B572A11181D160E280C1815100D0044" + strShadows1 +
                            "\n+CVars=0B572A11181D160E573418013A2A342B1C0A16150C0D10161744" + strShadows2 +
                            "\n+CVars=0B572A11181D160E573A2A345734180134161B10151C3A180A1A181D1C0A44" + strShadows3;
                }

                String shadowdistance = "\n+CVars=0B572A11181D160E573D100A0D18171A1C2A1A18151C44" + strShadowDistance;

                String texturequality = "\n+CVars=0B572A0D0B1C181410171E57291616152A10031C44" + strTextureQuality;

                String builtConfig = "";

                if (sprVersion.getSelectedItemPosition() == 0 || sprVersion.getSelectedItemPosition() == 2) {
                    builtConfig = defaults +
                            userquality +
                            fps +
                            scalefactor +
                            hdr +
                            colorstyle +
                            tonemapper +
                            lighting +
                            msaa +
                            materialquality +
                            shadows +
                            shadowdistance +
                            texturequality;
                } else if (sprVersion.getSelectedItemPosition() == 1 || sprVersion.getSelectedItemPosition() == 3) {
                    builtConfig = defaults +
                            userquality +
                            fps +
                            scalefactor +
                            hdr +
                            colorstyle +
                            tonemapper +
                            lighting +
                            msaa +
                            materialquality +
                            shadows +
                            shadowdistance +
                            texturequality;
                }
                File dir = Environment.getExternalStorageDirectory();
                String path = dir.getAbsolutePath();
                String config_path;
                config_path = path + "/Android/data/" + strPubgmVersion + "/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/config/Android/UserCustom.ini";
                file_path = config_path;
                String fdata = FR.D(config_path, this);
                config = builtConfig + "\n\n" + fdata;
                writeToFile();
                saveConfig();
                killAppBypackage(strPubgmVersion);


                SharedPreferences prefs = this.getSharedPreferences(
                        getPackageName(), Context.MODE_PRIVATE);
                if ((prefs.getInt("runGameCrashed", 0) == 0)) {
                    Toast.makeText(this, "Config Applied!", Toast.LENGTH_SHORT).show();
                    ConfigApplied = true;
                    btnBuild.setText("Run Game");
                } else {
                    Toast.makeText(this, "Config Applied! Please run the game manually.", Toast.LENGTH_LONG).show();
                }
                showInterstitialAd();
            } else {
                runGame();
                ConfigApplied = false;
                btnBuild.setText("Apply");
            }
        }else{
            newAppDetailsIntent(this,this.getPackageName());
            Toast.makeText(getApplicationContext(), "Please allow storage permission first", Toast.LENGTH_LONG).show();
        }
    }

    private void runGame() {
        try {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(strPubgmVersion);
            startActivity(launchIntent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(strPubgmVersion);
                startActivity(intent);
                System.out.println("Error: " + e.getMessage());
            } catch (Exception d) {
                SharedPreferences prefs = this.getSharedPreferences(
                        getPackageName(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("runGameCrashed", 1);
                Toast.makeText(getApplicationContext(), "Run Game didn't work, please run the game manually.", Toast.LENGTH_LONG).show();
                System.out.println("Error: " + d.getMessage());
            }
        }
    }

    private void killAppBypackage(String packageTokill) {

        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = getPackageManager();
        //get a list of installed apps.
        packages = pm.getInstalledApplications(0);


        ActivityManager mActivityManager = (ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
        String myPackage = getApplicationContext().getPackageName();

        for (ApplicationInfo packageInfo : packages) {

            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                continue;
            }
            if (packageInfo.packageName.equals(myPackage)) {
                continue;
            }
            if (packageInfo.packageName.equals(packageTokill)) {
                mActivityManager.killBackgroundProcesses(packageInfo.packageName);
            }

        }

    }


    private void showInterstitialAd() {


        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!mInterstitialAd.isLoaded()) loadInterstitialAd();
    }

    private void saveConfig() {
        SharedPreferences prefs = this.getSharedPreferences(
                getPackageName(), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("sprGraphicsQuality", sprGraphicsQuality.getSelectedItemPosition());
        editor.putInt("sprFps", sprFps.getSelectedItemPosition());
        editor.putInt("sprResolution", sprResolution.getSelectedItemPosition());
        editor.putInt("sprColor", sprColor.getSelectedItemPosition());
        editor.putInt("sprRender", sprRender.getSelectedItemPosition());
        editor.putInt("sprShadowQuality", sprShadowQuality.getSelectedItemPosition());
        editor.putInt("sprShadowDistance", sprShadowDistance.getSelectedItemPosition());
        editor.putInt("sprMsaa", sprMsaa.getSelectedItemPosition());
        editor.putInt("sprLightFx", sprLightFx.getSelectedItemPosition());
        editor.putInt("sprGraphApi", sprGraphApi.getSelectedItemPosition());
        editor.putInt("sprTextureQuality", sprTextureQuality.getSelectedItemPosition());
        editor.putInt("sprVersion", sprVersion.getSelectedItemPosition());
        editor.commit();


    }

    private void revertConfig() {
        SharedPreferences prefs = this.getSharedPreferences(
                getPackageName(), Context.MODE_PRIVATE);

        sprGraphicsQuality.setSelection(prefs.getInt("sprGraphicsQuality", 0));
        sprFps.setSelection(prefs.getInt("sprFps", 0));
        sprResolution.setSelection(prefs.getInt("sprResolution", 0));
        sprColor.setSelection(prefs.getInt("sprColor", 0));
        sprRender.setSelection(prefs.getInt("sprRender", 0));
        sprShadowQuality.setSelection(prefs.getInt("sprShadowQuality", 0));
        sprShadowDistance.setSelection(prefs.getInt("sprShadowDistance", 0));
        sprMsaa.setSelection(prefs.getInt("sprMsaa", 0));
        sprLightFx.setSelection(prefs.getInt("sprLightFx", 0));
        sprGraphApi.setSelection(prefs.getInt("sprGraphApi", 0));
        sprTextureQuality.setSelection(prefs.getInt("sprTextureQuality", 0));
        sprVersion.setSelection(prefs.getInt("sprVersion", 0));


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
