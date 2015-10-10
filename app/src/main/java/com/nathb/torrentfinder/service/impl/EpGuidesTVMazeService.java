package com.nathb.torrentfinder.service.impl;

import android.text.TextUtils;

import com.nathb.torrentfinder.model.Episode;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EpGuidesTVMazeService extends AbstractEpGuidesService {

    // -1x01-
    private static final String REGEX = "-\\d{1,2}x\\d{1,2}-";

    private Pattern mPattern;

    public EpGuidesTVMazeService() {
        mPattern = Pattern.compile(REGEX);
    }

    @Override
    protected Document getDocument(String url) throws IOException {
        return JsoupHttpWrapper.get(url);
    }

    @Override
    protected Episode parseEpisode(Element element) {
        final String title = element.html();

        int seasonNumber = -1;
        int episodeNumber = -1;

        final String href = element.attr("href");
        if (!TextUtils.isEmpty(href)) {
            final Matcher matcher = mPattern.matcher(href);
            if (matcher.find()) {
                final String matchedStr = matcher.group(0).replace("-","");
                final int x = matchedStr.indexOf('x');
                seasonNumber = Integer.valueOf(matchedStr.substring(0, x));
                episodeNumber = Integer.valueOf(matchedStr.substring(x + 1, matchedStr.length()));
            }
        }

        Episode episode = null;
        if (!TextUtils.isEmpty(title) &&
                seasonNumber > 0 &&
                episodeNumber > 0) {
            episode = new Episode(seasonNumber, episodeNumber, title);
        }

        return episode;
    }
}
