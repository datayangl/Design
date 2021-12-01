package com.ly.design.message.bus.design1;

public interface EventExceptionHandler {
    void handle(Throwable cause, EventContext context);
}
