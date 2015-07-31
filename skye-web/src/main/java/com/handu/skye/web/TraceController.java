package com.handu.skye.web;

import com.handu.skye.domain.SpanM;
import com.handu.skye.domain.TraceM;
import com.handu.skye.repository.SpanMRepository;
import com.handu.skye.repository.TraceMRepository;
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
            List<SpanM> spans = spanMRepository.findByTraceId(traceId);
            if (spans != null) {
                Collections.sort(spans);

                long start = -1;
                long end = 0;

                for (SpanM spanM : spans) {
                    if (start == -1 || spanM.getStart() < start) {
                        start = spanM.getStart();
                    }
                    if (spanM.getEnd() > end) {
                        end = spanM.getEnd();
                    }
                }

                trace.setStart(start);
                trace.setEnd(end);

                model.put("spans", spans);
                model.put("trace", trace);
                return "trace/view";
            }
        }
        return "redirect:trace";
    }

}
