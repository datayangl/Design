package com.ly.design.message.bus.design1.apply;

import com.ly.design.message.bus.design1.EventBus;

public class DirectoryMonitorInstance {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        DirectoryTargetMonitor monitor = null;
        try {
            monitor =  new DirectoryTargetMonitor(eventBus, "/Users/luoy/Desktop/test/t/");
            monitor.startMonitor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                monitor.stopMonitor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
