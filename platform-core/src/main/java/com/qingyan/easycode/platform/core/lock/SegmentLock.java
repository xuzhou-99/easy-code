package com.qingyan.easycode.platform.core.lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁
 *
 * @author xuzhou
 * @since 2022/11/16
 */
public class SegmentLock<T> {

    private final HashMap<Integer, ReentrantLock> lockMap = new HashMap<>();
    private Integer segments = 16;

    public SegmentLock() {
        this.init(null, false);
    }

    public SegmentLock(Integer counts, boolean fair) {
        this.init(counts, fair);
    }

    private void init(Integer counts, boolean fair) {
        if (counts != null) {
            this.segments = counts;
        }

        for (int i = 0; i < this.segments; ++i) {
            this.lockMap.put(i, new ReentrantLock(fair));
        }

    }

    public boolean lock(T key) {
        ReentrantLock lock = this.lockMap.get(this.getAbsHashKey(key));
        return lock.tryLock();
    }

    public boolean lock(T key, long timeout, TimeUnit unit) {
        ReentrantLock lock = this.lockMap.get(this.getAbsHashKey(key));

        try {
            return lock.tryLock() || lock.tryLock(timeout, unit);
        } catch (InterruptedException var7) {
            var7.printStackTrace();
            return false;
        }
    }

    public void unlock(T key) {
        ReentrantLock lock = this.lockMap.get(this.getAbsHashKey(key));
        lock.unlock();
    }

    private int getAbsHashKey(T key) {
        return Math.abs(key.hashCode() % this.segments);
    }
}
