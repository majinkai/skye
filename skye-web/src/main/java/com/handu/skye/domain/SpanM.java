package com.handu.skye.domain;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Jinkai.Ma
 */
@Document(collection = "span")
@CompoundIndexes({@CompoundIndex(name = "span_idx", def = "{'spanId': 1, 'traceId': 1}")})
public class SpanM implements Comparable<SpanM> {

    @Id
    private String id;
    private String traceId;
    private String spanId;
    private String name;
    private Long start;
    private Long end;

    private List<EventM> events;
    private List<BinaryEventM> binaryEvents;

    public SpanM() {
    }

    public SpanM(String traceId, String spanId, String name) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.name = name;
        this.events = Lists.newArrayList();
        this.binaryEvents = Lists.newArrayList();
    }

    public String getId() {
        return id;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public List<EventM> getEvents() {
        return events;
    }

    public void setEvents(List<EventM> events) {
        this.events = events;
    }

    public List<BinaryEventM> getBinaryEvents() {
        return binaryEvents;
    }

    public void setBinaryEvents(List<BinaryEventM> binaryEvents) {
        this.binaryEvents = binaryEvents;
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("spanId", spanId)
                .add("name", name)
                .add("start", start)
                .add("end", end)
                .add("events", events)
                .add("binaryEvents", binaryEvents)
                .toString();
    }

    private static final String SPANID_SPLITOR = "\\.";

    public int compareTo(SpanM that) {
        if (that == null) {
            return 1;
        }
        String[] s1 = this.getSpanId().split(SPANID_SPLITOR);
        String[] s2 = that.getSpanId().split(SPANID_SPLITOR);

        if (s1.length < s2.length) {
            return -1;
        } else if (s1.length > s2.length) {
            return 1;
        }

        for (int i = 0; i < s1.length; i++) {
            int i1 = Integer.parseInt(s1[i]);
            int i2 = Integer.parseInt(s2[i]);
            if (i1 != i2) {
                return i1 - i2;
            }
        }

        return 0;
    }

}
