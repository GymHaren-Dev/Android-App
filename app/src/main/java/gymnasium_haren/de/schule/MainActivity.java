package gymnasium_haren.de.schule;

import android.app.Activity;
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
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pushbots.push.Pushbots;


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
        WebView.setWebViewClient(new MyWebViewClient());
        WebView.getSettings().setBuiltInZoomControls(true);
        WebView.setInitialScale(100);
        Pushbots.sharedInstance().init(this);
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

        switch (number) {
            case 1:
                mTitle = "Vertretungsplan";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/vertretungsplan/Schueler/subst_001.htm");
                break;
            case 2:
                mTitle = "Stundenplan";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/stundenplan/Klassenplan/default.htm");
                break;
            case 3:
                mTitle = "Schulausfälle";
                WebView.loadUrl("http://www.vmz-niedersachsen.de/aktuell/schulausfall.php");
                break;
            case 4:
                mTitle = "Termine";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/termine/termine.htm");
                break;
            case 5:
                mTitle = "Verwaltung";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/verwaltung/verwaltung.htm");
                break;
            case 6:
                mTitle = "Pressespiegel";
                WebView.loadUrl("https://gymharen.wordpress.com/");
                break;
            case 7:
                mTitle = "Fächer";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Faecher/faecher-start.htm");
                break;
            case 8:
                mTitle = "Soziales Lernen";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/soziales_lernen/soziales_lernen.htm");
                break;
            case 9:
                mTitle = "AGs";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/arbeitsgemeinschaften/arbeitsgemeinschaften.htm");
                break;
            case 10:
                mTitle = "Schüler";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Schueler/startseite/aktuelles.htm");
                break;
            case 11:
                mTitle = "Berufs- und Studienorientierung";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/berufs-und_studienorientierung/start.htm");
                break;
            case 12:
                mTitle = "Eltern";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Eltern/eltern.htm");
                break;
            case 13:
                mTitle = "Grundschuleltern";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/eltern_grundschule/eltern_grundschule.htm");
                break;
            case 14:
                mTitle = "Kollegium";
                WebView.loadUrl("http://fs2.directupload.net/images/150629/fxph5jda.png");
                break;
            case 15:
                mTitle = "Partnerschulen";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/partnerschulen/partnerschulen.htm");
                break;
            case 16:
                mTitle = "Mensa";
                WebView.loadUrl("https://d.maxfile.ro/mdumuxrjup.html");
                break;
            case 17:
                mTitle = "Förderverein";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/foerderverein/foerderverein.htm");
                break;
            case 18:
                mTitle = "Links";
                WebView.loadUrl("http://www.nibis.ni.schule.de/~gymharen/Links/links.htm");
                break;
            case 19:
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

}
class MyWebViewClient extends WebViewClient {
    @Override
    public void onReceivedHttpAuthRequest(WebView view,
                                          HttpAuthHandler handler, String host, String realm) {

        handler.proceed("student", "sTu2411");

    }
}
