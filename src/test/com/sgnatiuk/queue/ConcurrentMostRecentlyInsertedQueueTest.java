package com.sgnatiuk.queue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Created by sgnatiuk on 11/9/16.
 */
public class ConcurrentMostRecentlyInsertedQueueTest extends BaseQueueTest{


    @Override
    protected Queue<Integer> createQueue(int capacity) {
        return new ConcurrentMostRecentlyInsertedQueue<>(capacity);
    }

    @Test
    public void concurrentAddElementsMaxThreads() throws InterruptedException {
        concurrentAddElements(data.size());
    }

    @Test
    public void concurrentAddElements4Threads() throws InterruptedException {
        concurrentAddElements(4);
    }

    @Test
    public void concurrentAddElements3Threads() throws InterruptedException {
        concurrentAddElements(3);
    }

    @Test
    public void concurrentAddAndPollElementsMaxThreads() throws InterruptedException {
        concurrentAddAndPoll(data.size());
    }

    @Test
    public void concurrentAddAndPollElements3Threads() throws InterruptedException {
        concurrentAddAndPoll(3);
    }

    @Test
    public void concurrentAddAndPollElements4Threads() throws InterruptedException {
        concurrentAddAndPoll(4);
    }

    @Test
    public void concurrentAddAndPollElements1Threads() throws InterruptedException {
        concurrentAddAndPoll(1);
    }


    private void concurrentAddAndPoll(int threadsNum) throws InterruptedException {
        Queue<Integer> queue = createQueue(data.size());
        List<Thread> offerThreads = createOfferThreads(queue, threadsNum);

        int elementsPerThread = (data.size()-1)/threadsNum + 1;
        Integer[] elements = new Integer[data.size()];
        List<Thread> pollThreads = new ArrayList<>();
        for (int i = 0; i < threadsNum; i++) {
            int from = i * elementsPerThread;
            int to = Math.min(from + elementsPerThread, data.size());
            pollThreads.add(new Thread(){
                @Override
                public void run() {
                    for (int j = from; j < to; j++) {
                        Integer value = queue.poll();
                        if(value == null){
                            j--;
                            continue;
                        }
                        elements[j] = value;

                    }
                }
            });
        }

        startAllThreads(offerThreads);
        startAllThreads(pollThreads);

        waitAllThreadsFinished(offerThreads);
        waitAllThreadsFinished(pollThreads);

        verifyAllElementsIsPresent(elements);
    }

    private void concurrentAddElements(int threadsNum) throws InterruptedException {
        Queue<Integer> queue = createQueue(data.size());
        List<Thread> threads = createOfferThreads(queue, threadsNum);
        startAllThreads(threads);
        waitAllThreadsFinished(threads);
        verifyAllElementsIsPresent(queue);
    }

    private List<Thread> createOfferThreads(Queue<Integer> queueToOffer, int threadsNum){
        List<Thread> offerThreads = new ArrayList<>();
        int elementsPerThread = (data.size()-1)/threadsNum + 1;
        for (int i = 0; i < threadsNum; i++) {
            int from = i * elementsPerThread;
            int to = Math.min(from + elementsPerThread, data.size());
            offerThreads.add(createOfferThread(queueToOffer, from, to));
        }
        return offerThreads;
    }

    private Thread createOfferThread(Queue<Integer> queueToOffer, int dataFrom, int dataTo){
        return new Thread(){
            @Override
            public void run() {
                for (int j = dataFrom; j < dataTo; j++) {
                    queueToOffer.offer(data.get(j));

                }
            }
        };
    }

    private void startAllThreads(List<Thread> threads){
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private void waitAllThreadsFinished(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private void verifyAllElementsIsPresent(Queue<Integer> queue){
        verifyAllElementsIsPresent(queue.toArray(new Integer[0]));
    }

    private void verifyAllElementsIsPresent(Integer[] elements){
        int result = 0;
        for (int i = 0; i < elements.length; i++) {
            result ^= elements[i]^i;
        }
        assertEquals(0, result);
    }

}
