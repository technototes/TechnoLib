package com.technototes.library.logger;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.technototes.library.hardware.HardwareDevice;
import com.technototes.library.logger.entry.BooleanEntry;
import com.technototes.library.logger.entry.Entry;
import com.technototes.library.logger.entry.NumberEntry;
import com.technototes.library.logger.entry.StringEntry;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The class to manage logging
 *
 * @author Alex Stedman
 */
public class Logger {

    private static Set<String> hardwareToLog = new TreeSet<>();

    public static void LogHardware(String names) {
        if (names == null || names.length() == 0) {
            hardwareToLog = new TreeSet<>();
        } else {
            hardwareToLog = new TreeSet<String>(Arrays.asList(names.split(";")));
        }
    }

    public Entry<?>[] runEntries;
    public Entry<?>[] initEntries;
    private final Set<Entry<?>> unindexedRunEntries;
    private final Set<Entry<?>> unindexedInitEntries;
    private final Set<Object> recordedAlready;
    private final Telemetry telemetry;
    private final OpMode opMode;
    /**
     * The divider between the tag and the entry for telemetry (default ':')
     */
    public char captionDivider = ':';

    /**
     * Instantiate the logger
     *
     * @param op The OpMode class
     */
    public Logger(OpMode op) {
        opMode = op;
        telemetry = op.telemetry;
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.HTML);
        unindexedRunEntries = new LinkedHashSet<>();
        unindexedInitEntries = new LinkedHashSet<>();
        recordedAlready = new LinkedHashSet<>();
        configure(op);
        runEntries = generate(unindexedRunEntries);
        initEntries = generate(unindexedInitEntries);
    }

    private void configure(Object root) {
        for (Field field : root.getClass().getFields()) {
            try {
                Object o = field.get(root);
                if (!recordedAlready.contains(o) && isFieldAllowed(field)) {
                    if (o instanceof Loggable) {
                        configure(o);
                        recordedAlready.add(o);
                    } else if (
                        field.isAnnotationPresent(Log.class) ||
                        field.isAnnotationPresent(Log.Number.class) ||
                        field.isAnnotationPresent(Log.Boolean.class)
                    ) {
                        if (field.getType().isPrimitive() || o instanceof String) {
                            set(field.getDeclaredAnnotations(), field, root);
                            System.out.println("prim");
                        } else if (getCustom(o) != null) {
                            set(field.getDeclaredAnnotations(), getCustom(o));
                            System.out.println("cust");
                        }
                    }
                }
            } catch (IllegalAccessException ignored) {
                System.out.println("reeeeeeeeeeeeeeeeeeee");
            }
        }
        for (Method m : root.getClass().getMethods()) {
            set(m.getDeclaredAnnotations(), m, root);
        }
    }

    // TODO make list and do sort with comparators
    // I wish this had a comment describing what Alex thinks it's doing,
    // I *think* it's trying to set the 'indexed' entries to their preferred locations
    // then filling in the gaps with unindexed  or lower priority entries.
    // That bottom loop is also quite slow, but we're talking about 0-20 entries, so performance
    // is probably irrelevant...
    private Entry<?>[] generate(Set<Entry<?>> a) {
        Entry<?>[] returnEntry = new Entry[a.size()];
        List<Entry<?>> unindexed = new ArrayList<>();
        for (Entry<?> e : a) {
            int index = e.getIndex();
            if (index >= 0 && index < returnEntry.length) {
                Entry<?> other = returnEntry[index];
                if (other == null) {
                    returnEntry[index] = e;
                } else {
                    if (e.getPriority() > other.getPriority()) {
                        unindexed.add(other);
                        returnEntry[index] = e;
                    } else {
                        unindexed.add(e);
                    }
                }
            } else {
                unindexed.add(e);
            }
        }
        for (int i = 0; unindexed.size() > 0; i++) {
            if (returnEntry[i] == null) {
                returnEntry[i] = unindexed.remove(0);
            }
        }
        return returnEntry;
    }

    private void update(Entry<?>[] choice) {
        if (!hardwareToLog.isEmpty()) {
            for (HardwareDevice<?> hw : HardwareDevice.devices) {
                if (hardwareToLog.contains(hw.getName())) {
                    // Add the line to the log
                    telemetry.addLine(hw.LogLine());
                }
            }
        }

        try {
            for (Entry<?> item : choice) {
                // telemetry.addLine(
                //   (i > 9 ? i + "| " : i + " | ") +
                //   (choice[i] == null ? "" : choice[i].getTag().replace('`', captionDivider) + choice[i].toString())
                // );
                // All teh fancy HTML stuff gets in the way of the FTC Dashboard graph
                telemetry.addData(item.getName(), item.get());
            }
            telemetry.update();
        } catch (Exception ignored) {}
    }

    /**
     * Update the logged run items in temeletry
     */
    public void runUpdate() {
        update(runEntries);
    }

    /**
     * Update the logged init items in temeletry
     */
    public void initUpdate() {
        update(initEntries);
    }

    private void set(Annotation[] a, Method m, Object root) {
        set(a, () -> {
            try {
                return m.invoke(root);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private void set(Annotation[] a, Field m, Object root) {
        set(a, () -> {
            try {
                return m.get(root);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @SuppressWarnings({ "unchecked" })
    private void set(Annotation[] a, Supplier<?> m) {
        boolean init = false, run = true;
        Entry<?> e = null;
        for (Annotation as : a) {
            if (as instanceof Log.Number) {
                e = new NumberEntry(((Log.Number) as).name(), (Supplier<Number>) m, ((Log.Number) as).index());
                e.setPriority(((Log.Number) as).priority());
            } else if (as instanceof Log) {
                e = new StringEntry(((Log) as).name(), (Supplier<String>) m, ((Log) as).index(), ((Log) as).format());
                e.setPriority(((Log) as).priority());
            } else if (as instanceof Log.Boolean) {
                e = new BooleanEntry(
                    ((Log.Boolean) as).name(),
                    (Supplier<Boolean>) m,
                    ((Log.Boolean) as).index(),
                    ((Log.Boolean) as).trueValue(),
                    ((Log.Boolean) as).falseValue()
                );
                e.setPriority(((Log.Boolean) as).priority());
            } else if (as instanceof LogConfig.Run) {
                init = ((LogConfig.Run) as).duringInit();
                run = ((LogConfig.Run) as).duringRun();
            }
        }
        if (e != null) {
            if (init) {
                unindexedInitEntries.add(e);
            }
            if (run) {
                unindexedRunEntries.add(e);
            }
        }
    }

    /**
     * Repeat a String
     *
     * @param s   The String to repeat
     * @param num The amount of times to repeat the String
     * @return The String s repeated num times
     */
    public static String repeat(String s, int num) {
        if (num > 100) {
            System.err.println("One of the entries is too long, make sure your scaling is correct");
            num = 100;
        }
        return num > 0 ? repeat(s, num - 1) + s : "";
    }

    private static Supplier<?> getCustom(Object o) {
        if (o instanceof Supplier) {
            return (Supplier<?>) o;
        } else if (o instanceof BooleanSupplier) {
            return ((BooleanSupplier) o)::getAsBoolean;
        } else if (o instanceof IntSupplier) {
            return ((IntSupplier) o)::getAsInt;
        } else if (o instanceof DoubleSupplier) {
            return ((DoubleSupplier) o)::getAsDouble;
        } else if (o instanceof Integer) {
            return () -> (Integer) o;
        } else if (o instanceof Double) {
            return () -> (Double) o;
        } else if (o instanceof Boolean) {
            return () -> (Boolean) o;
        } else {
            return null;
        }
    }

    private boolean isFieldAllowed(Field f) {
        if (f.isAnnotationPresent(LogConfig.AllowList.class)) {
            if (!Arrays.asList(f.getAnnotation(LogConfig.AllowList.class).value()).contains(opMode.getClass())) {
                return false;
            }
        }
        if (f.isAnnotationPresent(LogConfig.DenyList.class)) {
            if (Arrays.asList(f.getAnnotation(LogConfig.DenyList.class).value()).contains(opMode.getClass())) {
                return false;
            }
        }
        return !f.isAnnotationPresent(LogConfig.Disabled.class);
    }
}
