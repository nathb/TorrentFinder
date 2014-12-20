package com.nathb.torrentfinder.module;

import com.nathb.torrentfinder.service.TorrentCollectionService;
import com.nathb.torrentfinder.service.impl.TorrentCollectionServiceImpl;
import com.nathb.torrentfinder.ui.activity.HomeActivity;
import com.nathb.torrentfinder.ui.adapter.TorrentListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = { HomeActivity.class, TorrentListAdapter.class },
        complete = false)
public class TorrentCollectionServiceModule {

    @Provides @Singleton TorrentCollectionService providesTorrentCollectionService() {
        return new TorrentCollectionServiceImpl();
    }
}
