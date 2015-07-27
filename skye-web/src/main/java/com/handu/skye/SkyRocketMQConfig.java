package com.handu.skye;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.MQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.handu.skye.common.PropertyLoader;
import com.handu.skye.rocketmq.SpanListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jinkai.Ma
 */
@Configuration
public class SkyRocketMQConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SkyRocketMQConfig.class);

    @Value("${" + PropertyLoader.ROCKETMQ_KEY + "}")
    private String namesrvAddr = "127.0.0.1:9876";

    @Autowired
    private SpanListener listener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public MQPushConsumer spanPushConsumer() throws MQClientException {

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(Header.MQ_CONSUMER_GROUP);

        pushConsumer.setNamesrvAddr(namesrvAddr);
        pushConsumer.subscribe(Header.MQ_TOPIC, Header.MQ_TAG);
        pushConsumer.registerMessageListener(listener);

        LOG.debug("Started rocketmq consumer({})", Header.MQ_CONSUMER_GROUP);

        return pushConsumer;
    }

}
