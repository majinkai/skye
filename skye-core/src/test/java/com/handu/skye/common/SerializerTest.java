package com.handu.skye.common;

import com.handu.skye.Span;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jinkai.Ma
 */
public class SerializerTest {

    @Test
    public void test() throws Exception {
        Span span = new Span();
        span.setId("SpanId-1");
        span.setTraceId("TraceId-1");
        span.setName("SpanName");
        span.addEvent("Event");
        span.addBinaryEvent("BinaryEvent", "Test");
        byte[] b = Serializer.ser(span);
        Span span1 = Serializer.deser(b);

        assertEquals(span, span1);
        assertEquals(span.toString(), span1.toString());
    }
}