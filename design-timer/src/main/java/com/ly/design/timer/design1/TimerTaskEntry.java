package com.ly.design.timer.design1;

public class TimerTaskEntry {
    volatile TimerTaskList list = null;
    TimerTaskEntry next = null;
    TimerTaskEntry prev = null;

    private TimerTask timerTask;
    private long expirationMs;

    public boolean cancelled() {
        return timerTask.getTimerTaskEntry() != this;
    }

    public TimerTaskEntry(TimerTask timerTask, long expirationMs) {
        this.timerTask = timerTask;
        this.expirationMs = expirationMs;
        if (timerTask != null) {
            timerTask.setTimerTaskEntry(this);
        }
    }

    public void remove() {
        TimerTaskList currentList = list;
        // If remove is called when another thread is moving the entry from a task entry list to another,
        // this may fail to remove the entry due to the change of value of list. Thus, we retry until the list becomes null.
        // In a rare case, this thread sees null and exits the loop, but the other thread insert the entry to another list later.
        while (currentList != null) {
            currentList.remove(this);
            currentList = list;
        }
    }

    public int compare(TimerTaskEntry that)  {
        return Long.compare(expirationMs, that.expirationMs);
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(long expirationMs) {
        this.expirationMs = expirationMs;
    }
}