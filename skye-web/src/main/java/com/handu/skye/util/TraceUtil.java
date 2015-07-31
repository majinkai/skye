package com.handu.skye.util;

import com.handu.skye.BinaryEvent;
import com.handu.skye.Endpoint;
import com.handu.skye.Event;
import com.handu.skye.Span;
import com.handu.skye.domain.BinaryEventM;
import com.handu.skye.domain.EventM;
import com.handu.skye.domain.SpanM;
import com.handu.skye.domain.TraceM;

/**
 * @author Jinkai.Ma
 */
public class TraceUtil {

    public static TraceM getTraceM(SpanM spanM) {
        return new TraceM(spanM.getTraceId(), spanM.getName(), spanM.getStart());
    }

    public static SpanM getSpanM(Span span) {
        SpanM spanM = new SpanM(span.getTraceId(), span.getId(), span.getName());
        for (Event o : span.getEvents()) {
            EventM m = new EventM(o.getTimestamp(), o.getKey(), getHost(o.getHost()), getPort(o.getHost()));
            if (gt(spanM.getStart(), m.getTimestamp())) {
                spanM.setStart(m.getTimestamp());
            }
            if (lt(spanM.getEnd(), m.getTimestamp())) {
                spanM.setEnd(m.getTimestamp());
            }
            spanM.getEvents().add(m);
        }

        for (BinaryEvent o : span.getBinaryEvents()) {
            BinaryEventM m = new BinaryEventM(o.getTimestamp(), o.getKey(), o.getValue(), getHost(o.getHost()), getPort(o.getHost()));
            if (gt(spanM.getStart(), m.getTimestamp())) {
                spanM.setStart(m.getTimestamp());
            }
            if (lt(spanM.getEnd(), m.getTimestamp())) {
                spanM.setEnd(m.getTimestamp());
            }
            spanM.getBinaryEvents().add(m);
        }



        return spanM;
    }

    private static String getHost(Endpoint endpoint) {
        return endpoint != null ? endpoint.getHost() : null;
    }

    private static Integer getPort(Endpoint endpoint) {
        if (endpoint != null && endpoint.getPort() > 0) {
            return endpoint.getPort();
        }
        return null;
    }

    private static boolean gt(Long l, long r) {
        return l == null || l > r;
    }

    private static boolean lt(Long l, long r) {
        return l == null || l < r;
    }

    public static long getStart(SpanM spanM) {
        if (spanM == null || spanM.getStart() == null) {
            return 0L;
        }
        return spanM.getStart();
    }

    public static long getEnd(SpanM spanM) {
        if (spanM == null || spanM.getEnd() == null) {
            return 0L;
        }
        return spanM.getEnd();
    }

}
