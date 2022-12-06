package com.technototes.library.util;

/** Class with various math functions
 * @author Alex Stedman
 */
public class MathUtils {

    /** Get the max of supplied doubles
     *
     * @param args The doubles
     * @return The max
     */
    public static double getMax(double... args) {
        double max = args[0];
        for (int i = 1; i < args.length; i++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    /** Get the max of supplied ints
     *
     * @param args The ints
     * @return The max
     */
    public static int getMax(int... args) {
        int max = 0;
        for (int i = 1; i < args.length; i++) {
            max = Math.max(args[i - 1], args[i]);
        }
        return max;
    }

    /** Calculate pythagorean theorem of any number of sides
     *
     * @param vals The sides
     * @return The hypotenuse
     */
    public static double pythag(double... vals) {
        double total = 0;
        for (double d : vals) {
            total += d * d;
        }
        return Math.sqrt(total);
    }

    /** Constrain the supplied int
     *
     * @param min The minimum of the constraint
     * @param num The number to constrain
     * @param max The maximum of the constraint
     * @return The constrained number
     */
    public static int constrain(int min, int num, int max) {
        return num < min ? min : (num > max ? max : num);
    }

    /** Constrain the supplied double
     *
     * @param min The minimum of the constraint
     * @param num The number to constrain
     * @param max The maximum of the constraint
     * @return The constrained number
     */
    public static double constrain(double min, double num, double max) {
        return num < min ? min : (num > max ? max : num);
    }

    /** Calculate if the supplied number is prime
     *
     * @param number The number to check
     * @return If number is prime
     */
    public static boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static double map(
        double x,
        double in_min,
        double in_max,
        double out_min,
        double out_max
    ) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double closestTo(double d, double... values) {
        int lowestDif = 0;
        for (int i = 1; i < values.length; i++) {
            if (Math.abs(values[lowestDif] - d) > Math.abs(values[i] - d)) lowestDif = i;
        }
        return values[lowestDif];
    }

    public static int closestTo(double d, int... values) {
        int lowestDif = 0;
        for (int i = 1; i < values.length; i++) {
            if (Math.abs(values[lowestDif] - d) > Math.abs(values[i] - d)) lowestDif = i;
        }
        return values[lowestDif];
    }
}
