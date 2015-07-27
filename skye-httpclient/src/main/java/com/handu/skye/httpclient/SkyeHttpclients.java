package com.handu.skye.httpclient;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Jinkai.Ma
 */
public final class SkyeHttpclients {

    private SkyeHttpclients() {
    }

    public static HttpClientBuilder get() {
        return HttpClients.custom()
                .addInterceptorFirst(new SkyeHttpRequestInterceptor())
                .addInterceptorFirst(new SkyeHttpResponseInterceptor());
    }
}
