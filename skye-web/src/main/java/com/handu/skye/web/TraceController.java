package com.handu.skye.web;

import com.handu.skye.domain.SpanM;
import com.handu.skye.domain.TraceM;
import com.handu.skye.repository.SpanMRepository;
import com.handu.skye.repository.TraceMRepository;
import com.handu.skye.util.TraceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Jinkai.Ma
 */
@Controller
public class TraceController {

    @Autowired
    private TraceMRepository traceMRepository;
    @Autowired
    private SpanMRepository spanMRepository;

    @RequestMapping("/")
    public String home() {
        return "redirect:trace";
    }

    @RequestMapping("/trace")
    public String list(Map<String, Object> model) {
        model.put("traces", traceMRepository.findAll());
        return "trace/list";
    }

    @RequestMapping("/trace/{traceId}")
    public String view(@PathVariable String traceId, Map<String, Object> model) {
        TraceM trace = traceMRepository.findByTraceId(traceId);
        if (trace != null) {
            model.put("trace", trace);

            List<SpanM> spans = spanMRepository.findByTraceId(traceId);
            if (spans != null) {
                Collections.sort(spans);

                SpanM root = spans.get(0);

                long start = TraceUtil.getStart(root);
                long end = TraceUtil.getEnd(root);

                model.put("spans", spans);
                model.put("start", start);
                model.put("end", end);
                model.put("duration", end - start);
            }
            return "trace/view";
        } else {
            return "redirect:trace";
        }
    }

}
