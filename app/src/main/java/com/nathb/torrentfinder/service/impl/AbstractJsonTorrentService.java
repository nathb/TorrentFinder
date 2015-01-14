package com.nathb.torrentfinder.service.impl;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.service.TorrentService;
import com.nathb.torrentfinder.service.exception.TorrentResponseException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class AbstractJsonTorrentService implements TorrentService {

    protected abstract String buildUrl(Show show, Episode episode);
    protected abstract List<Torrent> parseResponse(JSONObject jsonResponse) throws JSONException;

    @Override
    public List<Torrent> getTorrents(Show show, Episode episode) throws TorrentResponseException {
        final String url = buildUrl(show, episode);
        final Request request = new Request.Builder().url(url).build();

        try {
            final Response response = new OkHttpClient().newCall(request).execute();
            final JSONObject jsonResponse = new JSONObject(response.body().string());
            return parseResponse(jsonResponse);
        } catch (Exception e) {
            throw new TorrentResponseException(e);
        }
    }
}
