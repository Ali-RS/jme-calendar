package com.jayfella.jme.calendar;

import com.jme3.math.Vector3f;

import java.util.Calendar;

/**
 * This interface should be implemented by classes that provide
 * sun and moon data to the sky.
 *
 * @author Andreas
 */
public interface PositionProvider {

    /**
     * This method should return the direction to the sun. It needs to
     * be normalized.
     *
     * @return The sun direction.
     */
    public Vector3f getSunDirection();

    /**
     * See getSunDirection.
     *
     * @return The moon direction.
     */
    public Vector3f getMoonDirection();

    /**
     * Get the current phase of the moon.
     *
     * @return The moon-phase. 0 is new mooon.
     */
    public int getMoonPhase();

    public String getMoonPhaseVerbose();

    /**
     * Get the maximum/minimum normalized height (y-max/y-min).
     */
    public float getMaxHeight();
    public float getMinHeight();


    /**
     * Should be run every frame.
     * @param tpf
     */
    public void update(float tpf);
}
