package com.handu.skye.common;

import com.handu.skye.BinaryEvent;
import com.handu.skye.Endpoint;
import com.handu.skye.Event;
import com.handu.skye.Span;
import org.nustaq.serialization.FSTConfiguration;

/**
 * @author Jinkai.Ma
 */
public final class Serializer {

    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    static {
        conf.registerClass(Span.class, Event.class, BinaryEvent.class, Endpoint.class);
    }

    private Serializer() {
    }

    public static byte[] ser(Span span) {
        return conf.asByteArray(span);
    }

    public static Span deser(byte[] bytes) {
        return (Span) conf.asObject(bytes);
    }

}
