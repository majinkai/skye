package com.handu.skye.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Jinkai.Ma
 */
public class PropertyLoader {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyLoader.class);

    public static final String ROCKETMQ_KEY = "skye.rocketmq";
    public static final String SAMPLE_COUNT_KEY = "skye.sample.count";
    public static final String SAMPLE_OVERFLOW_KEY = "skye.sample.overflow";
    private static final String DEFAULT_ROCKETMQ = "127.0.0.1:9876";
    private static final String DEFAULT_SAMPLE_COUNT = "100";
    private static final String DEFAULT_SAMPLE_OVERFLOW = "10";

    private static Properties properties = null;

    public static Properties getProperties() {
        if (properties != null) {
            return properties;
        }
        properties = new Properties();
        try {
            InputStream is = PropertyLoader.class.getClassLoader().getResourceAsStream("skye.properties");
            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            LOG.warn("Can not load properties file, use default configuration.");
            properties.setProperty(ROCKETMQ_KEY, DEFAULT_ROCKETMQ);
            properties.setProperty(SAMPLE_COUNT_KEY, DEFAULT_SAMPLE_COUNT);
            properties.setProperty(SAMPLE_OVERFLOW_KEY, DEFAULT_SAMPLE_OVERFLOW);
        }
        verifyProperties(properties);
        return properties;
    }

    private static void verifyProperties(Properties properties) {
        String rocketmq = properties.getProperty(ROCKETMQ_KEY);
        String sampleCount = properties.getProperty(SAMPLE_COUNT_KEY);
        String sampleOverflow = properties.getProperty(SAMPLE_OVERFLOW_KEY);
        if (rocketmq == null || rocketmq.length() == 0) {
            properties.setProperty(ROCKETMQ_KEY, DEFAULT_ROCKETMQ);
        }
        if (sampleCount == null || sampleCount.length() == 0) {
            properties.setProperty(SAMPLE_COUNT_KEY, DEFAULT_SAMPLE_COUNT);
        }
        if (sampleOverflow == null || sampleOverflow.length() == 0) {
            properties.setProperty(SAMPLE_OVERFLOW_KEY, DEFAULT_SAMPLE_OVERFLOW);
        }
    }

}
