package com.nathb.torrentfinder.service.impl;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.service.TorrentService;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public abstract class AbstractJsoupTorrentService implements TorrentService {

    protected abstract String buildUrl(String title, int seasonNumber, int episodeNumber);
    protected abstract List<Torrent> parseResponse(Document document);

    @Override
    public List<Torrent> getTorrents(Show show, Episode episode) throws IOException {
        final String url = buildUrl(show.getTitle(), episode.getSeasonNumber(), episode.getEpisodeNumber());
        final Document document = JsoupHttpWrapper.get(url);
        return parseResponse(document);
    }

}
