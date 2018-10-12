package jediarstudios.fagt;

import android.util.Log;

import com.google.ads.AdRequest.ErrorCode;
import com.google.android.gms.ads.AdListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public abstract class AdBlockerListener extends AdListener {

    public abstract void onAdBlocked();

    public boolean shouldCheckAdBlock(){
        return true;
    }

    public void onReceiveAd() {
    }
    public static boolean isAdBlockerPresent(boolean showAd) {
        if (showAd){
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        new FileInputStream("/etc/hosts")));
                String line;

                while ((line = in.readLine()) != null) {
                    if (line.contains("admob")) {
                        return true;
                    }
                }
            } catch (Exception e) {
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return false;
    }
    public void onFailedToReceiveAd(final ErrorCode errorCode) {
        Log.e("AdBlockerListener", "Failed to receive ad, checking if network blocker is installed...");
        if (isAdBlockerPresent(shouldCheckAdBlock())) {
            Log.e("AdBlockerListener", "Ad blocking seems enabled, tracking attempt");
            onAdBlocked();
        } else {
            Log.e("AdBlockerListener", "No ad blocking detected, silently fails");
        }
    }



}