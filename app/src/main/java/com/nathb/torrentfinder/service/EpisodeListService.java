package com.nathb.torrentfinder.service;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;

import java.io.IOException;
import java.util.List;

public interface EpisodeListService {
    public List<Episode> getEpisodes(Show show) throws IOException;
}
