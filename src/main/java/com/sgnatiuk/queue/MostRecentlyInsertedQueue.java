package com.sgnatiuk.queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Created by sgnatiuk on 11/9/16.
 */
public class MostRecentlyInsertedQueue<T> extends AbstractQueue<T> {

    private Node head;
    private Node tail;
    private int size = 0;
    private int maxCapacity;

    public MostRecentlyInsertedQueue(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public MostRecentlyInsertedQueue(Collection<T> data) {
        maxCapacity = data.size();
        for (T t : data) {
            offer(t);
        }
    }

    public Iterator<T> iterator() {
        return new MostRecentlyInsertedIterator();
    }

    public int size() {
        return size;
    }

    public boolean offer(T t) {
        if(size == maxCapacity){
            poll();
        }
        if(head == null){
            head = new Node(t);
            tail = head;
        }else {
            tail.next = new Node(t);
            tail = tail.next;
        }
        size++;
        return true;
    }

    public T poll() {
        if (size == 0)
            return null;

        T value = head.value;
        head = head.next;
        size--;
        if(size < 2) tail = head;
        return value;
    }

    public T peek() {
        if (size == 0)
            return null;
        return head.value;
    }

    private class Node{
        T value;
        Node next;

        public Node(T value) {
            this(value, null);
        }

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private class MostRecentlyInsertedIterator implements Iterator<T>{

        private Node currentNode = head;

        public boolean hasNext() {
            return currentNode != null;
        }

        public T next() {
            T value = currentNode.value;
            currentNode = currentNode.next;
            return value;
        }
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
