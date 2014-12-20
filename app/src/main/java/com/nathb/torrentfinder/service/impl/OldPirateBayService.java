package com.nathb.torrentfinder.service.impl;

import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.util.FormatUtil;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class OldPirateBayService extends AbstractJsoupTorrentService {

    private static final String BASE_URL = "https://oldpiratebay.org/search.php?q=";
    private static final String SORT_ORDER = "&Torrent_sort=seeders.desc";
    private String mTitleLower;

    @Override
    protected String buildUrl(String title, int seasonNumber, int episodeNumber) {
        mTitleLower = title.toLowerCase();
        final String searchTitle = new StringBuilder(title)
                .append(" S").append(FormatUtil.getFormattedNumber(seasonNumber))
                .append("E").append(FormatUtil.getFormattedNumber(episodeNumber))
                .toString().replace(" ", "+");

        return new StringBuilder(BASE_URL)
                .append(searchTitle)
                .append(SORT_ORDER)
                .toString();
    }

    @Override
    protected List<Torrent> parseResponse(Document document) {
        final List<Torrent> torrents = new ArrayList<Torrent>();
        final Elements searchResults = document.select("tr[class]:lt(" + (LIMIT) + ")");
        for (Element element : searchResults) {
            final String title = element.select("td.title-row span").html();

            // Old Pirate Bay returns wrong results if none exist
            if (!title.toLowerCase().contains(mTitleLower)) {
                return torrents;
            }

            torrents.add(new Torrent(
                    title,
                    element.select("td.date-row").html(),
                    element.select("td.size-row").html(),
                    "", // No user name available
                    element.select("td.title-row a").attr("href"),
                    Integer.valueOf(element.select("td.seeders-row").html()),
                    Integer.valueOf(element.select("td.leechers-row").html())
            ));
        }
        return torrents;
    }
}
