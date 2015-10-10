package com.nathb.torrentfinder.service.impl;

import android.text.TextUtils;

import com.nathb.torrentfinder.model.Episode;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EpGuidesTVRageService extends AbstractEpGuidesService {

    private static final String SEASON_REGEX = "season \\d{1,2}";
    private static final String EPISODE_REGEX = "episode \\d{1,2}";

    private Pattern mSeasonPattern;
    private Pattern mEpisodePattern;

    private Map<String, String> mPostData;

    public EpGuidesTVRageService() {
        mSeasonPattern = Pattern.compile(SEASON_REGEX);
        mEpisodePattern = Pattern.compile(EPISODE_REGEX);
        mPostData = new HashMap<String, String>();
        mPostData.put("list", "tvrage.com");
    }

    @Override
    protected Document getDocument(String url) throws IOException {
        return JsoupHttpWrapper.post(url, mPostData);
    }

    @Override
    protected Episode parseEpisode(Element element) {
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
