package com.handu.skye;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author Jinkai.Ma
 */
public class Endpoint implements Serializable {

    private String host; // required
    private int port; // required

    public Endpoint() {
    }

    public Endpoint(String host, int port) {
        this.host = host;
        if (port > 0) {
            this.port = port;
        } else {
            this.port = 80;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        if (port > 0) {
            this.port = port;
        } else {
            this.port = 80;
        }
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("host", host)
                .add("port", port)
                .toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint that = (Endpoint) o;

        return Objects.equal(this.getHost(), that.getHost()) && Objects.equal(this.getPort(), that.getPort());

    }

    public int hashCode() {
        return Objects.hashCode(this);
    }
}
