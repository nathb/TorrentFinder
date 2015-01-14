package com.nathb.torrentfinder.module;

import android.content.Context;

import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.provider.EpisodeProvider;
import com.nathb.torrentfinder.task.SetEpisodesDownloadedTask;
import com.nathb.torrentfinder.task.UpdateEpisodeTask;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
            EpisodeProvider.class,
            SetEpisodesDownloadedTask.class,
            UpdateEpisodeTask.class},
        includes = AndroidModule.class,
        complete = false)
public class EpisodeDaoModule {

    @Provides @Singleton EpisodeDao providesEpisodeDao(Context context) {
        return new EpisodeDao(context);
    }
}
