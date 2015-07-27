package com.handu.skye.sampler;

import com.handu.skye.Sampler;
import com.handu.skye.common.PropertyLoader;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jinkai.Ma
 */
public class DefaultSampler implements Sampler {

    private int maxCount;
    private int overflow;
    private long lastTimeMillis = -1;
    private AtomicInteger counter = new AtomicInteger(0);

    public DefaultSampler() {
        this.maxCount = Integer.parseInt(PropertyLoader.getProperties().getProperty(PropertyLoader.SAMPLE_COUNT_KEY, "100"));
        this.overflow = Integer.parseInt(PropertyLoader.getProperties().getProperty(PropertyLoader.SAMPLE_OVERFLOW_KEY, "10"));
        lastTimeMillis = System.currentTimeMillis();
    }

    public boolean isSample() {
        boolean isSample = true;
        int c = counter.incrementAndGet();
        long current = System.currentTimeMillis();

        if ((current - lastTimeMillis) < 1000) {
            if (c > maxCount) {
                c = c%overflow;
                if (c != 0) {
                    isSample = false;
                }
            }
        } else {
            counter.set(0);
            lastTimeMillis = current;
        }

        return isSample;
    }
}
