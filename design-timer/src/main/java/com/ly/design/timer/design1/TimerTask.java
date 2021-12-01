package com.ly.design.timer.design1;

abstract class TimerTask implements Runnable {
    public long delayMs;

    private TimerTaskEntry timerTaskEntry = null;

    public void cancel() {
        synchronized(this) {
            if (timerTaskEntry != null) {
                timerTaskEntry.remove();
            }
            timerTaskEntry = null;
        }
    }

    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this) {
            if (timerTaskEntry != null && timerTaskEntry != entry) {
                timerTaskEntry.remove();
            }
            timerTaskEntry = entry;
        }
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

}
