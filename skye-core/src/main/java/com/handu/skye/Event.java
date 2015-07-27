package com.handu.skye;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author Jinkai.Ma
 */
public class Event implements Serializable {

    public static final String CLIENT_SEND = "CS";
    public static final String CLIENT_RECV = "CR";
    public static final String SERVER_SEND = "SS";
    public static final String SERVER_RECV = "SR";

    private long timestamp; // require
    private String key; // require
    private Endpoint host;

    public Event() {
    }

    public Event(long timestamp, String key) {
        this.timestamp = timestamp;
        this.key = key;
    }

    public Event(long timestamp, String key, Endpoint host) {
        this.timestamp = timestamp;
        this.key = key;
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

    public Endpoint getHost() {
        return host;
    }

    public void setHost(Endpoint host) {
        this.host = host;
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("timestamp", timestamp)
                .add("key", key)
                .add("host", host)
                .toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event that = (Event) o;

        return Objects.equal(this.getTimestamp(), that.getTimestamp())
                && Objects.equal(this.getKey(), that.getKey())
                && Objects.equal(this.getHost(), that.getHost());

    }

    public int hashCode() {
        return Objects.hashCode(this);
    }
}
