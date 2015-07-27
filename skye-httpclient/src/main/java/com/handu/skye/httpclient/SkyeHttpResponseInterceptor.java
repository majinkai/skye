package com.handu.skye.httpclient;

import com.handu.skye.Event;
import com.handu.skye.Span;
import com.handu.skye.Tracer;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @author Jinkai.Ma
 */
public class SkyeHttpResponseInterceptor implements HttpResponseInterceptor {

    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Span span = Tracer.lastSpan();
        if (span != null) {
            span.addBinaryEvent("http.statuscode", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            span.addEvent(Event.CLIENT_RECV);

            Tracer.commit(span);
        }
    }

}
