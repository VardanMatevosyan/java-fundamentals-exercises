package com.bobocode.cs;


import com.bobocode.util.ExerciseNotCompletedException;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <T> generic type parameter
 * @author Taras Boychuk
 * @author Serhii Hryhus
 */
public class LinkedList<T> implements List<T> {

    Node<T> head;
    Node<T> tail;
    int size = 0;

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements the were passed as method parameters
     */
    public static <T> LinkedList<T> of(T... elements) {
        LinkedList<T> list = new LinkedList<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        Node<T> nodeElement = new Node<>(element);

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            nodeElement.next = head;
            head = nodeElement;
            size++;
        }

        if (index > 0) {
            Node<T> prevNode = getPrevNode(index);
            nodeElement.next = prevNode.next;
            prevNode.next = nodeElement;
            size++;
        }

    }

    private Node<T> getPrevNode(int index) {
        Node<T> prevNode = head;
        for (int i = 0; i < index - 1; i++) {
            prevNode = prevNode.next;
        }
        return prevNode;
    }

    /**
     * Changes the value of an list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        Node<T> node = head;

        if ((index == 0 && node == null) || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i <= index; i++) {
            if (i == index) {
                node.item = element;
            }
            node = node.next;
        }

    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        Node<T> node = head;
        if (node == null || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    /**
     * Returns the first element of the list. Operation is performed in constant time O(1)
     *
     * @return the first element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.item;
    }

    /**
     * Returns the last element of the list. Operation is performed in constant time O(1)
     *
     * @return the last element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        return tail.item;
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return deleted element
     */
    @Override
    public T remove(int index) {
        Node<T> node = head;

        if ((node == null && index == 0) || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            head = node.next;
            size--;
        }

        if (index > 0) {
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    Node<T> prevNode = getPrevNode(index);
                    prevNode.next = node.next;
                    if (index == size -1) {
                        tail = prevNode;
                        tail.next = null;
                    }
                    size--;
                }
                if (i != index) {
                    node = node.next;
                }
            }
        }
        return node.item;
    }


    /**
     * Checks if a specific exists in he list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        Node<T> node = head;

        if (node == null) {
            return false;
        }

        if (node.item.equals(element)) {
            return true;
        }

        for (int i = 0; i < size; i++) {
            node = node.next;
            if (node == null) {
                return false;
            }
            if (node.item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    static class Node<T> {
        T item;
        Node<T> next;

        Node(T item) {
            this.item = item;
        }


    }
}
