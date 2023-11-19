package com.technototes.library.logger;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** The root annotation for annotation logging, also doubles as a basic string log
 * @author Alex Stedman
 */
@Documented
@Repeatable(Log.Logs.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { FIELD, LOCAL_VARIABLE, METHOD })
public @interface Log {
    /** Store index for this annotation (position in telemetry)
     *
     * @return The index
     */
    int index() default -1;

    /** Store priority for this log entry (to pick the most wanted entry over others with same index)
     *
     * @return The priority
     */
    int priority() default -1;

    /** Store the name for this annotation to be be beside
     *
     * @return The name as a string
     */
    String name() default "";

    /** The format for the logged String
     *
     * @return The format
     */
    String format() default "%s";

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.METHOD })
    @interface Logs {
        Log[] value();
    }

    /** Log a number
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = { FIELD, LOCAL_VARIABLE, METHOD })
    @interface Number {
        /** Store index for this annotation (position in telemetry)
         *
         * @return The index
         */
        int index() default -1;

        /** Store priority for this log entry (to pick the most wanted entry over others with same index)
         *
         * @return The priority
         */
        int priority() default -1;

        /** Store the name for this annotation to be be beside
         *
         * @return The name as a string
         */
        String name() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = { FIELD, LOCAL_VARIABLE, METHOD })
    @interface Boolean {
        /** Store index for this annotation (position in telemetry)
         *
         * @return The index
         */
        int index() default -1;

        /** Store priority for this log entry (to pick the most wanted entry over others with same index)
         *
         * @return The priority
         */
        int priority() default -1;

        /** Store the string when the annotated method returns true
         *
         * @return The string
         */
        String trueValue() default "true";

        /** Store the string when the annotated method returns false
         *
         * @return The string
         */
        String falseValue() default "false";

        /** Store the name for this annotation to be be beside
         *
         * @return The name as a string
         */
        String name() default "";
    }
}
