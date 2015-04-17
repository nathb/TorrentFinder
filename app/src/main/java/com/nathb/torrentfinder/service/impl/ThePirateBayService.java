package com.nathb.torrentfinder.service.impl;

import android.content.Context;
import android.net.Uri;

import com.nathb.torrentfinder.config.Config;
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.util.FormatUtil;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ThePirateBayService extends AbstractJsoupTorrentService {

    public static final String DEFAULT_URL = "thepiratebay.se";
    private static final String BASE_URL_FORMAT = "https://%s/search/";
    private static final String SORT_ORDER = "0/7/0";

    private Context mContext;

    public ThePirateBayService(Context context) {
        mContext = context;
    }

    @Override
    protected String buildUrl(Show show, Episode episode) {
        final String searchQuery = FormatUtil.getSearchQueryFormat(show, episode);
        final String baseUrl = String.format(BASE_URL_FORMAT, Config.getThePirateBayUrl(mContext));
        final Uri uri = new Uri.Builder()
                .encodedPath(baseUrl)
                .appendPath(searchQuery)
                .appendEncodedPath(SORT_ORDER).build();

        return uri.toString();
    }

    @Override
    protected List<Torrent> parseResponse(Document document) {
        final List<Torrent> torrents = new ArrayList<Torrent>();
        final int resultLimit = Config.getTorrentResultLimit(mContext);
        final Elements searchResults = document.select("#searchResult tr:not(.header):lt(" + (resultLimit) + ")");
        for (Element element : searchResults) {
            final String description = element.select(".detDesc").html();
            torrents.add(new Torrent(
                    element.select(".detName a").html(),
                    parseUploadedDate(description),
                    parseSize(description),
                    element.select(".detDesc a").html(),
                    element.select("a:eq(1)").attr("href"),
                    Integer.valueOf(element.select("td:eq(2)").html()),
                    Integer.valueOf(element.select("td:eq(3)").html())
            ));
        }
        return torrents;
    }

    private String parseUploadedDate(String input) {
        return input.split(",")[0]
                .replace("Uploaded ", "")
                .replace("&nbsp;", " ");
    }

    private String parseSize(String input) {
        return input.split(",")[1]
                .substring(1)
                .replace("&nbsp;", " ");
    }
}
