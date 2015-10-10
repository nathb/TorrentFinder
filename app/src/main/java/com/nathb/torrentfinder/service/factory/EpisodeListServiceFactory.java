package com.nathb.torrentfinder.service.factory;

import android.content.Context;

import com.nathb.torrentfinder.config.Config;
import com.nathb.torrentfinder.service.EpisodeListService;
import com.nathb.torrentfinder.service.impl.EpGuidesTVMazeService;
import com.nathb.torrentfinder.service.impl.EpGuidesTVRageService;

public class EpisodeListServiceFactory {

    public enum EpisodeListServiceType {
        EpGuidesTVMaze,
        EpGuidesTVRage
    }

    public static EpisodeListService get(Context context) {
        final EpisodeListServiceType type = Config.getEpisodeListServiceType(context);
        final EpisodeListService service;
        switch (type) {
            case EpGuidesTVMaze:
                service = new EpGuidesTVMazeService();
                break;
            case EpGuidesTVRage:
                service = new EpGuidesTVRageService();
                break;
            default:
                service = null;
        }
        return service;
    }
}
