package com.handu.skye.example.servlet;

import com.handu.skye.Span;
import com.handu.skye.Tracer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jinkai.Ma
 */
public class DemoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Span span = Tracer.begin("DemoServlet");
        span.addBinaryEvent("Start", "这里是\"开始\"输出的信息");
        try {
            simulation1();
        } finally {
            span.addBinaryEvent("End", "这里是\"结束\"输出的信息");
            Tracer.commit(span);
        }
    }

    private void simulation1() {
        Span span = Tracer.begin("DemoServlet.simulation1");
        span.addEvent("SaveUserStart");
        try {
            Thread.sleep(8L);
            span.addBinaryEvent("Log", "保存用户成功，这里记录日志(前后sleep了8毫秒)");
            Thread.sleep(8L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.addEvent("SaveUserEnd");
        }
        Tracer.commit(span);
    }
}
