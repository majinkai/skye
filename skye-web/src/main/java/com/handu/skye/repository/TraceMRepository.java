package com.handu.skye.repository;

import com.handu.skye.domain.TraceM;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jinkai.Ma
 */
public interface TraceMRepository extends MongoRepository<TraceM, String> {

    public TraceM findByTraceId(String traceId);

}
