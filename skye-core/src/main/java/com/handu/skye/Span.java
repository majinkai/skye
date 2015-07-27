package com.handu.skye;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jinkai.Ma
 */
public class Span implements Serializable {

    private String id; // require
    private String traceId; // require
    private String name; // require
    private boolean sampled;
    private List<Event> events;
    private List<BinaryEvent> binaryEvents;

    public Span() {
        this.events = Lists.newArrayList();
        this.binaryEvents = Lists.newArrayList();
        this.sampled = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSampled() {
        return sampled;
    }

    public void setSampled(boolean sampled) {
        this.sampled = sampled;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addEvent(String key) {
        events.add(new Event(System.currentTimeMillis(), key));
    }

    public void addEvent(String key, Endpoint endpoint) {
        events.add(new Event(System.currentTimeMillis(), key, endpoint));
    }

    public List<BinaryEvent> getBinaryEvents() {
        return binaryEvents;
    }

    public void setBinaryEvents(List<BinaryEvent> binaryEvents) {
        this.binaryEvents = binaryEvents;
    }

    public void addBinaryEvent(BinaryEvent binaryEvent) {
        binaryEvents.add(binaryEvent);
    }

    public void addBinaryEvent(String key, String value) {
        binaryEvents.add(new BinaryEvent(System.currentTimeMillis(), key, value));
    }

    public void addBinaryEvent(String key, String value, Endpoint endpoint) {
        binaryEvents.add(new BinaryEvent(System.currentTimeMillis(), key, value, endpoint));
    }

    public boolean isRoot() {
        return !this.getId().contains(".");
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("traceId", traceId)
                .add("name", name)
                .add("sampled", sampled)
                .add("events", events)
                .add("binaryEvents", binaryEvents)
                .toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Span that = (Span) o;

        return Objects.equal(this.getId(), that.getId())
                && Objects.equal(this.getTraceId(), that.getTraceId())
                && Objects.equal(this.getName(), that.getName())
                && Objects.equal(this.isSampled(), that.isSampled())
                && Objects.equal(this.getEvents(), that.getEvents())
                && Objects.equal(this.getBinaryEvents(), that.getBinaryEvents());
    }

    public int hashCode() {
        return Objects.hashCode(this);
    }
}
