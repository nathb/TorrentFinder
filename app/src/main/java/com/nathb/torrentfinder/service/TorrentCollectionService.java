package com.nathb.torrentfinder.service;

import android.content.Context;

import com.nathb.torrentfinder.model.TorrentDataWrapper;

public interface TorrentCollectionService {
    public void add(TorrentDataWrapper torrentData);
    public void remove(TorrentDataWrapper torrentData);
    public void send(Context context);
}
