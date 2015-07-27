package com.handu.skye.util;

import com.handu.skye.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jinkai.Ma
 */
public final class HttpTracerUtil {

    private HttpTracerUtil() {
    }

    public static void before(HttpServletRequest request) {
        String traceId = request.getHeader(Header.TRACE_ID);
        String spanId = request.getHeader(Header.SPAN_ID);
        boolean sampled = Boolean.parseBoolean(request.getHeader(Header.SAMPLED));
        Tracer.setRootSpan(traceId, spanId, sampled);

        Span span = Tracer.begin(request.getRequestURI());

        span.addEvent(Event.SERVER_RECV, new Endpoint(request.getLocalAddr(), request.getLocalPort()));
    }

    public static void after(HttpServletRequest request) {
        Span span = Tracer.lastSpan();
        if (span != null) {
            span.addEvent(Event.SERVER_SEND, new Endpoint(request.getLocalAddr(), request.getLocalPort()));
            Tracer.commit(span);
        }
        // release
        Tracer.release();
    }

}
