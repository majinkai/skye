package com.handu.skye;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.handu.skye.collector.AsyncSpanCollector;
import com.handu.skye.sampler.DefaultSampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tracer(Singleton)
 *
 * @author Jinkai.Ma
 */
public final class Tracer {

    private static final Logger LOG = LoggerFactory.getLogger(Tracer.class);

    private static Tracer INSTANCE = new Tracer();

    static {
        INSTANCE.startCollector();
    }

    private AsyncSpanCollector collector;
    private Sampler simpler;

    private Tracer() {
        collector = new AsyncSpanCollector();
        // TODO 通过配置获得采样率实例
        simpler = new DefaultSampler();
    }

    // 开启收集线程
    private void startCollector() {
        collector.start();
    }

    // 设置根Span
    public static void setRootSpan(String tid, String sid, boolean sampled) {
        if ((tid != null && tid.length() > 0) && (sid != null && sid.length() > 0)) {
            INSTANCE.traceIdHolder.set(tid);
            Span span = new Span();
            span.setTraceId(tid);
            span.setId(sid);
            span.setSampled(sampled);
            INSTANCE.addSpan(span);
            LOG.debug("Set root span: {}", span);
        }
    }

    // 开始跟踪
    public static Span begin(String spanName) {
        return INSTANCE.newSpan(spanName);
    }

    // 提交跟踪
    public static void commit() {
        Tracer.commit(Tracer.lastSpan());
    }

    // 提交Span跟踪
    public static void commit(Span span) {
        LOG.debug("Wait for send {}", span);
        if (INSTANCE.spanListHolder.get().indexOf(span) >= 0 && span.isSampled()) {
            INSTANCE.collector.put(span);
        }
        INSTANCE.removeSpan(span);
        if (INSTANCE.spanListHolder.get().size() == 0) {
            Tracer.release();
        }
    }

    // 释放资源
    public static void release() {
        LOG.debug("Release trace: {}", Tracer.getTraceId());
        INSTANCE.traceIdHolder.remove();
        INSTANCE.spanIdHolder.remove();
        INSTANCE.spanListHolder.remove();
    }

    private ThreadLocal<String> traceIdHolder = new ThreadLocal<String>() {
        protected String initialValue() {
            return ID.uuid();
        }
    };

    private ThreadLocal<Map<String, AtomicInteger>> spanIdHolder = new ThreadLocal<Map<String, AtomicInteger>>() {
        protected Map<String, AtomicInteger> initialValue() {
            return Maps.newHashMap();
        }
    };
    private ThreadLocal<List<Span>> spanListHolder = new ThreadLocal<List<Span>>() {
        protected List<Span> initialValue() {
            return Lists.newArrayList();
        }
    };

    public static String getTraceId() {
        return INSTANCE.traceIdHolder.get();
    }

    public String nextSpanId() {
        Span parentSpan = Tracer.lastSpan();
        String parentId = parentSpan != null ? parentSpan.getId() : null;
        AtomicInteger ai = spanIdHolder.get().get(parentId);
        if (ai == null) {
            ai = new AtomicInteger(0);
            spanIdHolder.get().put(parentId, ai);
        }
        if (parentId == null) {
            return String.valueOf(ai.incrementAndGet());
        } else {
            return parentId + "." + ai.incrementAndGet();
        }
    }

    private Span newSpan(String name) {
        Span span = new Span();
        span.setTraceId(Tracer.getTraceId());
        span.setId(this.nextSpanId());
        span.setName(name);
        span.setSampled(this.isSampled());
        this.addSpan(span);

        LOG.debug("New span: {}", span);

        return span;
    }

    public void addSpan(Span span) {
        spanListHolder.get().add(span);
    }

    public void removeSpan(Span span) {
        spanListHolder.get().remove(span);
    }

    public void removeLastSpan() {
        List<Span> sl = spanListHolder.get();
        if (sl.size() > 0) {
            sl.remove(sl.size() - 1);
            spanListHolder.set(sl);
        }
    }

    public static Span lastSpan() {
        List<Span> spans = INSTANCE.spanListHolder.get();
        if (spans.size() == 0) {
            return null;
        }
        return spans.get(spans.size() - 1);
    }

    private boolean isSampled() {
        List<Span> spans = this.spanListHolder.get();
        if (spans.size() > 0) {
            return spans.get(0).isSampled();
        } else {
            return simpler.isSample();
        }
    }

}
