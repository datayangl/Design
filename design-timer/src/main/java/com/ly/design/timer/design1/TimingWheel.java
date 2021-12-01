package com.ly.design.timer.design1;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TimingWheel {
    private long tickMs;

    private int wheelSize;

    private long startMs;

    private AtomicInteger taskCounter;

    private DelayQueue<TimerTaskList> queue;

    private long interval;

    private long currentTime;

    private TimerTaskList[] buckets;

    private volatile TimingWheel overflowWheel = null;

    public TimingWheel(long tickMs, int wheelSize, long startMs, AtomicInteger taskCounter, DelayQueue<TimerTaskList> queue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;

        this.interval = tickMs * wheelSize;
        this.currentTime = startMs - (startMs % tickMs);

        initializeBuckets(wheelSize, taskCounter);
    }

    private void initializeBuckets(int wheelSize, AtomicInteger taskCounter) {
        buckets = new TimerTaskList[wheelSize];
        for (int i=0; i<wheelSize; i++) {
            buckets[i] = new TimerTaskList(taskCounter);
        }
    }

    /**
     * 当调度时间超过当前时间轮的范围，需要增加上层时间轮
     */
    public void addOverflowWheel() {
        synchronized(this) {
            if(overflowWheel == null) {
              overflowWheel = new TimingWheel(
                interval,
                wheelSize,
                currentTime,
                taskCounter,
                queue
              );
            }
        }
    }

    public boolean add(TimerTaskEntry entry) {
        long expiration = entry.getExpirationMs();

        if (entry.cancelled()) {
            // Cancelled
            return false;
        } else if (expiration < currentTime + tickMs) {
            // Already expired
            return false;
        } else if (expiration < currentTime + interval) {
            // Put in its own bucket. 当前 bucket 可以存放
            int bucketId =  (int) (expiration / tickMs);
            TimerTaskList bucket = buckets[bucketId];
            bucket.add(entry);

            // Set the bucket expiration time
            if (bucket.setExpiration(bucketId * tickMs)) {
                queue.offer(bucket);
            }
            return true;
        } else {
            // need overflowWheel
            if (overflowWheel != null) {
                addOverflowWheel();
            }

           return  overflowWheel.add(entry);
        }
    }

    public void advanceClock(long timeMs) {
        if (timeMs >= currentTime + tickMs) {
            currentTime = timeMs - (timeMs % timeMs);

            if (overflowWheel != null) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }
}
