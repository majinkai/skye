package com.handu.skye.sampler;

import com.handu.skye.Sampler;
import com.handu.skye.Tracer;
import com.handu.skye.common.PropertyLoader;

/**
 * @author Jinkai.Ma
 */
public class HashSampler implements Sampler {

    private int overflow;

    public HashSampler() {
        this.overflow = Integer.parseInt(PropertyLoader.getProperties().getProperty(PropertyLoader.SAMPLE_OVERFLOW_KEY, "10"));
    }

    public boolean isSample() {
        String traceId = Tracer.getTraceId();
        int hash = traceId.hashCode();
        return hash % overflow == 0;
    }

}
