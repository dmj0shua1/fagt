package jediarstudios.fagt;

public class legitcheck {

    public static Boolean L(Boolean isAppLegit){


        String packagename = BuildConfig.APPLICATION_ID;
        if (packagename.contentEquals("jediarstudios.fagt")){
         isAppLegit = true;
        }else{
            isAppLegit = false;
        }

        return isAppLegit;
    }
}
