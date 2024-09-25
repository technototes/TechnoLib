package com.technototes.library.command;

import androidx.annotation.Nullable;
import com.technototes.library.general.Periodic;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.subsystem.Subsystem;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;

/**
 * This is a "singleton" object for scheduling commands. Most usage originates from {@link Command}
 * methods {@link Command#andThen} or {@link Command#alongWith} for example. Outside of
 * those usages, you will typically use {@code CommandScheduler.getInstance().scheduleJoystick} to
 * control a drivebase.
 *
 * <p>
 * TODO: The "CommandScheduler.getInstance()" thing is really kinda silly. Perhaps it's how WPIlib
 * TODO: works, but it's clunky and makes things messier for non-FRC programmers. I think I'm going
 * TODO: yoink that method and make static methods for next year's release...
 */
public final class CommandScheduler {

    private static final Map<Command, BooleanSupplier> commandMap = new HashMap<>();
    private static final Map<Subsystem, Set<Command>> requirementMap = new HashMap<>();
    private static final Map<Subsystem, Command> defaultMap = new HashMap<>();

    private static final Set<Periodic> registered = new LinkedHashSet<>();

    private static CommandOpMode opMode;

    /**
     * Set the scheduler's opmode
     *
     * @param c the opmode
     */
    public static void setOpMode(CommandOpMode c) {
        opMode = c;
    }

    /**
     * Forcefully halt the opmode
     */
    public static void terminateOpMode() {
        opMode.terminate();
    }

    /**
     * Gets the number of seconds that the opmode has been executing
     *
     * @return elapsed time since opmode was started, in seconds
     */
    public static double getOpModeRuntime() {
        return opMode.getOpModeRuntime();
    }

    /**
     * Reset the scheduler...
     */
    public static void resetScheduler() {
        Command.clear();
    }

    /**
     * Schedule a command to run (just use "schedule")
     *
     * @param command the command to schedule
     */
    public static void scheduleOnce(Command command) {
        schedule(command);
    }

    /**
     * Schedule a command to run during a particular OpModeState
     * You can just use scheduleForState instead...
     *
     * @param command the command to schedule
     * @param state   the state during which the command should be scheduled
     */
    public static void scheduleOnceForState(Command command, CommandOpMode.OpModeState state) {
        scheduleForState(command, state);
    }

    /**
     * Schedules a command to be run during Run and End states, all the time.
     * This is called "Schedule for Joystick" because joysticks don't really have a
     * condition you might consider 'useful for triggering', so the command
     * just runs repeatedly. This adds the requirement that the BooleanSupplier function
     * is also true for the command to be run.
     *
     * @param command  The command to run repeatedly
     * @param supplier The condition which must also be true to run the command
     */
    public static void scheduleJoystick(Command command, BooleanSupplier supplier) {
        scheduleForState(command, supplier, CommandOpMode.OpModeState.RUN, CommandOpMode.OpModeState.END);
    }

    /**
     * Schedules a command to be run during Run and End states, all the time.
     * This is called "Schedule for Joystick" because joysticks don't really have a
     * condition you might consider 'useful for triggering', so the command
     * just runs repeatedly
     *
     * @param command The command to run repeatedly.
     */
    public static void scheduleJoystick(Command command) {
        scheduleForState(command, CommandOpMode.OpModeState.RUN, CommandOpMode.OpModeState.END);
    }

    /**
     * Schedule a command to run
     *
     * @param command the command to schedule
     */
    public static void schedule(Command command) {
        schedule(command, () -> true);
    }

    /**
     * Schedule a command to be run recurringly during the 'Init' phase of an opmode.
     * This can be used for vision, as well as running any system to help the
     * drive team ensure that the robot is appropriately positioned on the field.
     * This will only be run if the BooleanSupplier 'supplier' is also true!
     *
     * @param command  The command to run
     * @param supplier The condition which also must be true to run the command
     */
    public static void scheduleInit(Command command, BooleanSupplier supplier) {
        scheduleForState(command, supplier, CommandOpMode.OpModeState.INIT);
    }

    /**
     * Schedule a command to be run recurringly during the 'Init' phase of an opmode.
     * This can be used for vision, as well as running any system to help the
     * drive team ensure that the robot is appropriately positioned on the field.
     *
     * @param command The command to run
     */
    public static void scheduleInit(Command command) {
        scheduleForState(command, () -> true, CommandOpMode.OpModeState.INIT);
    }

    /**
     * Schedule a command to be run when the OpMode is one of the provided list of states and the
     * 'supplier' boolean function is also true.
     *
     * @param command  The command to run
     * @param states   The list of states to schedule the command
     * @param supplier The function to determin in the command should be scheduled
     */
    public static void scheduleForState(
        Command command,
        BooleanSupplier supplier,
        CommandOpMode.OpModeState... states
    ) {
        schedule(
            command.cancelUpon(() -> !opMode.getOpModeState().isState(states)),
            () -> supplier.getAsBoolean() && opMode.getOpModeState().isState(states)
        );
    }

