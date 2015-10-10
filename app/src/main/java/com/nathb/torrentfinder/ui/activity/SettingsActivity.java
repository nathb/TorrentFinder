package com.nathb.torrentfinder.ui.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.service.factory.EpisodeListServiceFactory.EpisodeListServiceType;
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
            generateListPreferenceFromEnum(R.string.key_torrent_service, TorrentServiceType.class);
            generateListPreferenceFromEnum(R.string.key_episode_list_service, EpisodeListServiceType.class);
        }

        private <E extends Enum <E>> void generateListPreferenceFromEnum(int listPreferenceKey, Class<E> enumType) {
            final String keyString = getString(listPreferenceKey);
            final ListPreference listPreference = (ListPreference) findPreference(keyString);
            final Enum[] types = enumType.getEnumConstants();
            final CharSequence[] typeStrings = new CharSequence[types.length];
            for (int i = 0; i < types.length; i++) {
                typeStrings[i] = types[i].toString();
            }
            listPreference.setEntries(typeStrings);
            listPreference.setEntryValues(typeStrings);
        }
    }

}
