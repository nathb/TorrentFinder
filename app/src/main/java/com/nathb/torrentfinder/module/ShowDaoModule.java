package com.nathb.torrentfinder.module;

import android.content.Context;

import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.loader.ShowListLoader;
import com.nathb.torrentfinder.loader.TorrentListLoader;
import com.nathb.torrentfinder.provider.EpisodeProvider;
import com.nathb.torrentfinder.task.AddShowTask;
import com.nathb.torrentfinder.task.DeleteShowTask;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
            AddShowTask.class,
            DeleteShowTask.class,
            EpisodeProvider.class,
            ShowListLoader.class,
            TorrentListLoader.class},
        includes = AndroidModule.class,
        complete = false)
public class ShowDaoModule {

    @Provides @Singleton ShowDao providesShowDao(Context context) {
        return new ShowDao(context);
    }
}
