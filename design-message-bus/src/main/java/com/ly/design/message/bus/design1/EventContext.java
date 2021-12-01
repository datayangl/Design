package com.ly.design.message.bus.design1;

import java.lang.reflect.Method;

public interface EventContext {
    String getSource();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();
}
