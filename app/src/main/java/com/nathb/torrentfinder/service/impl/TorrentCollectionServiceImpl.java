package com.nathb.torrentfinder.service.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nathb.torrentfinder.config.Config;
import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.TorrentDataWrapper;
import com.nathb.torrentfinder.service.TorrentCollectionService;
import com.nathb.torrentfinder.task.SetEpisodesDownloadedTask;
import com.nathb.torrentfinder.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class TorrentCollectionServiceImpl implements TorrentCollectionService {

    private static final String EMAIL_TITLE = "TorrentFinder";
    private static final String TORRENT_LINE_FORMAT = "%s %sx%s - %s.mp4 : %s\n";

    private List<TorrentDataWrapper> mTorrentList;

    public TorrentCollectionServiceImpl() {
        mTorrentList = new ArrayList<TorrentDataWrapper>();
    }

    @Override
    public void add(TorrentDataWrapper torrentData) {
        mTorrentList.add(torrentData);
    }

    @Override
    public void remove(TorrentDataWrapper torrentData) {
        mTorrentList.remove(torrentData);
    }

    @Override
    public void send(Context context, EpisodeDao episodeDao) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, getEmailAddress(context));
        intent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_TITLE);
        intent.putExtra(Intent.EXTRA_TEXT, generateEmailBody());
        context.startActivity(Intent.createChooser(intent, "Send Torrent Links via Email"));
        setTorrentsAsDownloaded(episodeDao);
        mTorrentList.clear();
    }

    private String[] getEmailAddress(Context context) {
        return new String[] { Config.getEmailAddress(context) };
    }

    private String generateEmailBody() {
        final StringBuilder sb = new StringBuilder();
        for (TorrentDataWrapper wrapper : mTorrentList) {
            sb.append(String.format(TORRENT_LINE_FORMAT,
                    wrapper.getShow().getTitle(),
                    wrapper.getEpisode().getSeasonNumber(),
                    FormatUtil.getFormattedNumber(wrapper.getEpisode().getEpisodeNumber()),
                    wrapper.getEpisode().getTitle(),
                    wrapper.getTorrent().getMagnetLink()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private void setTorrentsAsDownloaded(EpisodeDao episodeDao) {
        final List<Episode> episodes = new ArrayList<Episode>();
        for (TorrentDataWrapper wrapper : mTorrentList) {
            episodes.add(wrapper.getEpisode());
        }
        new SetEpisodesDownloadedTask(episodeDao, episodes).execute();
    }

}
