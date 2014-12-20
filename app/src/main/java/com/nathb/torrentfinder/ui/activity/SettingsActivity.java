package com.nathb.torrentfinder.ui.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.service.factory.TorrentServiceFactory.TorrentServiceType;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            addTorrentServicesListPreference();
        }

        private void addTorrentServicesListPreference() {
            final String torrentServiceKey = getString(R.string.key_torrent_service);
            final ListPreference listPreference = (ListPreference) findPreference(torrentServiceKey);
            final TorrentServiceType[] types = TorrentServiceType.values();
            final CharSequence[] typeStrings = new CharSequence[types.length];
            for (int i = 0; i < types.length; i++) {
                typeStrings[i] = types[i].toString();
            }
            listPreference.setEntries(typeStrings);
            listPreference.setEntryValues(typeStrings);
        }
    }

}
