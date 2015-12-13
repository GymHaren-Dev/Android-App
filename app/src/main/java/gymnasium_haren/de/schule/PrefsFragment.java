package gymnasium_haren.de.schule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import java.util.MissingResourceException;

/**
 * Created by Erik on 12.12.2015.
 */
public class PrefsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void onActivityCreated(){

        // Load the preferences from an XML resource

        /**classPref.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
        userPref.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
        passPref.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);*/

    }
    protected boolean onPostExecute(Void result){
        if(isAdded()){
            return true;
        }
        return false;
    }
}