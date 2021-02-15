package hr.fer.oprpp1.custom.collections;

/**
 * Model which represents some general collection of objects.
 */
public interface Collection<T> {

    /**
     * Checks if collection is empty.
     *
     * @return true if collection contains no objects and false otherwise.
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of currently stored objects in this collection.
     *
     * @return number of currently stored objects in this collection.
     */
    int size();

    /**
     * Adds the given object into this collection.
     *
     * @param value object to add in collection.
     */
    void add(T value);

    /**
     * Returns true only if this collection contains given object.
     *
     * @param value object to check if it is in this collection.
     * @return returns true if this collection contains given object and false otherwise.
     */
    boolean contains(Object value);

    /**
     * Returns true only if the collection contains given value and removes
     * one occurrence of it.
     *
     * @param value Object to remove.
     * @return returns true if the collection contains given value and false otherwise.
     */
    boolean remove(T value);

    /**
     * Allocates new array with size equals to the size of this collections,
     * fills it with collection content and returns the array.
     *
     * @return new array of objects contained in this collection.
     */
    Object[] toArray();

    /**
     * Calls <code>processor.process(.)</code> for each element of this collection.
     *
     * @param processor proces to be executed for each element of this collection.
     */
    default void forEach(Processor<? super T> processor) {
        ElementsGetter<? extends T> getter = this.createElementsGetter();
        while (getter.hasNextElement()) {
            processor.process(getter.getNextElement());
        }
    }

    /**
     * Method adds into the current collection all elements from the given collection.
     *
     * @param other collection which elements will be added in current collection.
     */
    default void addAll(Collection<? extends T> other) {

        class AddAllProcessor implements Processor<T> {

            @Override
            public void process(T value) {
                Collection.this.add(value);
            }
        }

        AddAllProcessor processor = new AddAllProcessor();
        other.forEach(processor);
    }

    /**
     * Removes all elements from this collection.
     */
    void clear();

    /**
     * Creates new ElementsGetter.
     *
     * @return returns new ElementsGetter.
     */
    ElementsGetter<T> createElementsGetter();

    /**
     * Appends all elements from given collection to this collection only if elements satisfy given test.
     *
     * @param col collection with elements to test and append.
     * @param tester test for collection elements.
     */
    default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {

        ElementsGetter<? extends T> getter = col.createElementsGetter();
        while (getter.hasNextElement()) {
            T nextElement = getter.getNextElement();
            if (tester.test(nextElement)) {
                this.add(nextElement);
            }
        }
    }
}
