package com.sgnatiuk.queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Created by sgnatiuk on 11/9/16.
 */
public class MostRecentlyInsertedQueue<T> extends AbstractQueue<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;
    private int maxCapacity;

    public MostRecentlyInsertedQueue(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public MostRecentlyInsertedQueue(Collection<T> data) {
        maxCapacity = data.size();
        addAll(data);
    }

    public Iterator<T> iterator() {
        Node<T> head = this.head;
        return new MostRecentlyInsertedIterator<>(head);
    }

    public int size() {
        return size;
    }

    public boolean offer(T t) {
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
        return true;
    }

    public T poll() {
        if (size == 0)
            return null;

        T value = head.getValue();
        head = head.getNextNode();
        size--;
        if(size < 2) tail = head;
        return value;
    }

    public T peek() {
        if (size == 0)
            return null;
        return head.getValue();
    }

    @Override
    public String toString() {
        Iterator<T> iterator = iterator();
        StringJoiner joiner = new StringJoiner(", ");
        while (iterator.hasNext()){
            joiner.add(iterator.next().toString());
        }

        return new StringBuilder(getClass().getSimpleName())
                .append('{')
                .append(joiner)
                .append('}').toString();
    }

    private class Node<T>{
        private T value;
        private Node next;

        public Node(T value) {
            this(value, null);
        }

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNextNode() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private class MostRecentlyInsertedIterator<T> implements Iterator<T> {

        private Node<T> currentNode;

        public MostRecentlyInsertedIterator(Node<T> head) {
            this.currentNode = head;
        }

        public boolean hasNext() {
            return currentNode != null;
        }

        public T next() {
            T value = currentNode.getValue();
            currentNode = currentNode.getNextNode();
            return value;
        }
    }
}
