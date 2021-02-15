package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Model of collection of data with unique non null key and value given for that key.
 *
 * @param <K> key of dictionary entry.
 * @param <V> value of dictionary entry.
 */
public class Dictionary<K, V> {

    /**
     * Internal collection of entries with key and value.
     */
    private ArrayIndexedCollection<Entry<K, V>> dictionary;

    public Dictionary() {
        this.dictionary = new ArrayIndexedCollection<>();
    }


    /**
     * Returns true if dictionary collection is empty.
     *
     * @return returns true if dictionary collection is empty.
     */
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    /**
     * Returns number of elements currently stored in this dictionary collection.
     *
     * @return returns number of elements currently stored in this dictionary collection.
     */
    public int size() {
        return this.dictionary.size();
    }

    /**
     * Removes all elements from this dictionary collection.
     */
    public void clear() {
        this.dictionary.clear();
    }

    /**
     * Adds new element to this dictionary collection.
     * If given key already exists in collection rewrites
     * value for this key and returns old value, otherwise returns <code>null</code>.
     *
     * @param key key of new element.
     * @param value value for given key.
     * @return returns old value of given key already exists in this collection, otherwise returns <code>null</code>.
     * @throws NullPointerException if given key is <code>null</code>.
     */
    public V put(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        if (containsKey(key)) {
            return rewriteValue(key, value);
        } else {
            this.dictionary.add(entry);
            return null;
        }
    }

    private V rewriteValue(K key, V value) {
        V v = null;
        for (int i = 0; i < this.dictionary.size(); i++) {
            if (this.dictionary.get(i).getKey().equals(key)) {
                v = this.dictionary.get(i).getValue();
                this.dictionary.get(i).setValue(value);
            }
        }
        return v;
    }

    /**
     * Returns value for given key.
     *
     * @param key key of element which value will be returned.
     * @return returns value for given key or returns <code>null</code> if given key do not exist in this dictionary collection.
     */
    public V get(Object key) {
        if (containsKey((K) key)) {
            return getValue((K) key);
        }
        return null;

    }

    /**
     * Removes element with given key from this dictionary collection and returns value stored with that key.
     * If element with given key do not exists returns <code>null</code>.
     *
     * @param key key of element which will be removed from this dictionary collection.
     * @return returns value stored with given key or <code>null</code> if element with given key do not exist in this collection.
     */
    public V remove(K key) {
        if (containsKey(key)) {
            V value = getValue(key);
            this.dictionary.remove(new Entry<>(key, value));
            return value;
        }
        return null;
    }

    /**
     * Returns true only if this dictionary collection contains element with given key.
     *
     * @param key key to check if in collection
     * @return returns true only if this dictionary collection contains element with given key, false otherwise.
     */
    private boolean containsKey(K key) {
        final boolean[] contains = {false};
        this.dictionary.forEach(entry -> {
            if (entry.getKey().equals(key)) {
                contains[0] = true;
            }
        });
        return contains[0];
    }

    /**
     * Returns value of entry with given key.
     *
     * @param key key of element which value will be returned.
     * @return returns value of entry with given key or <code>null</code> if given key do not exists in this collection.
     */
    private V getValue(K key) {
        for (int i = 0; i < this.dictionary.size(); i++) {
            if (this.dictionary.get(i).getKey().equals(key)) {
                return this.dictionary.get(i).getValue();
            }
        }
        return null;
    }

    /**
     * Model of on element in dictionary collection.
     *
     * @param <K> key of element.
     * @param <V> value of element.
     */
    private class Entry<K, V> {

        /**
         * key of element.
         */
        private K key;

        /**
         * value of element for given key
         */
        private V value;

        /**
         * Constructs one element of dictionary with key and value.
         *
         * @throws NullPointerException if given key is null
         */
        public Entry(K key, V value) {
            if (key == null)
                throw new NullPointerException();
            this.key = key;
            this.value = value;
        }

        /**
         * @return returns key of this entry.
         */
        private K getKey() {
            return this.key;
        }

        /**
         * @return returns value of this entry.
         */
        private V getValue() {
            return this.value;
        }

        /**
         * Sets value of this entry to new given value
         *
         * @param value new value of this entry
         */
        private void setValue(V value) {
            this.value = value;
        }

        /**
         * @param o object to compare key.
         * @return returns equals if two entries have equal key.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return key.equals(entry.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
