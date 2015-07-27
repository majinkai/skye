package com.handu.skye.httpclient;

import com.handu.skye.*;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @author Jinkai.Ma
 */
public class SkyeHttpRequestInterceptor implements HttpRequestInterceptor {

    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Span span = Tracer.begin(this.getMethod(httpRequest));
        span.addEvent(Event.CLIENT_SEND, this.getEndpoint(httpRequest));
        this.addHeaders(httpRequest, span);
    }

    private Endpoint getEndpoint(HttpRequest httpRequest) {
        HttpRequestWrapper wrapper = (HttpRequestWrapper) httpRequest;
        return new Endpoint(wrapper.getTarget().getHostName(), wrapper.getTarget().getPort());
    }

    private String getMethod(HttpRequest httpRequest) {
        return httpRequest.getRequestLine().getMethod() + ":" + httpRequest.getRequestLine().getUri();
    }

    private void addHeaders(HttpRequest httpRequest, Span span) {
        httpRequest.addHeader(Header.TRACE_ID, span.getTraceId());
        httpRequest.addHeader(Header.SPAN_ID, span.getId());
        httpRequest.addHeader(Header.SAMPLED, String.valueOf(span.isSampled()));
    }

}
