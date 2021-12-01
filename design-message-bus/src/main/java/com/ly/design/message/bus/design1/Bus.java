package com.ly.design.message.bus.design1;


/*
 * Bus接口定义了EventBus的所有使用方法
 * */
public interface Bus {

    /*
     * 将某个对象注册到Bus上，之后该类就成为了Suscriber
     * */
    void register(Object subscriber);

    /*
     * 将某个对象从Bus上取消注册，取消之后就不会再收到来自Bus的任何消息
     * */
    void unregister(Object subscriber);

    /*
     * 提交Event到默认的topic
     * */
    void post(Object event);

    /*
     * 提交Event到指定的topic
     * */
    void post(Object event, String topic);

    /*
     * 关闭该Bus  主要为了显示地释放Bus持有地资源 比如线程池ExecutorService
     */
    void close();

    /*
     * 返回该Bus的名称
     * */
    String getBusName();
}
