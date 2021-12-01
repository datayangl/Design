package com.ly.design.message.bus.design1;

import java.lang.reflect.Method;

public class Subscriber {

    private final Object subscribeObject;
    private final Method subsribeMethod;
    private boolean disable = false;

    public Subscriber(Object subscribeObject, Method subsribeMethod) {
        this.subscribeObject = subscribeObject;
        this.subsribeMethod = subsribeMethod;
    }

    public Object getSubscribeObject() {
        return subscribeObject;
    }

    public Method getSubsribeMethod() {
        return subsribeMethod;
    }

    public boolean isDisable(){
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}