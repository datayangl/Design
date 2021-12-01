package com.ly.design.timer.design1;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class TimerTaskList implements Delayed {
    private AtomicInteger taskCounter;

    private AtomicLong expiration = new AtomicLong(-1L);

    // TimerTaskList forms a doubly linked cyclic list using a dummy root entry
    // root.next points to the head
    // root.prev points to the tail
    private TimerTaskEntry root = new TimerTaskEntry(null, -1);

    public TimerTaskList(AtomicInteger taskCounter) {
        this.taskCounter = taskCounter;
    }

    public boolean setExpiration(long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    public long getExpiration() {
        return expiration.get();
    }

    public void forEach(Function<TimerTask, Void> func) {
        synchronized(this) {
            TimerTaskEntry entry = root.next;
            while (!entry.equals(root)) {
                TimerTaskEntry nextEntry = entry.next;

                if (!entry.cancelled()) {
                    func.apply(entry.getTimerTask());
                }

                entry = nextEntry;
            }
        }

    }

    // Add a timer task entry to this list
    public void add(TimerTaskEntry entry) {
        boolean done = false;
        while (!done) {
            entry.remove();
            synchronized (this) {
                synchronized (entry) {
                    if (entry.list == null) {
                        // put the timer task entry to the end of the list. (root.prev points to the tail entry)
                        TimerTaskEntry tail = root.prev;
                        entry.next = root;
                        entry.prev = tail;
                        entry.list = this;
                        tail.next = entry;
                        root.prev = entry;
                        taskCounter.incrementAndGet();
                        done = true;
                    }
                }
            }
        }
    }

    // Remove the specified timer task entry from this list
    public void remove(TimerTaskEntry entry) {
        synchronized (this) {
            synchronized (entry) {
                if (entry.list.equals(this)) {
                    entry.next.prev = entry.prev;
                    entry.prev.next = entry.next;
                    entry.next = null;
                    entry.prev = null;
                    entry.list = null;
                    taskCounter.incrementAndGet();
                }
            }
        }
    }

    public void flush() {

    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max(getExpiration() - System.currentTimeMillis(), 0), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        TimerTaskList other = (TimerTaskList) o;
        return Long.compare(getExpiration(), other.getExpiration());
    }
}

