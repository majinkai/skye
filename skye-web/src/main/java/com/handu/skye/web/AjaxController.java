package com.handu.skye.web;

import com.google.common.collect.Maps;
import com.handu.skye.repository.SpanMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Jinkai.Ma
 */
@RestController
public class AjaxController {

    @Autowired
    private SpanMRepository spanMRepository;

    @RequestMapping("/a/span/{id}")
    public Map<String, Object> get(@PathVariable String id, long start) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("start", start);
        result.put("span", spanMRepository.findById(id));
        return result;
    }

}
