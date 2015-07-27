package com.handu.skye.amqp;

import com.handu.skye.Header;
import com.handu.skye.Span;
import com.handu.skye.Tracer;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author Jinkai.Ma
 */
public class SkyeRabbitTemplate extends RabbitTemplate {

    protected void doSend(Channel channel, String exchange, String routingKey, Message message, CorrelationData correlationData) throws Exception {
        Span span = Tracer.begin("MQ.provider");

        span.addBinaryEvent("provide.start", exchange + ":" + routingKey);

        message.getMessageProperties().getHeaders().put(Header.TRACE_ID, span.getTraceId());
        message.getMessageProperties().getHeaders().put(Header.SPAN_ID, span.getId());
        message.getMessageProperties().getHeaders().put(Header.SAMPLED, span.isSampled());

        try {
            super.doSend(channel, exchange, routingKey, message, correlationData);
        } catch (Exception e) {
            span.addBinaryEvent("provide.exception", e.getMessage());
            throw e;
        } finally {
            span.addBinaryEvent("provide.end", exchange + ":" + routingKey);
            Tracer.commit(span);
        }
    }
}
