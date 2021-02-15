package hr.fer.oprpp1.custom.collections;

/**
 * Model of list of objects
 */
public interface List<T> extends Collection<T>{

    /**
     * Returns object from collection on position <code>index</code>.
     *
     * @param index position of element to return.
     * @return returns object from collection on position <code>index</code>.
     * @throws IndexOutOfBoundsException if given <code>index</code> is smaller than 0 or greater than size-1 of this collection.
     */
    Object get(int index);

    /**
     * Inserts the given value at the given position.
     *
     * @param value Object to be inserted into collection.
     * @param position Position on which object will be inserted.
     * @throws IndexOutOfBoundsException if given position is smaller than 0 or greater than size of this collection.
     * @throws NullPointerException if given value is null.
     */
    void insert(T value, int position);

    /**
     * Searches the collection and returns the index of the first occurrence
     * of the given value or -1 if the value is not found
     *
     * @param value value to search.
     * @return index of first occurrence of the given value or -1 if the value is not found
     */
    int indexOf(T value);

    /**
     * Removes element at specified <code>index</code>> from collection.
     *
     * @param index Index od element to remove.
     * @throws IndexOutOfBoundsException if <code>index</code> is smaller than 0 or greater than size-1 of this collection.
     */
    void remove(int index);
}
