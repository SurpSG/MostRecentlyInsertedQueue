package com.sgnatiuk.queue;

/**
 * Created by Surop on 11/13/2016.
 */
public class Node<T>{
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