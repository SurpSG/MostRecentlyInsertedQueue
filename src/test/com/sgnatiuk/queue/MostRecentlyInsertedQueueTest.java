package com.sgnatiuk.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sgnatiuk on 11/9/16.
 */
public class MostRecentlyInsertedQueueTest{

    private List<Integer> data;

    @Before
    public void initTestData(){
        data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(i);
        }
    }

    @Test
    public void testQueueCapacity(){
        int capacity = 10;
        MostRecentlyInsertedQueue<Integer> queue = new MostRecentlyInsertedQueue<>(capacity);
        for (int i = 0; i < data.size(); i++) {
            queue.offer(data.get(i));
            assertEquals(queue.size(), Math.min(capacity, i + 1));
        }
    }

    @Test
    public void testRemoveOldestElementWhenQueueIsFull(){
        int capacity = 10;
        MostRecentlyInsertedQueue<Integer> queue = new MostRecentlyInsertedQueue<>(capacity);
        for (int i = 0; i < data.size(); i++) {
            queue.offer(data.get(i));
            int fromIndex = Math.max(i-capacity+1, 0);
            int toIndex = i + 1;
            List<Integer> expectedElementsInQueue = data.subList(fromIndex, toIndex);
            assertTrue(isCollectionsAreEquals(queue, expectedElementsInQueue));
        }
    }

    private boolean isCollectionsAreEquals(Queue<Integer> coll1, List<Integer> coll2){
        if(coll1.size() != coll2.size()){
            return false;
        }

        int index = 0;
        for (Integer item : coll1) {
            if(item != coll2.get(index)){
                return false;
            }
            index++;
        }
        return true;
    }

}
