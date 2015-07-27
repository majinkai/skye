package com.handu.skye;

import java.util.List;

/**
 * @author Jinkai.Ma
 */
public interface SpanDeliver {
    void send(Span span);
    void send(List<Span> spans);
}
