package com.nathb.torrentfinder.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library =  true, complete = false)
public class AndroidModule {

    private Context mContext;

    public AndroidModule(Context context) {
        mContext = context;
    }

    @Provides @Singleton Context providesContext() {
        return mContext;
    }
}
