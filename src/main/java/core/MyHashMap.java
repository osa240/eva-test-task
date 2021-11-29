package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MyHashMap<K extends Long, V> implements Map<Long, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private static final int GROW_COEFFICIENT = 2;
    private Node<Long, V>[] table;
    private int size;
    private int threshold;

    private class Node<K extends Long, V> {
        private final Long key;
        private V value;
        private Node<Long, V> next;

        public Node(Long key, V value, Node<Long, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    {
        table = new Node[DEFAULT_INITIAL_CAPACITY];
        threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (Node<Long, V> node : table) {
            while (node != null) {
                if (node.key.equals(key)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node<Long, V> node : table) {
            while (node != null) {
                if (node.value.equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Node<Long, V> temporaryNode = table[index((Long) key)];
        while (temporaryNode != null) {
            if (Objects.equals(key, temporaryNode.key)) {
                return temporaryNode.value;
            }
            temporaryNode = temporaryNode.next;
        }
        return null;
    }

    @Override
    public V put(Long key, V value) {
        resize();
        Node<Long, V> node = table[index(key)];
        while (node != null) {
            if (Objects.equals(key, node.key)) {
                node.value = value;
                return value;
            }
            if (node.next == null) {
                node.next = new Node<>(key, value, null);
                size++;
                return value;
            }
            node = node.next;
        }
        table[index(key)] = new Node<>(key, value, null);
        size++;
        return value;
    }

    @Override
    public V remove(Object key) {
        Node<Long, V> temporaryNode = table[index((Long) key)];
        while (temporaryNode != null) {
            if (Objects.equals(key, temporaryNode.key)) {
                V oldValue = temporaryNode.value;
                temporaryNode = temporaryNode.next;
                size--;
                return oldValue;
            }
            temporaryNode = temporaryNode.next;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends V> map) {
        for (Entry<? extends Long, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                table[i] = null;
            }
        }
        size = 0;
    }

    @Override
    public Set<Long> keySet() {
        Set<Long> setKeys = new HashSet<>();
        for (Node<Long, V> node : table) {
            setKeys.add(node.key);
        }
        return setKeys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Node<Long, V> node : table) {
            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }
        return values;
    }

    @Override
    public Set<Entry<Long, V>> entrySet() {
        Set<Entry<Long, V>> entrySet = new LinkedHashSet<>();
        for (Node<Long, V> node : table) {
            while (node != null) {
                Node<Long, V> finalNode = node;
                Entry<Long, V> entry = new Entry<Long, V>() {
                    @Override
                    public Long getKey() {
                        return finalNode.key;
                    }

                    @Override
                    public V getValue() {
                        return finalNode.value;
                    }

                    @Override
                    public V setValue(V value) {
                        return null;
                    }
                };
                entrySet.add(entry);
                node = node.next;
            }
        }
        return entrySet;
    }

    private void resize() {
        if (size != threshold) {
            return;
        }
        Node<Long, V>[] temporaryTable = table;
        table = new Node[table.length * GROW_COEFFICIENT];
        size = 0;
        for (Node<Long, V> node : temporaryTable) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
        threshold = (int) (table.length * DEFAULT_LOAD_FACTOR);
    }

    private int index(Long key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % table.length;
    }
}
