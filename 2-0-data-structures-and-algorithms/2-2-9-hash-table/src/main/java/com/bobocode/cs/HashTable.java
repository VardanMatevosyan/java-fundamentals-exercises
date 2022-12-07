package com.bobocode.cs;

import com.bobocode.util.ExerciseNotCompletedException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * {@link HashTable} is a simple Hashtable-based implementation of {@link Map} interface with some additional methods.
 * It is based on the array of {@link Node} objects. Both {@link HashTable} and {@link Node} have two type parameters:
 * K and V, which represent key and value.
 * <p>
 * Elements are stored int the table by their key. A table is basically an array, and fast access is possible due to
 * array capabilities. (You can access an array element by its index in O(1) time). In order to find an index for any
 * given key, it uses calculateIndex method which is based on the element's hash code.
 * <p>
 * If two elements (keys) have the same array index, they form a linked list. That's why class {@link Node} requires
 * a reference to the next field.
 * <p>
 * Since you don't always know the number of elements in advance, the table can be resized. You can do that manually by
 * calling method resizeTable, or it will be done automatically once the table reach resize threshold.
 * <p>
 * The initial array size (initial capacity) is 8.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <K> key type
 * @param <V> value type
 * @author Taras Boychuk
 */
public class HashTable<K, V> implements Map<K, V> {

    private Node<K,V>[] table;
    private int size = 0;

    public HashTable(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        table = new Node[capacity];
    }

    public HashTable() {
        table = new Node[8];
    }

    /**
     * This method is a critical part of the hast table. The main idea is that having a key, you can calculate its index
     * in the array using the hash code. Since the computation is done in constant time (O(1)), it's faster than
     * any other kind search.
     * <p>
     * It's a function that accepts a key and calculates its index using a hash code. Please note that index cannot be
     * equal or greater than array size (table capacity).
     * <p>
     * This method is used for all other operations (put, get, remove).
     *
     * @param key
     * @param tableCapacity underlying array size
     * @return array index of the given key
     */
    public static int calculateIndex(Object key, int tableCapacity) {
        if (Objects.nonNull(key)) {
            int hashCode = key.hashCode();
            return (hashCode ^ (hashCode >>> 16)) & tableCapacity - 1;
        }
        return 0;
    }

    /**
     * Creates a mapping between provided key and value, and returns the old value. If there was no such key, it returns
     * null. {@link HashTable} does not support duplicate keys, so if you put the same key it just overrides the value.
     * <p>
     * It uses calculateIndex method to find the corresponding array index. Please note, that even different keys can
     * produce the same array index.
     *
     * @param key key
     * @param value value
     * @return old value or null
     */
    @Override
    public V put(K key, V value) {
        int index = calculateIndex(key, table.length);
        Node<K, V> previous = table[index];

        if (previous != null) {
            Node<K, V> temp = previous;
            while (temp != null) {
                if (temp.key.equals(key)) {
                    V prevValue = previous.value;
                    previous.value = value;
                    return prevValue;
                }
                temp = temp.next;
            }
        }

        Node<K, V> node = new Node<>(key, value);
        if (previous == null) {
            table[index] = node;
            size++;
            return null;
        } else {
            Node<K,V> lastNode = previous.next;
            while (lastNode != null && lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (lastNode == null) {
                previous.next = node;
            } else {
                lastNode.next = node;
            }
            size++;
            return null;
        }

    }


    /**
     * Retrieves a value by the given key. It uses calculateIndex method to find the corresponding array index.
     * Then it iterates though all elements that are stored by that index, and uses equals to compare its keys.
     *
     * @param key
     * @return value stored in the table by the given key or null if there is no such key
     */
    @Override
    public V get(K key) {
        int index = calculateIndex(key, table.length);
        Node<K, V> currentNode = table[index];

        if (currentNode == null) {
            return null;
        }

        if (currentNode.next == null) {
            return currentNode.value;
        }

        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return currentNode.value;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    /**
     * Checks if the table contains a given key.
     *
     * @param key
     * @return true is there is such key in the table or false otherwise
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Checks if the table contains a given value.
     *
     * @param value
     * @return true is there is such value in the table or false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < table.length - 1; i++) {
            Node<K, V> currentNode = table[i];
            while (currentNode != null) {
                if (currentNode.value.equals(value)) {
                    return true;
                }
                currentNode = currentNode.next;
            }
        }
        return false;
    }

    /**
     * Return a number of elements in the table.
     *
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks is the table is empty.
     *
     * @return true is table size is zero or false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes an element by its key and returns a removed value. If there is no such key in the table, it returns null.
     *
     * @param key
     * @return removed value or null
     */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int index = calculateIndex(key, table.length);
        Node<K, V> node = table[index];

        if (node.next == null) {
            table[index] = null;
            size--;
            return node.value;
        }

        Node<K, V> previousNode = node;
        while (node != null) {
            if (node.key.equals(key)) {
                previousNode.next = node.next;
                size--;
                return node.value;

            }
            previousNode = node;
            node = node.next;
        }


        return null;
    }

    /**
     * It's a special toString method dedicated to help you visualize a hash table. It creates a string that represents
     * an underlying array as a table. It has multiples rows. Every row starts with an array index followed by ": ".
     * Then it adds every key and value (key=value) that have a corresponding index. Every "next" reference is
     * represented as an arrow like this " -> ".
     * <p>
     * E.g. imagine a table, where the key is a string username, and the value is the number of points of that user.
     * Is this case method toString can return something like this:
     * <pre>
     * 0: johnny=439
     * 1:
     * 2: madmax=833 -> leon=886
     * 3:
     * 4: altea=553
     * 5:
     * 6:
     * 7:
     * </pre>
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            if (node != null && node.next == null) {
                builder.append(i).append(": ").append(node.key).append("=").append(node.value).append("\n");
            } else {
                builder.append(i).append(": ");
                while (node != null) {
                    builder.append(node.key).append("=").append(node.value);
                    if (node.next != null) {
                        builder.append(" -> ");
                    }
                    node = node.next;
                }
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Creates a new underlying table with a given size and adds all elements to the new table.
     * <p>
     * In order to allow a fast access, this hash table needs to have a sufficient capacity.
     * (You can imagine a hash table, with a default capacity of 8 that stores hundreds of thousands of elements.
     * In that case it's just 8 huge linked lists. That's why we need this method.)
     * <p>
     * PLEASE NOTE that such method <strong>should not be a part of the public API</strong>, but it was made public
     * for learning purposes. You can create a table, print it using toString, then resizeTable and print it again.
     * It will help you to understand how it works.
     *
     * @param newCapacity a size of the new underlying array
     */
    public void resizeTable(int newCapacity) {
        Node<K, V>[] newTable = new Node[newCapacity];
        for (Node<K, V> kvNode : table) {
            Node<K, V> node = kvNode;
            while (node != null) {
                int newIndex = calculateIndex(node.key, newTable.length);
                Node<K, V> newNode = newTable[newIndex];

                if (newNode != null) {
                    Node<K, V> lastNode = newNode;
                    while (newNode != null) {
                        lastNode = newNode;
                        newNode = newNode.next;
                    }
                    if (lastNode.next != null && !lastNode.next.key.equals(node.key)) {
                        lastNode.next = node;
                    }
                } else {
                    newTable[newIndex] = node;
                }

                node = node.next;
            }
        }

        table = newTable;
    }


    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

    }
}
