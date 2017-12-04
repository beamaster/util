/**
 *
 */
package com.steam.util.string;

/**
 * Utils - ArrayUtils
 *
 * @author farry
 * @version 3.0
 */
public class ArrayUtils {

    public static Long[] mergerArray(Long[] a, Long[] b) {
        Long[] mergerArray = new Long[a.length + b.length];
        System.arraycopy(b, 0, mergerArray, a.length, b.length);
        System.arraycopy(a, 0, mergerArray, 0, a.length);
        return mergerArray;
    }
}
