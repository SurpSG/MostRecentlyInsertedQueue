package com.sgnatiuk.queue;

import java.util.Collection;

/**
 * Created by sgnatiuk on 11/9/16.
 */
public class ConcurrentMostRecentlyInsertedQueue<T> extends MostRecentlyInsertedQueue<T> {

    public ConcurrentMostRecentlyInsertedQueue(int maxCapacity) {
        super(maxCapacity);
    }

    public ConcurrentMostRecentlyInsertedQueue(Collection<T> data) {
        super(data);
    }

    @Override
    public synchronized boolean offer(T t) {
        checkNotNull(t);
        return super.offer(t);
    }

    @Override
    public synchronized T poll() {
        return super.poll();
    }

    @Override
    public synchronized T peek() {
        return super.peek();
    }

    private void checkNotNull(T t){
        if(t == null) throw new NullPointerException();
    }
}
