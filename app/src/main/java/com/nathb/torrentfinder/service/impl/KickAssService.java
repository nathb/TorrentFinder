package com.nathb.torrentfinder.service.impl;

import android.net.Uri;
import android.util.Log;

import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.util.FormatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KickAssService extends AbstractJsonTorrentService {

    private static final String BASE_URL = "https://kickass.so/json.php";

    @Override
    protected String buildUrl(String title, int seasonNumber, int episodeNumber) {
        final StringBuilder searchTitle = new StringBuilder(title)
                .append(" S").append(FormatUtil.getFormattedNumber(seasonNumber))
                .append("E").append(FormatUtil.getFormattedNumber(episodeNumber));

        final Uri uri = new Uri.Builder()
                .encodedPath(BASE_URL)
                .appendQueryParameter("q", searchTitle.toString())
                .appendQueryParameter("field", "seeders")
                .appendQueryParameter("order", "desc")
                .build();

        return uri.toString();
    }

    @Override
    protected List<Torrent> parseResponse(JSONObject jsonResponse) throws JSONException {
        List<Torrent> torrents = new ArrayList<Torrent>();
        final int totalResults = jsonResponse.getInt("total_results");
        if (totalResults < LIMIT) {
            return  torrents;
        }

        final JSONArray list = jsonResponse.getJSONArray("list");
        for (int i = 0; i < LIMIT; i++) {
            final JSONObject jsonTorrent = list.getJSONObject(i);
            torrents.add(new Torrent(
                jsonTorrent.getString("title"),
                jsonTorrent.getString("pubDate"),
                jsonTorrent.getString("size"),
                "", // No user name available
                jsonTorrent.getString("torrentLink"),
                jsonTorrent.getInt("seeds"),
                jsonTorrent.getInt("leechs")
            ));
        }
        return torrents;
    }
}
