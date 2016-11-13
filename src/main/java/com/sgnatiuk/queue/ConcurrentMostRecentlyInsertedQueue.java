package com.sgnatiuk.queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Created by sgnatiuk on 11/9/16.
 */
public class ConcurrentMostRecentlyInsertedQueue<T> extends AbstractQueue<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;
    private int maxCapacity;

    public ConcurrentMostRecentlyInsertedQueue(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ConcurrentMostRecentlyInsertedQueue(Collection<T> data) {
        maxCapacity = data.size();
        addAll(data);
    }

    public Iterator<T> iterator() {
        return new MostRecentlyInsertedIterator<>(head);
    }

    public int size() {
        return size;
    }

    public boolean offer(T t) {
        checkNotNull(t);

        synchronized (this){
            if(size == maxCapacity){
                poll();
            }
            if(head == null){
                head = new Node<>(t);
                tail = head;
            }else {
                tail.setNext(new Node<>(t));
                tail = tail.getNextNode();
            }
            size++;
        }
        return true;
    }

    public synchronized T poll() {
        if (size == 0)
            return null;

        T value = head.getValue();
        head = head.getNextNode();
        size--;
        if(size < 2) tail = head;
        return value;
    }

    public synchronized T peek() {
        if (size == 0)
            return null;
        return head.getValue();
    }

    private void checkNotNull(T t){
        if(t == null) throw new NullPointerException();
    }

    @Override
    public String toString() {
        Iterator<T> iterator = iterator();
        StringJoiner joiner = new StringJoiner(", ");
        while (iterator.hasNext()){
            joiner.add(iterator.next().toString());
        }

        return new StringBuilder("MostRecentlyInsertedQueue{")
                .append(joiner)
                .append('}').toString();
    }
}
