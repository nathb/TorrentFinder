package com.nathb.torrentfinder.service.impl;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.service.TorrentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SFWTorrentService implements TorrentService {

    @Override
    public List<Torrent> getTorrents(Show show, Episode episode) throws IOException {
        final String name = show.getTitle() + " S" + episode.getSeasonNumber()
                + "E" + episode.getEpisodeNumber();
        List<Torrent> torrents = new ArrayList<Torrent>();
        torrents.add(new Torrent(name + " [eztv]", "YDay 21:11", "250mb", "eztv", "mg://123", 500, 100));
        torrents.add(new Torrent(name + " [rar]", "YDay 21:11", "230mb", "rar", "mg://123", 400, 50));
        torrents.add(new Torrent(name + " [test]", "YDay 21:11", "245mb", "test", "mg://123", 300, 100));
        torrents.add(new Torrent(name + " [test]", "YDay 21:11", "245mb", "test", "mg://123", 300, 100));
        torrents.add(new Torrent(name + " [test]", "YDay 21:11", "245mb", "test", "mg://123", 300, 100));
        return torrents;
    }
}
