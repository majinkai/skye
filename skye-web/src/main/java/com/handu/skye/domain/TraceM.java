package com.handu.skye.domain;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Jinkai.Ma
 */
@Document(collection = "trace")
public class TraceM {

    @Id
    private String id;
    @Indexed(unique = true)
    private String traceId;
    private String name;

    @Transient
    private List<SpanM> spans;

    public TraceM() {
        this.spans = Lists.newArrayList();
    }

    public TraceM(String traceId, String name) {
        this.traceId = traceId;
        this.name = name;
        this.spans = Lists.newArrayList();
    }

    public String getId() {
        return id;
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

    public List<SpanM> getSpans() {
        return spans;
    }

    public void setSpans(List<SpanM> spans) {
        this.spans = spans;
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("spans", spans)
                .toString();
    }
}
