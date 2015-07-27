package com.handu.skye.collector;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.handu.skye.Span;
import com.handu.skye.SpanDeliver;
import com.handu.skye.deliver.RocketMQSpanDeliver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author Jinkai.Ma
 */
public class AsyncSpanCollector {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncSpanCollector.class);

    private BlockingQueue<Span> queue;
    private List<Span> cache;
    private final TransferTask task;
    private SpanDeliver deliver;

    public AsyncSpanCollector() {
        queue = Queues.newLinkedBlockingQueue(1024);
        cache = Lists.newArrayList();
        task = new TransferTask();
        // TODO 通过配置获得发送实例
        deliver = new RocketMQSpanDeliver();
    }

    public void put(Span span) {
        try {
            queue.add(span);
        } catch (IllegalStateException e) {
            LOG.warn("Queue capacity 1024, span ignore", e);
            this.notifyTask();
        }
    }

    public void start() {
        if (!task.isAlive()) {
            task.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    cancel();
                }
            });
        }
    }

    public void notifyTask() {
        if (task.isAlive()) {
            synchronized (task) {
                task.notify();
            }
        }
    }

    private void cancel() {
        task.interrupt();
    }

    private class TransferTask extends Thread {

        public TransferTask() {
            this.setName("Thread-TransferTask");
        }

        public void run() {
            try {
                while (!this.isInterrupted()) {
                    queue.drainTo(cache);
                    if (cache.size() > 0) {
                        deliver.send(cache);
                        cache.clear();
                    }
                    synchronized (this) {
                        this.wait(10000L);
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
