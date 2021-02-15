package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Model of simple hash map.
 *
 * @param <K> key of entry.
 * @param <V> value of entry.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Constant default capacity for creating new <code>SimpleHashtable</code>.
     */
    private static final int DEFAULT_TABLE_CAPACITY = 16;

    /**
     * Internal collection of stored entries.
     */
    private TableEntry<K, V>[] table;

    /**
     * Number of entries currently stored in this collection.
     */
    private int size;

    /**
     * Counter of structural modifications over this collection.
     */
    private int modificationCount;

    /**
     * Constructs new <code>SimpleHashtable</code>
     */
    public SimpleHashtable() {
        this(DEFAULT_TABLE_CAPACITY);
    }

    /**
     * Constructs new <code>SimpleHashtable</code> with given initial capacity
     * of first bigger power of number 2 if <code>initialCapacity</code> is not
     * power of number 2.
     *
     * @param initialCapacity initial capacity for new <code>SimpleHashtable</code>.
     * @throws IllegalArgumentException if given <code>initialCapacity</code> is smaller than 1.
     */
    public SimpleHashtable(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException("Initial capacity must be greater or equal 1.");
        initialCapacity = determineCapacity(initialCapacity);
        this.size = 0;
        this.table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];
        this.modificationCount = 0;
    }

    /**
     * Adds new entry into collection and returns <code>null</code>.
     * If given Key already exists in this collection
     * replaces old value with given value and returns old value.
     *
     * @param key key of new entry.
     * @param value value of new entry.
     * @return returns <code>null</code> or if given key already exists in this collection returns old value of this key.
     * @throws NullPointerException if given key is <code>null</code>.
     */
    public V put(K key, V value) {
        /*checks if key is null*/
        if (key == null)
            throw new NullPointerException("Key can not be null.");
        /*checks if capacity is under 75 percent*/
        if (((this.size * 1.0) / this.table.length) >= 0.75) {
            doubleCapacity();
            this.modificationCount++;
        }

        /*determines index based on key's hashCode*/
        int index = index(key);
        /*checks if position on calculated index is occupied*/
        if (this.table[index] == null) {
            this.table[index] = new TableEntry<>(key, value, null);
        } else {
            TableEntry<K, V> current;
            /*checks if any entry in list on calculated index have given key*/
            for (current = this.table[index]; current != null; current = current.next) {
                /*if there is entry with given key rewrite value and return old value*/
                if (current.getKey().equals(key)) {
                    V returnValue = current.getValue();
                    current.setValue(value);
                    return returnValue;
                }
            }
            /*We are here only if there is no entry with given key but position on calculated index is occupied.
            * We are creating new entry and adding it as tail of list of elements on this index*/
            TableEntry<K, V> newEntry = new TableEntry<>(key, value, null);
            TableEntry<K, V> tmp;
            for (tmp = this.table[index]; tmp.next != null; tmp = tmp.next) {}
            tmp.next = newEntry;
        }
        this.size++;
        this.modificationCount++;
        return null;
    }

    /**
     * Doubles capacity of internal array of elements
     * and copies all stored elements in new array
     * with doubled capacity.
     */
    private void doubleCapacity() {
        TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[this.table.length * 2];
        TableEntry<K,V>[] elements = this.toArray();
        this.table = newTable;
        this.size = 0;
        for (TableEntry<K, V> e : elements)
            this.put(e.getKey(), e.getValue());
    }

    /**
     * Determines on which index of collection this element will be stored.
     *
     * @param key key of entry
     * @return returns index of this collection on which entry will be stored.
     */
    private int index(K key) {
        return Math.abs(key.hashCode()) % this.table.length;
    }

    /**
     * Returns value of entry with given key.
     *
     * @param key key of entry.
     * @return returns value of entry with given key or <code>null</code> if this collection do not contains given key.
     */
    public V get(Object key) {

        /*if this collection do not contain key return null*/
        if (!this.containsKey(key))
            return null;
        /*if collection contains key search for entry with that key on index calculated by hashCode of key*/
        TableEntry<K, V> current;
        for (current = this.table[index((K) key)]; current != null && current.getKey() != key; current = current.next) {
            if (current.getKey().equals(key))
                break;
        }
        return current.getValue();
    }

    /**
     * Returns number of elements currently stored in this collection.
     *
     * @return returns number of elements currently stored in this collection.
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns true only if this collection contains given key
     *
     * @param key key to search in collection
     * @return returns true only if this collection contains given key, false otherwise.
     */
    public boolean containsKey(Object key) {
        if (key == null)
            return false;

        for (TableEntry<K, V> current = this.table[index((K) key)]; current != null; current = current.next)
            if (current.getKey().equals(key))
                return true;
        return false;
    }

    /**
     * Returns true when finds first occurrence of given value.
     * Searches given value from slots with smaller index towards end.
     * If this collection do not contain given value returns <code>null</code>.
     *
     * @param value searched value.
     * @return returns true if this collection contains entry with give value, false otherwise.
     */
    public boolean containsValue(Object value) {
        for (int i = 0; i < this.table.length; i++) {
            for (TableEntry<K, V> current = this.table[i]; current != null; current = current.next) {
                /*value can be null so if we call method equals we get NullPointerException*/
                if (current.getValue() == null) {
                    if (value == null) {
                        return true;
                    }
                    continue;
                }
                /*if value of current is other than null compare it with method equals*/
                if (current.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes entry with given key. If entry with given key
     * exists in this collection return value of that entry,
     * <code>null</code> otherwise.
     *
     * @param key key of entry to remove.
     * @return returns value of removed entry, if entry with given key do not exist in this collection returns <code>null</code>.
     */
    public V remove(Object key) {
        if (!this.containsKey(key))
            return null;
        /*if entry with given key is head of list remove it from head of list and put entry's next as head*/
        if (this.table[index((K) key)].getKey().equals(key)) {
            V returnValue = this.table[index((K) key)].getValue();
            this.table[index((K) key)] = this.table[index((K) key)].next;
            this.size--;
            this.modificationCount++;
            return returnValue;
        } else {
            TableEntry<K, V> previous = this.table[index((K) key)];
            TableEntry<K, V> current;
            for (current = previous.next; !current.getKey().equals(key); previous = current, current = previous.next)
                continue;
            previous.next = current.next;
            this.size--;
            this.modificationCount++;
            return current.getValue();
        }
    }

    /**
     * Returns true only if this collection has no elements stored.
     *
     * @return returns true only if this collection has no elements stored, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Removes all elements from this collection
     */
    public void clear() {
        Arrays.fill(this.table, null);
        this.size = 0;
        this.modificationCount++;
    }

    /**
     * Returns string representation of this collection.
     *
     * @return returns string representation of this collection.
     */
    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] == null)
                continue;
            for (TableEntry<K, V> entry = this.table[i]; entry != null; entry = entry.next) {
                if (!s.equals("[")) {
                    s += ", " + entry.toString();
                } else {
                    s += entry.toString();
                }
            }
        }
        return s + "]";
    }

    /**
     * Returns new array of all elements of this collection.
     *
     * @return returns new array of all elements of this collection.
     */
    public TableEntry<K, V>[] toArray() {
        TableEntry<K, V>[] array = (TableEntry<K, V>[]) new TableEntry[this.size()];
        int index = 0;
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] == null)
                continue;
            for (TableEntry<K, V> entry = this.table[i]; entry != null; entry = entry.next) {
               array[index++] = entry;
            }
        }
        return array;
    }

    /**
     * Sets initial capacity to next power of number 2 if given
     * <code>initialCapacity</code> is not power of 2.
     *
     * @param initialCapacity given initial capacity.
     * @return returns initial capacity that is power of number 2.
     */
    private static int determineCapacity(int initialCapacity) {
        int i = 1;
        while (i < initialCapacity) {
            i *=2;
        }
        return i;
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        /**
         * Cursor to current element.
         */
        private TableEntry<K, V> current;

        /**
         * Current index of iteration.
         */
        private int index;

        /**
         * Count of modifications over collection in the moment of constructing this iterator.
         */
        private int savedModificationCount;

        /**
         * Constructs new iterator
         */
        public IteratorImpl() {
            int i = 0;
            while (SimpleHashtable.this.table[i] == null)
                i++;
            this.current = SimpleHashtable.this.table[i];
            //this.index = 0;
            this.savedModificationCount = SimpleHashtable.this.modificationCount;
        }

        /**
         * Returns true if the iteration has more elements.
         *
         * @return returns true if the iteration has more elements.
         * @throws ConcurrentModificationException if collection has been modified since construction of this iterator.
         */
        @Override
        public boolean hasNext() {
            if (savedModificationCount != SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException("Collection has been modified since construction of this iterator.");

            if (current.next != null)
                return true;
            int i = 0;
            /*searches for next non null element of hash table*/
            while (index + i < SimpleHashtable.this.table.length) {
                if (SimpleHashtable.this.table[index + i] != null) {
                    return true;
                }
                i++;
            }

            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return returns the next element in the iteration.
         * @throws NoSuchElementException if the iteration has no more elements.
         * @throws ConcurrentModificationException if collection has been modified since construction of this iterator.
         */
        @Override
        public TableEntry<K, V> next() {
            if (savedModificationCount != SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException("Collection has been modified since construction of this iterator.");

            if (!hasNext())
                throw new NoSuchElementException("No more elements.");

            if (current.next != null && index != 0) {
                current = current.next;
            } else {
                while (SimpleHashtable.this.table[index] == null)
                    index++;
                current = SimpleHashtable.this.table[index];
                index++;
            }
            return current;
        }

        /**
         * Removes from the underlying collection
         * the last element returned by this iterator.
         * This method can be called only once per call to next().
         *
         * @throws IllegalStateException if the next method has not yet been called, or the remove method has already been called after the last call to the next method.
         * @throws ConcurrentModificationException if collection has been modified since construction of this iterator.
         */
        @Override
        public void remove() {
            if (savedModificationCount != SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException("Collection has been modified since construction of this iterator.");

            if (current.getValue() == null) {
                if (SimpleHashtable.this.containsKey(current.getKey())) {
                    SimpleHashtable.this.remove(current.getKey());
                } else {
                    throw new IllegalStateException("Can not invoke method remove if the next method has not yet been called.");
                }
                /*if (current.getValue() != SimpleHashtable.this.remove(current.getKey())) {
                    throw new IllegalStateException("Can not invoke method remove if the next method has not yet been called.");
                }*/
            } else if (!current.getValue().equals(SimpleHashtable.this.remove(current.getKey())))
                throw new IllegalStateException("Can not invoke method remove if the next method has not yet been called.");
            this.savedModificationCount++;
        }
    }


    /**
     * Model of one entry in hash map.
     *
     * @param <K> unique key of entry.
     * @param <V> value of entry.
     */
    public static class TableEntry<K, V> {

        /**
         * Key of entry.
         */
        private K key;

        /**
         * Value of entry.
         */
        private V value;

        /**
         * Reference to next entry in this slot.
         */
        private TableEntry<K, V> next;

        /**
         * Constructs new entry.
         *
         * @param key key of entry.
         * @param value value of entry.
         */
        public TableEntry(K key, V value, TableEntry<K, V> entry) {
            this.key = key;
            this.value = value;
            this.next = entry;
        }

        /**
         * @return returns key of this entry.
         */
        public K getKey() {
            return this.key;
        }

        /**
         * @return returns value of this entry.
         */
        public V getValue() {
            return this.value;
        }

        /**
         * Sets value of this entry to new given value.
         *
         * @param value new value of this entry.
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * @return returns string representation of entry.
         */
        @Override
        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }

        /**
         * Checks if given object is equal to this one
         * based on their keys.
         *
         * @param o object to compare.
         * @return returns true if given object has same key as this entry, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableEntry<?, ?> that = (TableEntry<?, ?>) o;
            return getKey().equals(that.getKey());
        }
    }
}
