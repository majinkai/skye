package com.handu.skye.repository;

import com.handu.skye.domain.SpanM;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Jinkai.Ma
 */
public interface SpanMRepository extends MongoRepository<SpanM, String> {

    SpanM findById(String id);

    List<SpanM> findByTraceId(String traceId);

}
