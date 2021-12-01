package com.ly.design.message.bus.design1;

import java.util.concurrent.ThreadPoolExecutor;

public class AsyncEventBus extends EventBus {
    private final static String DEFAULT_ASYNCBUS_NAME = "default-async";

    AsyncEventBus(String busName, EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        super(busName, exceptionHandler, executor);
    }

    public AsyncEventBus(String busName, ThreadPoolExecutor executor) {
        super(busName, null, executor);
    }

    public AsyncEventBus( ThreadPoolExecutor executor) {
        super(DEFAULT_ASYNCBUS_NAME, null, executor);
    }

    public AsyncEventBus(EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        super(DEFAULT_ASYNCBUS_NAME, exceptionHandler, executor);
    }

}
