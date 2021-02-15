package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public interface ElementsGetter<T> {

    /**
     * Returns true only if there is more elements of collection to get.
     *
     * @return returns true only if there is more elements of collection to get, false otherwise.
     * @throws ConcurrentModificationException if collection has been modified since declaration of this ElementsGetter
     */
    boolean hasNextElement();

    /**
     * Returns next object from collection.
     *
     * @return returns next element from collection.
     * @throws NoSuchElementException if all elements of collection are given.
     * @throws ConcurrentModificationException if collection has been modified since declaration of this ElementsGetter
     */
    T getNextElement();

    default void processRemaining(Processor<? super T> p) {
        while (hasNextElement()) {
            p.process(getNextElement());
        }
    }
}
