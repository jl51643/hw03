package hr.fer.oprpp1.math;

/**
 * Model of two dimensional vector
 */
public class Vector2D {

    /**
     * X component of 2D
     */
    private double x;

    /**
     * Y component of 2D-vector
     */
    private double y;

    /**
     * Constructs new 2D-vector.
     *
     * @param x argument with unit vector "i".
     * @param y argument with unit vector "j".
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return returns x component of this vector.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return returns y component of this vector.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Modifies this vector and add offset to it's components.
     *
     * @param offset vector which will be added to this vector.
     */
    public void add(Vector2D offset) {
        this.x += offset.getX();
        this.y += offset.getY();
    }

    /**
     * Returns new vector as result of addition.
     *
     * @param offset second addend in addition.
     * @return returns new vector as result of addition
     */
    public Vector2D added(Vector2D offset) {
        return new Vector2D(this.x + offset.getX(), this.y + offset.getY());
    }

    /**
     * Rotates this vector for given angle.
     *
     * @param angle angle of rotation.
     */
    public void rotate(double angle) {
        double tmpX = this.x;
        double tmpY = this.y;
        this.x = tmpX * Math.cos(angle) - tmpY * Math.sin(angle);
        this.y = tmpX * Math.sin(angle) + tmpY * Math.cos(angle);
    }

    /**
     * Returns new vector as result of rotation.
     *
     * @param angle angle of rotation.
     * @return returns new vector ad result of rotation.
     */
    public Vector2D rotated(double angle) {
        return new Vector2D(this.x * Math.cos(angle) - this.y * Math.sin(angle),
                            this.x * Math.sin(angle) + this.y * Math.cos(angle));
    }

    /**
     * Scales this vector for given scale factor.
     *
     * @param scaler scale factor
     */
    public void scale(double scaler) {
        this.x = this.x * scaler;
        this.y = this.y * scaler;
    }

    /**
     * Returns new vector as result of scaling.
     *
     * @param scaler scale factor
     * @return returns new vector as result of scaling.
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(this.x * scaler, this.y * scaler);
    }

    /**
     * Returns new copy of this vector.
     *
     * @return returns new copy of this vector.
     */
    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }
}
