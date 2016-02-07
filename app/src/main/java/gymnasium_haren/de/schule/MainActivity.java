package gymnasium_haren.de.schule;
 import com.pushbots.push.Pushbots;

import android.app.Activity;
 import android.app.NotificationManager;
 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.net.Uri;
 import android.preference.PreferenceManager;
 import android.support.v4.app.NotificationCompat;
 import android.support.v4.view.ViewCompat;
 import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
 import android.view.Surface;
 import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.io.*;
 import java.net.*;
 import java.util.*;

 import static android.app.PendingIntent.getActivities;
 import static android.app.PendingIntent.getActivity;
 import static gymnasium_haren.de.schule.MainActivity.*;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private WebView WebView;
    public MyWebViewClient Webcl;

    NotificationManager notManag;
    int notID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        WebView = (WebView) findViewById(R.id.webView);
        // Enable JScript
        WebSettings webSettings = WebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //Weiterleitungen in App �ffnen
        Webcl =new MyWebViewClient();
        WebView.setWebViewClient(Webcl);
        Webcl.MyWebViewClient2(User(), Password());
        WebView.getSettings().setBuiltInZoomControls(true);
        float dpi = getResources().getDisplayMetrics().density;
        WebView.setInitialScale((int) dpi * 100);
        Pushbots.sharedInstance().init(this);
        WebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        /**test meinScanner = new test();
        String klasse = "5c";
        String datum = "20.11.";
        String quellcode = meinScanner.fetchPage("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/f2/subst_001.htm"); //Hier die Adresse eingeben und du hast den Quellcode in einer Variablen
        int anfang = quellcode.indexOf(klasse);
        int ende = quellcode.indexOf("</td><td class=");
        //Jetzt hole den String aus dem Quellcode
        String gesuchterText = quellcode.substring(anfang+94, ende);
        Context context = getApplicationContext();
        CharSequence text = "Die Klasse "+klasse+" hat in der "+gesuchterText+" Vertretung/Entfall.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
         */
        // Auslesen der ausgewählten Klasse aus den SharedPreferences
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        Pushbots.sharedInstance().tag(Klasse());
        Pushbots.sharedInstance().tag("Beta-3");
        Pushbots.sharedInstance().untag("Beta-2");
        Pushbots.sharedInstance().untag("Beta-1");
        Pushbots.sharedInstance().untag("dev");
        // Einstellung zum Ein- oder Ausschalten des Empfangens von Push-Nachrichten via Pushbots
        String prefPushKey = getString(R.string.preference_push_key);
        Boolean push = sPrefs.getBoolean(prefPushKey, true);
        if (push == true){
            Pushbots.sharedInstance().setPushEnabled(true);
        } if (push == false){
            Pushbots.sharedInstance().setPushEnabled(false);
        }
       /** String prefpushdKey = getString(R.string.preference_pushd_key);
        Boolean pushd = sPrefs.getBoolean(prefpushdKey, true);
        if (pushd == true){
            Pushbots.sharedInstance().tag("dev");
        } if (pushd == false){
            Pushbots.sharedInstance().untag("dev");
        }**/
        if (Password() == ""){
            Toast toast = Toast.makeText(this, "Bitte Einstellungen oben rechts überprüfen! Danach komplett beenden und neustarten!", Toast.LENGTH_SHORT);
            toast.show();
        }
        String aInfo = Klasse() + "," + User() + "," + Password();

        Intent intent = new Intent(this, GetWebString.class);
        intent.setData(Uri.parse(aInfo));
        startService(intent);
        //GetWebString.onHandleIntent();
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (WebView.canGoBack()) {
            WebView.goBack();
        } else {
            super.onBackPressed();
        }

    }

    public void onSectionAttached(int number) {



        if(Webcl != null){

            Webcl.MyWebViewClient2(User(), Password());
        }
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();

        switch (number) {
            case 1:
                mTitle = "Vertretungsplan";
                switch (rotation) {
                    case Surface.ROTATION_0:
                        if (!Modus()) {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/f2/subst_001.htm");
                        } else {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Lehrer/f2/subst_001.htm");
                        } break;
                    case Surface.ROTATION_90:
                        if (!Modus()) {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/subst_001.htm");
                        } else {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Lehrer/subst_001.htm");
                        } break;
                    case Surface.ROTATION_180:
                        if (!Modus()) {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/f2/subst_001.htm");
                        } else {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Lehrer/f2/subst_001.htm");
                        } break;
                    case Surface.ROTATION_270:
                        if (!Modus()) {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/subst_001.htm");
                        } else {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Lehrer/subst_001.htm");
                        } break;
                    default:
                        if (!Modus()) {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/subst_001.htm");
                        } else {
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Lehrer/subst_001.htm");
                        } break;
                }

                break;
            case 2:
                mTitle = "Stundenplan";
                if (!Modus()) {
                    switch(Klasse()){
                        case "5a":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00001.htm");
                            break;
                        case "5b":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00002.htm");
                            break;
                        case "5c":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00003.htm");
                            break;
                        case "5d":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00004.htm");
                            break;
                        case "6a":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00005.htm");
                            break;
                        case "6b":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00006.htm");
                            break;
                        case "6c":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00007.htm");
                            break;
                        case "7a":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00008.htm");
                            break;
                        case "7b":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00009.htm");
                            break;
                        case "7c":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00010.htm");
                            break;
                        case "7d":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00011.htm");
                            break;
                        case "8a":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00012.htm");
                            break;
                        case "8b":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00013.htm");
                            break;
                        case "8c":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00014.htm");
                            break;
                        case "8d":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00015.htm");
                            break;
                        case "9a":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00016.htm");
                            break;
                        case "9b":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00017.htm");
                            break;
                        case "9c":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00018.htm");
                            break;
                        case "9d":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00019.htm");
                            break;
                        case "10a":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00020.htm");
                            break;
                        case "10b":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00021.htm");
                            break;
                        case "10c":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00022.htm");
                            break;
                        case "10d":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00023.htm");
                            break;
                        case "11":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00024.htm");
                            break;
                        case "12a1":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00025.htm");
                            break;
                        case "12a2":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00026.htm");
                            break;
                        case "12b":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00027.htm");
                            break;
                        case "12c":
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/c/P3/c00028.htm");
                            break;
                        default:
                            WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/default.htm");
                            break;
                    }

                }else {
                    WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Lehrerplan/default.htm");
                }
                break;
            case 3:
                mTitle = "Schulausfälle";
                WebView.loadUrl("http://www.vmz-niedersachsen.de/aktuell/schulausfall.php");
                break;
            case 4:
                mTitle = "Hauptseite";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/home/willkommen.htm");
            case 5:
                mTitle = "Termine";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/termine/termine.htm");
                break;
            case 6:
                mTitle = "Verwaltung";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/verwaltung.htm");
                break;
            case 7:
                mTitle = "Pressespiegel";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/pressespiegel/15_16_pressespiegel.htm");
                break;
            case 8:
                mTitle = "Fächer";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Faecher/faecher-start.htm");
                break;
            case 9:
                mTitle = "Soziales Lernen";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/soziales_lernen/soziales_lernen.htm");
                break;
            case 10:
                mTitle = "AGs";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/arbeitsgemeinschaften/arbeitsgemeinschaften.htm");
                break;
            case 11:
                mTitle = "Schüler";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Schueler/startseite/aktuelles.htm");
                break;
            case 12:
                mTitle = "Berufs- und Studienorientierung";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/berufs-und_studienorientierung/start.htm");
                break;
            case 13:
                mTitle = "Eltern";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Eltern/eltern.htm");
                break;
            case 14:
                mTitle = "Grundschuleltern";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/eltern_grundschule/eltern_grundschule.htm");
                break;
            case 15:
                mTitle = "Kollegium";
                WebView.loadUrl("http://nibis.ni.schule.de/~gymharen/Kollegium/kollegium.htm");
                break;
            case 16:
                mTitle = "Partnerschulen";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/partnerschulen/partnerschulen.htm");
                break;
            case 17:
                mTitle = "Mensa";
                WebView.loadUrl("https://d.maxfile.ro/mdumuxrjup.html");
                break;
            case 18:
                mTitle = "Förderverein";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/foerderverein/foerderverein.htm");
                break;
            case 19:
                mTitle = "Links";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Links/links.htm");
                break;
            case 20:
                mTitle = "Über diese App";
                WebView.loadUrl("http://gymharen-dev.github.io/Android-App/");
                break;
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, EinstellungenActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }
    String Klasse(){
        SharedPreferences ksPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String prefKlasseKey = getString(R.string.preference_class_key);
        String prefKlasseDefault = getString(R.string.preference_class_default);
        String Klasse = ksPrefs.getString(prefKlasseKey, prefKlasseDefault);
        return Klasse;
    }
    String User(){
        SharedPreferences usPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String prefBenutzerKey = getString(R.string.preference_benutzer_key);
        String prefBenutzerDefault = getString(R.string.preference_benutzer_default);
        String Benutzer = usPrefs.getString(prefBenutzerKey, prefBenutzerDefault);
        return Benutzer;
    }
    String Password(){
        SharedPreferences psPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String prefPassKey = getString(R.string.preference_pass_key);
        String prefPassDefault = getString(R.string.preference_pass_default);
        String Pass = psPrefs.getString(prefPassKey, prefPassDefault);
        return Pass;
    }
    boolean Modus() {
        // Auslesen des Anzeige-Modus aus den SharedPreferences
        SharedPreferences msPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String prefLehrermodusKey = getString(R.string.preference_lehreremodus_key);
        Boolean lehrermodus = msPrefs.getBoolean(prefLehrermodusKey, false);
        return lehrermodus;

    }






}

class MyWebViewClient extends WebViewClient {
    public String Jorg;
    public String Pasjorg;
    public void MyWebViewClient2(String user, String password) {
        Jorg = user;
        Pasjorg =password;
    }

    //MainActivity test = new MainActivity();
    @Override
    public void onReceivedHttpAuthRequest(WebView view,
                                          HttpAuthHandler handler, String host, String realm) {

        handler.proceed(Jorg , Pasjorg);

    }

}

/**class test {
    public String fetchPage(String url) {
        StringBuilder sb = new StringBuilder();

        try {
            Scanner scanner = new Scanner(new URL(url).openStream());
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine() + "\n");

            }
            scanner.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    } // fetchPage
}*/