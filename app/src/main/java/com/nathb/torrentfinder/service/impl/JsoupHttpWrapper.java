package com.nathb.torrentfinder.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

public class JsoupHttpWrapper {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";
    private static final String REFERRER = "http://www.google.com";
    private static final int MAX_RETRIES = 5;

    public static Document get(String url) throws IOException {
        int retryCount = 0;
        while (retryCount != MAX_RETRIES) {
            try {
                return Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        .referrer(REFERRER)
                        .get();
            } catch (SocketTimeoutException e) {
                retryCount++;
            }
        }
        throw new SocketTimeoutException("timeout");
    }

    public static Document post(String url, Map<String, String> postData) throws IOException {
        return Jsoup.connect(url)
                .data(postData)
                .post();
    }

}