    /**
     * Schedule a command to be run when the OpMode is one of the provided list of states.
     *
     * @param command The command to run
     * @param states  The list of states to schedule the command
     */
    public static void scheduleForState(Command command, CommandOpMode.OpModeState... states) {
        schedule(command.cancelUpon(() -> !opMode.getOpModeState().isState(states)), () ->
            opMode.getOpModeState().isState(states)
        );
    }

    /**
     * Schedule the 'other' command (the second one) when the 'dependency' command has
     * finished (but *not* been cancelled!).
     *
     * @param dependency The command upon which 'other' depends
     * @param other      The command to schedule when 'dependency' has finished
     */
    public static void scheduleAfterOther(Command dependency, Command other) {
        schedule(other, dependency::justFinishedNoCancel);
    }

    /**
     * Schedule the 'other' command (the second one) when the 'dependency' command has
     * just started.
     *
     * @param dependency The command upon which 'other' depends
     * @param other      The command to schedule when 'dependency' has started
     */
    public static void scheduleWithOther(Command dependency, Command other) {
        schedule(other, dependency::justStarted);
    }

    /**
     * Schedule the 'other' command (the second one) when the 'dependency' command has
     * finished (but *not* been cancelled!) *and* 'additionalCondition' is true.
     *
     * @param dependency          The command upon which 'other' depends
     * @param other               The command to schedule when 'dependency' has finished
     * @param additionalCondition The additional condition necessary to be true to schedule the 'other' command
     */
    public static void scheduleAfterOther(Command dependency, Command other, BooleanSupplier additionalCondition) {
        schedule(other, () -> dependency.justFinishedNoCancel() && additionalCondition.getAsBoolean());
    }

    /**
     * Schedule the 'other' command (the second one) when the 'dependency' command has
     * just started *and* 'additionalCondition' is also true.
     *
     * @param dependency          The command upon which 'other' depends
     * @param other               The command to schedule when 'dependency' has started
     * @param additionalCondition The additional condition necessary to be true to schedule the 'other' command
     */
    public static void scheduleWithOther(Command dependency, Command other, BooleanSupplier additionalCondition) {
        schedule(other, () -> dependency.justStarted() && additionalCondition.getAsBoolean());
    }

    /**
     * Schedule the default command for a given subsystem. The default command will be run
     * if no other command is currently running on that subsystem.
     *
     * @param command   The command to be run when others aren't using that subsystem
     * @param subsystem The subsystem to run the command against when it's unused
     */
    public static void scheduleDefault(Command command, Subsystem subsystem) {
        if (command.getRequirements().contains(subsystem)) {
            defaultMap.put(subsystem, command);
            schedule(command, () -> getCurrent(subsystem) == command);
        } else {
            System.err.println("default commands must require their subsystem: " + command.getClass().toString());
        }
    }

    /**
     * Register a periodic function to be run once each schedule loop
     *
     * @param p The Periodic function to run
     */
    public static void register(Periodic p) {
        registered.add(p);
    }

    /**
     * Get the default command that is running on the subsystem provided
     *
     * @param s The subsystem in question
     * @return the default command for the subsystem, or null if there is none
     */
    @Nullable
    public static Command getDefault(Subsystem s) {
        return opMode.getOpModeState() == CommandOpMode.OpModeState.RUN ? defaultMap.get(s) : null;
    }

    /**
     * This gets the command currently using the subsystem provided
     *
     * @param s The subsystem in question.
     * @return The Command currently using the subsystem, the default
     * command for the subsystem, or null if there is no current
     * command usint the subsystem, nor a default command
     */
    @Nullable
    public static Command getCurrent(Subsystem s) {
        if (requirementMap.get(s) == null) return null;
        for (Command c : requirementMap.get(s)) {
            if (c.isRunning()) return c;
        }
        return getDefault(s);
    }

    /**
     * Register a command to be scheduled. The 'supplier' function is what triggers
     * the schedule to begin running the command.
     *
     * @param command  The command to schedule
     * @param supplier The function that returns true when the command should be run
     */
    public static void schedule(Command command, BooleanSupplier supplier) {
        commandMap.put(command, supplier);
        for (Subsystem s : command.getRequirements()) {
            // TODO: Fail if a required subsystem is null, and maybe log something
            requirementMap.putIfAbsent(s, new LinkedHashSet<>());
            requirementMap.get(s).add(command);
            register(s);
        }
    }

    /**
     * This is invoked from inside the CommandOpMode method, during the opCode.
     * It it the core logic of actually scheduling &amp; running the commands.
     */
    public static void run() {
        // For each newly scheduled command,
        // cancel any existing command that is using the new command's subsystem requirements
        commandMap.forEach((c1, b) -> {
            if (c1.justStarted()) {
                for (Subsystem s : c1.getRequirements()) {
                    for (Command c2 : requirementMap.get(s)) {
                        if (c1 != c2) {
                            c2.cancel();
                        }
                    }
                }
            }
        });
        commandMap.forEach((c1, b) -> {
            if (b.getAsBoolean() || c1.isRunning()) {
                c1.run();
            }
        });
        registered.forEach(Periodic::periodic);
    }
}
