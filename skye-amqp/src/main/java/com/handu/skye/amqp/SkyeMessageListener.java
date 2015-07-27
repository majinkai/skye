package com.handu.skye.amqp;

import com.handu.skye.Header;
import com.handu.skye.Span;
import com.handu.skye.Tracer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;

import java.util.Map;

/**
 * @author Jinkai.Ma
 */
public abstract class SkyeMessageListener implements MessageListener {

    public void onMessage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String traceId = (String) headers.get(Header.TRACE_ID);
        String spanId = (String) headers.get(Header.SPAN_ID);
        Boolean sampled = (Boolean) headers.get(Header.SAMPLED);

        MessageProperties properties = message.getMessageProperties();

        Tracer.setRootSpan(traceId, spanId, sampled.booleanValue());

        Span span = null;

        // 没有跟踪头信息不采样
        if (Tracer.lastSpan() != null) {
            span = Tracer.begin("MQ.consumer");
            span.addBinaryEvent("consumed.start", properties.getReceivedExchange() + ":" + properties.getReceivedRoutingKey());
        }

        handleMessage(message);

        if (span != null) {
            span.addBinaryEvent("consumed.end", properties.getReceivedExchange() + ":" + properties.getReceivedRoutingKey());
            Tracer.commit(span);
        }
        Tracer.release();
    }

    public abstract void handleMessage(Message message);
}
