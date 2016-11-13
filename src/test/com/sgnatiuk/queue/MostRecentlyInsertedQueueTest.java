package com.sgnatiuk.queue;

import java.util.Queue;

/**
 * Created by sgnatiuk on 11/9/16.
 */
public class MostRecentlyInsertedQueueTest extends BaseQueueTest{


    @Override
    protected Queue<Integer> createQueue(int capacity) {
        return new MostRecentlyInsertedQueue<>(capacity);
    }

}
