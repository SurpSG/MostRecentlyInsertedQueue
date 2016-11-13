package com.sgnatiuk.queue;

import java.util.Iterator;

/**
 * Created by Surop on 11/13/2016.
 */
public class MostRecentlyInsertedIterator<T> implements Iterator<T> {

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