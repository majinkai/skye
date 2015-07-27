import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.handu.skye.Header;
import com.handu.skye.common.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jinkai.Ma
 */
public class Test {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class);
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws MQClientException {
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Header.MQ_CONSUMER_GROUP);
        consumer.setNamesrvAddr("127.0.0.1:9876");

        consumer.subscribe(Header.MQ_TOPIC, Header.MQ_TAG);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt message : list) {
                    try {
                        count.incrementAndGet();
                        LOG.debug("接受到的消息: {}", Serializer.deser(message.getBody()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                consumer.shutdown();
                LOG.debug("Count {}", count.get());
            }
        });

        consumer.start();
    }

}
