package com.handu.skye.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.handu.skye.Span;
import com.handu.skye.common.Serializer;
import com.handu.skye.repository.SpanMRepository;
import com.handu.skye.repository.TraceMRepository;
import com.handu.skye.util.TraceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jinkai.Ma
 */
@Component
public class SpanListener implements MessageListenerConcurrently {

    private static final Logger LOG = LoggerFactory.getLogger(SpanListener.class);

    @Autowired
    private TraceMRepository traceMRepository;
    @Autowired
    private SpanMRepository spanMRepository;

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt message : list) {
            Span span = Serializer.deser(message.getBody());

            spanMRepository.save(TraceUtil.getSpanM(span));
            LOG.debug("Save span to mongodb, spanId: {}, traceId: {}", span.getId(), span.getTraceId());

            if (span.isRoot()) {
                traceMRepository.save(TraceUtil.getTraceM(span));
                LOG.debug("Save trace to mongodb, traceId: {}", span.getTraceId());
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
