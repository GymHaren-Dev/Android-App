package gymnasium_haren.de.schule;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * Created by TErK on 31.01.2016.
 */
public class GetWebString extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *  Used to name the worker thread, important only for debugging.
     */
    public GetWebString() {
        super("GetWebString");
    }

    static String getStrFromUrl (String surl) {
        final String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.8.1.12) Gecko/20080201 Firefox/2.0.0.12";
        String str = null;
        try {
            URL url = new URL(surl);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", userAgent);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str1;
            str = new String();
            while ((str1 = in.readLine()) != null) {
                str = str + str1;
            }
            in.close();
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return str;
    }

    int notID = 1;
    String content1 = null;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onHandleIntent(Intent intent) {

        Uri data = intent.getData();
        Pattern p = Pattern.compile(",");

        CharSequence s = data.toString();   //Zeile aus Textdatei wird eingelesen
        final String[] t=p.split(s);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(t[1].toString(), t[2].toCharArray());
            }
        });

        while (true) {
            synchronized (this) {
                try {


                    String htmlPage = getStrFromUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/f2/subst_001.htm");
                    int divIndex = htmlPage.indexOf("<div class=\"mon_title\"");
                    divIndex = htmlPage.indexOf(">", divIndex);

                    int endDivIndex = htmlPage.indexOf("</div>", divIndex);
                    String content = htmlPage.substring(divIndex + 1, endDivIndex);

                    if (getStrFromUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/f2/subst_001.htm").contains(t[0].toString())) {
                        if (!Objects.equals(content, content1)) {
                            NotificationCompat.Builder notBuilder = new
                                    NotificationCompat.Builder(this)
                                    .setContentTitle("Vertretung")
                                    .setContentText("Deine Klasse " + t[0].toString() + " hat Vertretung am " + content)
                                    .setTicker("Vertretung")
                                    .setSmallIcon(R.drawable.ic_drawer);
                            NotificationManager notManag = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
                            notBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
                            notBuilder.setAutoCancel(true);
                            notBuilder.build().flags |= Notification.FLAG_ONLY_ALERT_ONCE;
                            notManag.notify(notID, notBuilder.build());
                            content1 = htmlPage.substring(divIndex + 1, endDivIndex);
                        }

                    }else {
                        NotificationManager notManag = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
                        notManag.cancel(notID);
                    }sleep(100000);
                }catch(Exception e){
                    }
                }
           }

    }
}