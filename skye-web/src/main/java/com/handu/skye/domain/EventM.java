package com.handu.skye.domain;

import com.google.common.base.Objects;

/**
 * @author Jinkai.Ma
 */
public class EventM {

    private Long timestamp;
    private String key;
    private String host;
    private Integer port;

    public EventM() {
    }

    public EventM(Long timestamp, String key, String host, Integer port) {
        this.timestamp = timestamp;
        this.key = key;
        this.host = host;
        this.port = port;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("timestamp", timestamp)
                .add("key", key)
                .add("host", host)
                .add("port", port)
                .toString();
    }

}
