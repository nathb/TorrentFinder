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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EpGuidesService implements EpisodeListService {

    // http://epguides.com/BigBangTheory/
    private static final String BASE_URL = "http://epguides.com";
    private static final String SEASON_REGEX = "season \\d{1,2}";
    private static final String EPISODE_REGEX = "episode \\d{1,2}";

    private Pattern mSeasonPattern;
    private Pattern mEpisodePattern;

    public EpGuidesService() {
        mSeasonPattern = Pattern.compile(SEASON_REGEX);
        mEpisodePattern = Pattern.compile(EPISODE_REGEX);
    }

    @Override
    public List<Episode> getEpisodes(Show show) throws IOException {
        final String url = buildUrl(show.getEpisodeListSearchTerm());
        final Document document = JsoupHttpWrapper.get(url);
        return parseResponse(document);
    }

    private String buildUrl(String title) {
        return new Uri.Builder()
                .encodedPath(BASE_URL)
                .appendPath(title)
                .build()
                .toString();
    }

    private List<Episode> parseResponse(Document document) {
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

    private Episode parseEpisode(Element element) {
        final String title = element.html();

        int seasonNumber = -1;
        int episodeNumber = -1;

        final String description = element.attr("title");
        if (!TextUtils.isEmpty(description)) {
            final Matcher seasonMatcher = mSeasonPattern.matcher(description);
            if (seasonMatcher.find()) {
                String seasonNumberStr = seasonMatcher.group(0).replace("season ", "");
                seasonNumber = Integer.valueOf(seasonNumberStr);
            }

            final Matcher episodeMatcher = mEpisodePattern.matcher(description);
            if (episodeMatcher.find()) {
                String episodeNumberStr = episodeMatcher.group(0).replace("episode ", "");
                episodeNumber = Integer.valueOf(episodeNumberStr);
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
