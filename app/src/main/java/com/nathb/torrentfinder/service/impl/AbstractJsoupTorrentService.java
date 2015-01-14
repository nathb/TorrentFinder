package com.nathb.torrentfinder.service.impl;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.service.TorrentService;
import com.nathb.torrentfinder.service.exception.TorrentResponseException;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public abstract class AbstractJsoupTorrentService implements TorrentService {

    protected abstract String buildUrl(Show show, Episode episode);
    protected abstract List<Torrent> parseResponse(Document document);

    @Override
    public List<Torrent> getTorrents(Show show, Episode episode) throws TorrentResponseException {
        final String url = buildUrl(show, episode);

        final Document document;
        try {
            document = JsoupHttpWrapper.get(url);
        } catch (IOException e) {
            throw new TorrentResponseException(e);
        }
        return parseResponse(document);
    }

}
