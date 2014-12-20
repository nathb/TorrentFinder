package com.nathb.torrentfinder.service;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;

import java.io.IOException;
import java.util.List;

public interface TorrentService {
    public static final int LIMIT = 5;
    public List<Torrent> getTorrents(Show show, Episode episode) throws IOException;
}
