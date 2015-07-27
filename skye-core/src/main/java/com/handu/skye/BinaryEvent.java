package com.handu.skye;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author Jinkai.Ma
 */
public class BinaryEvent implements Serializable {

    private long timestamp; // require
    private String key; // require
    private String value;
    private Endpoint host;

    public BinaryEvent() {
    }

    public BinaryEvent(long timestamp, String key, String value) {
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
    }

    public BinaryEvent(long timestamp, String key, String value, Endpoint host) {
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
        this.host = host;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Endpoint getHost() {
        return host;
    }

    public void setHost(Endpoint host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("timestamp", timestamp)
                .add("key", key)
                .add("value", value)
                .add("host", host)
                .toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryEvent that = (BinaryEvent) o;

        return Objects.equal(this.getTimestamp(), that.getTimestamp())
                && Objects.equal(this.getKey(), that.getKey())
                && Objects.equal(this.getValue(), that.getValue())
                && Objects.equal(this.getHost(), that.getHost());

    }

    public int hashCode() {
        return Objects.hashCode(this);
    }
}
