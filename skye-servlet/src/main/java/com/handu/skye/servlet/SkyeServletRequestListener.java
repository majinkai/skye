package com.handu.skye.servlet;

import com.handu.skye.util.HttpTracerUtil;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Jinkai.Ma
 */
public class SkyeServletRequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre) {
        HttpTracerUtil.before((HttpServletRequest) sre.getServletRequest());
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        HttpTracerUtil.after((HttpServletRequest) sre.getServletRequest());
    }
}
