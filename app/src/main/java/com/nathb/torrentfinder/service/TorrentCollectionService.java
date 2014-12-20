package com.nathb.torrentfinder.service;

import android.content.Context;

import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.model.TorrentDataWrapper;

import javax.inject.Singleton;

public interface TorrentCollectionService {
    public void add(TorrentDataWrapper torrentData);
    public void remove(TorrentDataWrapper torrentData);
    public void send(Context context, EpisodeDao episodeDao);
}
