package hr.fer.oprpp1.custom.collections;

/**
 * Thrown if application tries to pop elements from empty stack.
 */
public class EmptyStackException extends RuntimeException{

    /**
     * Serial version of class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs new EmptyStackException with <code>null</code> as its detail message.
     * The cause is not initialized.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructs a new EmptyStackException with the specified detail message.
     * The cause is not initialized.
     *
     * @param message the detail message.
     */
    public EmptyStackException(String message) {
        super(message);
    }

    /**
     * Constructs a new EmptyStackException with the specified cause and a detail
     * message of <code>(cause==null ? null : cause.toString())</code>.
     *
     * @param cause the cause.
     */
    public EmptyStackException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new EmptyStackException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause.
     */
    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }
}
