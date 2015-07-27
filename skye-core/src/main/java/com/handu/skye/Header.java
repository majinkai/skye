package com.handu.skye;

/**
 * @author Jinkai.Ma
 */
public class Header {

    public static final String TRACE_ID = "X-Skye-TraceId";
    public static final String SPAN_ID = "X-Skye-SpanId";
    public static final String SAMPLED = "X-Skye-Sampled";

    public static final String MQ_TOPIC = "MQ-Skye-Topic";
    public static final String MQ_TAG = "MQ-Skye-Tag";
    public static final String MQ_PRODUCER_GROUP = "MQ-Skye-Producer-Group";
    public static final String MQ_CONSUMER_GROUP = "MQ-Skye-Consumer-Group";
}
