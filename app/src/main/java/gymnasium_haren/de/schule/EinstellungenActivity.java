package gymnasium_haren.de.schule;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class EinstellungenActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefsFragment test = new PrefsFragment();
        addPreferencesFromResource(R.xml.preferences);
        Preference classPref = findPreference(getString(R.string.preference_class_key));
        Preference userPref = findPreference(getString(R.string.preference_benutzer_key));
        Preference passPref = findPreference(getString(R.string.preference_pass_key));
        classPref.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
        userPref.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
        passPref.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
        // onPreferenceChange sofort aufrufen mit der in SharedPreferences gespeicherten Klasse
        /**SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String gespeicherteKlasse = sharedPrefs.getString(test.classPref.getKey(), "");
        onPreferenceChange(test.classPref, gespeicherteKlasse);
        String gespeicherterBenutzer = sharedPrefs.getString(test.userPref.getKey(), "");
        onPreferenceChange(test.userPref, gespeicherterBenutzer);
        String gespeichertesPasswort = sharedPrefs.getString(test.passPref.getKey(), "");
        onPreferenceChange(test.passPref, gespeichertesPasswort);*/
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String gespeicherteKlasse = sharedPrefs.getString(classPref.getKey(), "");
        onPreferenceChange(classPref, gespeicherteKlasse);
        String gespeicherterBenutzer = sharedPrefs.getString(userPref.getKey(), "");
        onPreferenceChange(userPref, gespeicherterBenutzer);
        String gespeichertesPasswort = sharedPrefs.getString(passPref.getKey(), "");
        onPreferenceChange(passPref, gespeichertesPasswort);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        preference.setSummary(value.toString());

        return true;
    }
    protected void onPostExecute(Void result) {
        // do whatever you do to save data
        PrefsFragment test = new PrefsFragment();
        if (test.getView() != null) {
            // update views
        }
    }
}
/**class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}*/
