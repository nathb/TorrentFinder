package com.nathb.torrentfinder;

import android.app.Application;

import com.nathb.torrentfinder.module.AndroidModule;
import com.nathb.torrentfinder.module.EpisodeDaoModule;
import com.nathb.torrentfinder.module.ShowDaoModule;
import com.nathb.torrentfinder.module.TorrentCollectionServiceModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class TorrentFinderApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(getModules().toArray());
    }

    private List<? extends Object> getModules() {
        return Arrays.asList(
                new AndroidModule(this),
                new EpisodeDaoModule(),
                new ShowDaoModule(),
                new TorrentCollectionServiceModule());
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
