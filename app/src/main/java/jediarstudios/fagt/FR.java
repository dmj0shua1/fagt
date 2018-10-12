package jediarstudios.fagt;
import java.io.*;
import android.content.*;

public class FR
	{

    public static String D(String filePath, Context ctx) {
        StringBuffer datax = new StringBuffer("");
        try {
			File file = new File(filePath);
			FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader buffreader = new BufferedReader(isr);
            String readString;
            Boolean backupDetected = false;
            while ((readString = buffreader.readLine()) != null ){

                if (readString.contentEquals("[BackUp DeviceProfile]")) backupDetected = true;
                if (backupDetected && readString.contentEquals("[UserCustom DeviceProfile]")) backupDetected = false;
                if (backupDetected){
                    datax.append(readString);
                    datax.append("\n");

                }


            }

            isr.close();
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
			return "";
        }
        return datax.toString();
	}

        public static String C(int rawfile,Context ctx) {
            StringBuffer datax = new StringBuffer("");
            try {
                InputStream inputStream = ctx.getResources().openRawResource(rawfile);
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(isr);
                String readString;
                while ((readString = buffreader.readLine()) != null ){

                        datax.append(readString);
                        datax.append("\n");

                }

                isr.close();
            } catch ( IOException ioe ) {
                ioe.printStackTrace ( ) ;
                return "";
            }
            return datax.toString();
        }


	
	
}
