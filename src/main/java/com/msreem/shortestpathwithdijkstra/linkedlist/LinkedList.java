package com.msreem.shortestpathwithdijkstra.linkedlist;

// Implementation of linkedlist class
public class LinkedList<T> {

    private Node<T> head, tail;

    public Node<T> getHead() {
        return head;
    }

    public T getFirst() {
        return (head != null) ? head.getData() : null;
    }

    public T getLast() {
        return (tail != null) ? tail.getData() : null;
    }

    // Inserts data at head
    public void insertFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null)   // empty
            head = tail = newNode;
        else {
            newNode.setNext(head);
            head = newNode;
        }
    }

    // Inserts data at tail
    public void insertLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null)   // empty
            head = tail = newNode;
        else {
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    // Deletes last node
    public Node<T> deleteLast() {
        Node<T> deleted = tail, curr = head;
        for (; curr != tail && curr.getNext() != tail; curr = curr.getNext());
        if (curr == tail)  // empty or 1 node
            head = tail = null;
        else {
            curr.setNext(null);
            tail = curr;
        }
        return deleted;
    }

    // Deletes first node
    public Node<T> deleteFirst() {
        Node<T> deleted = head;
        if (head == tail) // empty or 1 node
            head = tail = null;
        else
            head = head.getNext();
        return deleted;
    }

    // Returns number of nodes
    public int length() {
        Node<T> curr = head;
        int count = 0;
        while (curr != null) {
            count++;
            curr = curr.getNext();
        }
        return count;
    }

    // Checks if the linked-list is empty
    public boolean isEmpty() {
        return head == null && tail == null;
    }

    // Deletes the nodes
    public void clear() {
        head = tail = null;
    }

    @Override
    public String toString() {
        Node<T> curr = head;
        String linkedlist = "Head --> ";
        while (curr != null) {
            linkedlist += curr + " --> ";
            curr = curr.getNext();
        }
        return linkedlist + "Null";
    }

}
