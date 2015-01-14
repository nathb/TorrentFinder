package com.nathb.torrentfinder.util;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;

public class FormatUtil {

    public static String getFormattedNumber(int number) {
        final StringBuilder builder = new StringBuilder();
        if (number < 10) {
            builder.append("0");
        }
        builder.append(number);
        return builder.toString();
    }

    public static String getSearchQueryFormat(Show show, Episode episode) {
        StringBuilder searchQuery = new StringBuilder(show.getTitle())
                .append(" S").append(FormatUtil.getFormattedNumber(episode.getSeasonNumber()))
                .append("E").append(FormatUtil.getFormattedNumber(episode.getEpisodeNumber()));
        return searchQuery.toString();
    }
}
