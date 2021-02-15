package hr.fer.oprpp1.custom.collections;

/**
 * Model of stack-like collection
 */
public class ObjectStack<T> {

    /**
     * Collection representing stack.
     */
    private ArrayIndexedCollection<T> collection;

    /**
     * Constructing new empty stack
     */
    public ObjectStack() {
        this.collection = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if stack is empty.
     *
     * @return <code>true</code> if collection contains no objects and <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Returns the number of currently stored objects in this stack.
     *
     * @return Number of currently stored objects in this stack.
     */
    public int size() {
        return this.collection.size();
    }

    /**
     * Pushes object <code>value</code> on the top of the stack.
     *
     * @param value Value to push on this stack.
     * @throws NullPointerException if given <code>value</code> is <code>null</code>.
     */
    public void push(T value) {
        this.collection.add(value);
    }

    /**
     * Removes last value pushed on stack from stack and returns it.
     *
     * @return last value pushed on stack
     * @throws EmptyStackException if stack is empty
     */
    public T pop() {
        T valueToReturn = this.peek();
        this.collection.remove(this.collection.size()-1);
        return valueToReturn;
    }

    /**
     * Returns last element placed on stack but does not delete it from stack.
     *
     * @return last element placed on stack.
     * @throws EmptyStackException if stack is empty.
     */
    public T peek() {
        if (this.collection.size() == 0)
            throw new EmptyStackException("Stack is empty!");

        return this.collection.get(this.collection.size()-1);
    }

    /**
     * Removes all elements from stack;
     */
    public void clear() {
        this.collection.clear();
    }

}
