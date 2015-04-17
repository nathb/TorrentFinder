package com.nathb.torrentfinder.service.impl;

import android.content.Context;
import android.net.Uri;

import com.nathb.torrentfinder.config.Config;
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.util.FormatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KickAssService extends AbstractJsonTorrentService {

    public static final String DEFAULT_URL = "kickass.to";
    private static final String BASE_URL_FORMAT = "https://%s/json.php";

    private Context mContext;

    public KickAssService(Context context) {
        mContext = context;
    }

    @Override
    protected String buildUrl(Show show, Episode episode) {
        final String searchQuery = FormatUtil.getSearchQueryFormat(show, episode);
        final String baseUrl = String.format(BASE_URL_FORMAT, Config.getKickassUrl(mContext));
        final Uri uri = new Uri.Builder()
                .encodedPath(baseUrl)
                .appendQueryParameter("q", searchQuery)
                .appendQueryParameter("field", "seeders")
                .appendQueryParameter("order", "desc")
                .build();

        return uri.toString();
    }

    @Override
    protected List<Torrent> parseResponse(JSONObject jsonResponse) throws JSONException {
        List<Torrent> torrents = new ArrayList<Torrent>();
        final int totalResults = jsonResponse.getInt("total_results");
        final int resultLimit = Config.getTorrentResultLimit(mContext);
        if (totalResults < resultLimit) {
            return  torrents;
        }

        final JSONArray list = jsonResponse.getJSONArray("list");
        for (int i = 0; i < resultLimit; i++) {
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
