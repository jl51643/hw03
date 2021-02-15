package hr.fer.oprpp1.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {

    @Test
    public void getXTest() {
        Vector2D v = new Vector2D(2, 5);
        assertEquals(2, v.getX());
        Vector2D v1 = new Vector2D(0.0, 0.0);
        assertEquals(0.0, v1.getX());
    }

    @Test
    public void getYTest() {
        Vector2D v = new Vector2D(2, 5);
        assertEquals(5, v.getY());
        Vector2D v1 = new Vector2D(0.0, 0.0);
        assertEquals(0.0, v1.getY());
    }

    @Test
    public void addTest() {
        Vector2D v = new Vector2D(2, 5);
        Vector2D v1 = new Vector2D(3.6, 4.7);
        v.add(v1);
        assertEquals(5.6, v.getX());
        assertEquals(9.7, v.getY());
    }

    @Test
    public void addedTest() {
        Vector2D v = new Vector2D(2, 5);
        Vector2D v1 = new Vector2D(3.6, 4.7);
        Vector2D v2 = v.added(v1);
        assertEquals(5.6, v2.getX());
        assertEquals(9.7, v2.getY());
    }

    @Test
    public void rotateTest() {
        Vector2D v = new Vector2D(4, 5);
        v.rotate(1.3);
        assertTrue(Math.abs(-3.7477956125 - v.getX()) < 1E-10);
        assertTrue(Math.abs(5.1917268847 - v.getY()) < 1E-10);

    }

    @Test
    public void rotatedTest() {
        Vector2D v = new Vector2D(4, 5);
        Vector2D v1 = v.rotated(1.3);
        assertTrue(Math.abs(-3.7477956125 - v1.getX()) < 1E-10);
        assertTrue(Math.abs(5.1917268847 - v1.getY()) < 1E-10);
    }

    @Test
    public void scaleTest() {
        Vector2D v = new Vector2D(2, 3);
        v.scale(4);
        assertEquals(8, v.getX());
        assertEquals(12, v.getY());
    }

    @Test
    public void scaledTest() {
        Vector2D v = new Vector2D(2, 3);
        Vector2D v1 = v.scaled(4);
        assertEquals(8, v1.getX());
        assertEquals(12, v1.getY());
    }

    @Test
    public void copyTest() {
        Vector2D v = new Vector2D(2.3, 4.5);
        Vector2D v1 = v.copy();
        assertEquals(v.getX(), v1.getX());
        assertEquals(v.getY(), v1.getY());
    }
}