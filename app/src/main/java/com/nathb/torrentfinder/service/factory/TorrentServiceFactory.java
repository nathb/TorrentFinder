package com.nathb.torrentfinder.service.factory;

import android.content.Context;

import com.nathb.torrentfinder.config.Config;
import com.nathb.torrentfinder.service.TorrentService;
import com.nathb.torrentfinder.service.impl.KickAssService;
import com.nathb.torrentfinder.service.impl.OldPirateBayService;
import com.nathb.torrentfinder.service.impl.SFWTorrentService;
import com.nathb.torrentfinder.service.impl.ThePirateBayService;

public class TorrentServiceFactory {

    public enum TorrentServiceType {
        ThePirateBay,
        OldPirateBay,
        KickAss,
        SFWTorrent
    }

    public static TorrentService getTorrentService(Context context) {
        final TorrentServiceType type = Config.getTorrentServiceType(context);
        final TorrentService service;
        switch (type) {
            case ThePirateBay:
                service = new ThePirateBayService(context);
                break;
            case OldPirateBay:
                service = new OldPirateBayService(context);
                break;
            case KickAss:
                service = new KickAssService(context);
                break;
            case SFWTorrent:
                service = new SFWTorrentService();
                break;
            default:
                service = null;
        }
        return service;
    }
}
