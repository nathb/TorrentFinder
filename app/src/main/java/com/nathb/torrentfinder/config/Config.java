package com.nathb.torrentfinder.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.service.TorrentService;
import com.nathb.torrentfinder.service.factory.EpisodeListServiceFactory.EpisodeListServiceType;
import com.nathb.torrentfinder.service.factory.TorrentServiceFactory.TorrentServiceType;
import com.nathb.torrentfinder.service.impl.KickAssService;
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

    public static EpisodeListServiceType getEpisodeListServiceType(Context context) {
        final String key = getKey(context, R.string.key_episode_list_service);
        final String value = getSharedPreferences(context).getString(key, null);

        EpisodeListServiceType type = null;
        if (value != null) {
            type = EpisodeListServiceType.valueOf(value);
        }

        return type != null ? type : EpisodeListServiceType.EpGuidesTVMaze;
    }

    public static String getThePirateBayUrl(Context context) {
        final String key = getKey(context, R.string.key_the_pirate_bay_url);
        return getString(context, key, ThePirateBayService.DEFAULT_URL);
    }

    public static String getKickassUrl(Context context) {
        final String key = getKey(context, R.string.key_kickass_url);
        return getString(context, key, KickAssService.DEFAULT_URL);
    }

    private static String getString(Context context, String key, String defaultValue) {
        final String value = getSharedPreferences(context).getString(key, defaultValue);
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }

    public static int getTorrentResultLimit(Context context) {
        final String key = getKey(context, R.string.key_torrent_result_limit);
        return getInt(context, key, TorrentService.DEFAULT_LIMIT);
    }

    private static Integer getInt(Context context, String key, int defaultValue) {
        final String stringValue = getSharedPreferences(context).getString(key, "");
        if (TextUtils.isEmpty(stringValue)) {
            return defaultValue;
        }

        Integer intValue = null;
        try {
            intValue = Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
        }

        return intValue != null ? intValue : defaultValue;
    }
}
