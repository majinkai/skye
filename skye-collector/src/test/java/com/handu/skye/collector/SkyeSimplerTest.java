package com.handu.skye.collector;

import com.handu.skye.Sampler;
import com.handu.skye.sampler.DefaultSampler;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jinkai.Ma
 */
public class SkyeSimplerTest {

    @Test
    public void testSample() throws Exception {
        Sampler sampler = new DefaultSampler();
        boolean isSample = sampler.isSample();
        assertTrue(isSample);
    }

    @Test
    public void testNotSample() throws Exception {
        Sampler sampler = new DefaultSampler();
        boolean isSample = true;
        for (int i = 0; i < 101; i++) {
            isSample = sampler.isSample();
        }
        assertFalse(isSample);
    }

    @Test
    public void testSampleWithOverflow() throws Exception {
        Sampler sampler = new DefaultSampler();
        boolean isSample;
        int counter = 0;
        for (int i = 0; i < 155; i++) {
            isSample = sampler.isSample();
            if (isSample) {
                counter++;
            }
        }
        assertEquals(counter, 105);
    }

    @Test
    public void testNotSampleWithOverflow() throws Exception {
        Sampler sampler = new DefaultSampler();
        boolean isSample = true;
        for (int i = 0; i < 149; i++) {
            isSample = sampler.isSample();
        }
        assertFalse(isSample);
    }

    @Test
    public void testSampleAfter1s() throws Exception {
        Sampler sampler = new DefaultSampler();
        boolean isSample = true;
        for (int i = 0; i < 101; i++) {
            if (i > 99) {
                Thread.sleep(1000L);
            }
            isSample = sampler.isSample();
        }
        assertTrue(isSample);
    }
}