package com.handu.skye.deliver;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.handu.skye.Header;
import com.handu.skye.Span;
import com.handu.skye.SpanDeliver;
import com.handu.skye.common.Serializer;
import com.handu.skye.util.ProducerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Jinkai.Ma
 */
public class RocketMQSpanDeliver implements SpanDeliver {

    private static final Logger LOG = LoggerFactory.getLogger(RocketMQSpanDeliver.class);

    private MQProducer producer;

    public RocketMQSpanDeliver() {
        producer = Preconditions.checkNotNull(ProducerUtil.getProducer());
        try {
            producer.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    producer.shutdown();
                }
            });
        } catch (MQClientException e) {
            LOG.error("MQProducer start error", e);
            throw new RuntimeException(e);
        }
    }

    public void send(Span span) {
        this.send(Lists.newArrayList(span));
    }

    public void send(List<Span> spans) {
        try {
            for (Span span : spans) {
                LOG.debug("Ready for send {}", span);
                Message message = new Message(Header.MQ_TOPIC, Header.MQ_TAG, Serializer.ser(span));
                SendResult sendResult = producer.send(message);
                LOG.debug("SendResult={}", sendResult);
            }
        } catch (Exception e) {
            LOG.error("Send message exception", e);
        }
    }

}
