package com.nathb.torrentfinder.service.impl;

import android.net.Uri;
import android.text.TextUtils;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.service.EpisodeListService;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractEpGuidesService implements EpisodeListService {

    private static final String BASE_URL = "http://epguides.com";

    protected abstract Document getDocument(String url) throws IOException;
    protected abstract Episode parseEpisode(Element element);

    @Override
    public List<Episode> getEpisodes(Show show) throws IOException {
        final String url = buildUrl(show.getEpisodeListSearchTerm());
        final Document document = getDocument(url);
        return parseResponse(document);
    }

    protected String buildUrl(String title) {
        return new Uri.Builder()
                .encodedPath(BASE_URL)
                .appendPath(title)
                .build()
                .toString() + "/";
    }

    protected List<Episode> parseResponse(Document document) {
        final List<Episode> episodes = new ArrayList<Episode>();
        final Elements result = document.select("pre a");
        for (Element element : result) {
            final Episode episode = parseEpisode(element);
            if (episode != null) {
                episodes.add(episode);
            }
        }
        return episodes;
    }

}
