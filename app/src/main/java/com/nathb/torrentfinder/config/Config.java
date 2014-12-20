package com.nathb.torrentfinder.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.service.factory.TorrentServiceFactory.TorrentServiceType;
import com.nathb.torrentfinder.service.impl.ThePirateBayService;

public class Config {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static String getKey(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String getEmailAddress(Context context) {
        final String key = getKey(context, R.string.key_email_address);
        return getSharedPreferences(context).getString(key, "");
    }

    public static boolean getSingleEpisodePreference(Context context) {
        final String key = getKey(context, R.string.key_single_episode);
        return getSharedPreferences(context).getBoolean(key, true);
    }

    public static TorrentServiceType getTorrentServiceType(Context context) {
        final String key = getKey(context, R.string.key_torrent_service);
        final String value = getSharedPreferences(context).getString(key, null);

        TorrentServiceType type = null;
        if (value != null) {
            type = TorrentServiceType.valueOf(value);
        }

        return type != null ? type : TorrentServiceType.ThePirateBay;
    }

    public static String getThePirateBayUrl(Context context) {
        final String key = getKey(context, R.string.key_the_pirate_bay_url);
        return getSharedPreferences(context).getString(key, ThePirateBayService.DEFAULT_URL);
    }
}
