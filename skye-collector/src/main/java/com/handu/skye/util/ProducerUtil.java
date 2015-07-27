package com.handu.skye.util;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.handu.skye.Header;
import com.handu.skye.common.PropertyLoader;

/**
 * @author Jinkai.Ma
 */
public class ProducerUtil {

    public static DefaultMQProducer getProducer() {
        String namesrvAddr = PropertyLoader.getProperties().getProperty(PropertyLoader.ROCKETMQ_KEY);

        DefaultMQProducer producer = new DefaultMQProducer(Header.MQ_PRODUCER_GROUP);
        producer.setNamesrvAddr(namesrvAddr);

        return producer;
    }

}
