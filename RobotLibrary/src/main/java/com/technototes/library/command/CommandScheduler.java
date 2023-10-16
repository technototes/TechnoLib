package com.technototes.library.command;

import androidx.annotation.Nullable;
import com.technototes.library.general.Periodic;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.subsystem.Subsystem;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

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
     * Schedule a method on a subsystem to run as a command:
     * {@code
     * CommandScheduler.schedule(robot.intakeSubsystem, IntakeSubsystem::activate)
     * }
     *
     * @param req the subsystem required
     * @param methodRef the function to invoke on the subsystem
     * @param <S> The type of the subsystem required by the method
     */
    public static <S extends Subsystem> void schedule(S req, Consumer<S> methodRef) {
        schedule(req, methodRef, () -> true);
    }

    /**
     * Schedule a method on a subsystem to run as a command, that takes an extra parameter:
     * {@code
     * // This command effectively calls robot.liftSubsys.GoToPosition(LiftPos.MIDDLE)
     * CommandScheduler.schedule(robot.liftSubsys, LiftSubsystem::GoToPosition, LiftPos.MIDDLE)
     * }
     *
     * @param req the subsystem required
     * @param methodRef the function to invoke on the subsystem
     * @param param the parameter to pass to the function being called
     * @param <S> The type of the subsystem required by the method
     * @param <T> The type of the parameter to pass to the method
     */
    public static <S extends Subsystem, T> void schedule(S req, BiConsumer<S, T> methodRef, T param) {
        schedule(req, methodRef, param, () -> true);
    }

    /**
     * Schedule a method to run as a command that does not require controlling a subsystem!
     * {@code
     * CommandScheduler.schedule(robot.signalSubsystem::blinkLights)
     * }
     *
     * @param methodRef the function to invoke on the subsystem
     */
    public static void schedule(Runnable methodRef) {
        schedule(methodRef, () -> true);
    }

    /**
     * Schedule a method on a subsystem to run as a command, that takes an extra parameter:
     * {@code
     * // This command effectively calls robot.liftSubsys.GoToPosition(LiftPos.MIDDLE)
     * CommandScheduler.schedule(robot.liftSubsys, LiftSubsystem::GoToPosition, LiftPos.MIDDLE)
     * }
     *
     * @param methodRef the function to invoke on the subsystem
     * @param param the parameter to pass to the function being called
     * @param <S> The type of the subsystem required by the method
     * @param <T> The type of the parameter to pass to the method
     */
    public static <S extends Subsystem, T> void schedule(Consumer<T> methodRef, T param) {
        schedule(methodRef, param, () -> true);
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
     * Schedule a method on a subsystem to be run recurringly during the 'Init' phase of an opmode.
     * This can be used for vision, as well as running any system to help the
     * drive team ensure that the robot is appropriately positioned on the field.
     * This will only be run if the BooleanSupplier 'supplier' is also true!
     * {@code
     * // This command effectively calls robot.visionSystem.seeTheThing()
     * CommandScheduler.scheduleInit(robot.visionSystem, TokenIdentifyingSubsystem::seeTheThing, () -> TokenIdentifyingSubsystem.CAMERA_CONNECTED)
     * }
     *
     * @param req the subsystem required
     * @param methodRef the function to invoke on the subsystem
     * @param supplier the boolean function to run to determine if the function should be run
     * @param <S> The type of the subsystem required by the method
     */
    public static <S extends Subsystem> void scheduleInit(S req, Consumer<S> methodRef, BooleanSupplier supplier) {
        scheduleInit(new SimpleRequiredCommand<>(req, methodRef), supplier);
    }

    /**
     * Schedule a method on a subsystem to be run recurringly during the 'Init' phase of an opmode,
     * that also takes a parameter.
     * This can be used for vision, as well as running any system to help the
     * drive team ensure that the robot is appropriately positioned on the field.
     * This will only be run if the BooleanSupplier 'supplier' is also true!
     * {@code
     * // This command effectively calls robot.visionSystem.seeTheThing(Alliance.RED),
     * // but only if TokenIdentifyingSubsystem.CAMERA_CONNECTED is also true
     * CommandScheduler.scheduleInit(robot.visionSystem, TokenIdentifyingSubsystem::seeTheThing, Alliance.RED, () -> TokenIdentifyingSubsystem.CAMERA_CONNECTED)
     * }
     * @param req the subsystem required
     * @param methodRef the function to invoke on the subsystem
     * @param param the argument passed to the methodRef function
     * @param supplier the boolean function to run to determine if the function should be run
     * @param <S> The type of the subsystem required by the method
     * @param <T> The type of the parameter to pass to the method
     */
    public static <S extends Subsystem, T> void scheduleInit(
        S req,
        BiConsumer<S, T> methodRef,
        T param,
        BooleanSupplier supplier
    ) {
        scheduleInit(new ParameterRequiredCommand<S, T>(req, methodRef, param), supplier);
    }

    /**
     * Schedule a method on a subsystem to be run recurringly during the 'Init' phase of an opmode.
     * This can be used for vision, as well as running any system to help the
     * drive team ensure that the robot is appropriately positioned on the field.
     * This will only be run if the BooleanSupplier 'supplier' is also true!
     * {@code
     * // This command effectively calls robot.visionSystem.seeTheThing()
     * CommandScheduler.scheduleInit(robot.visionSystem, TokenIdentifyingSubsystem::seeTheThing, () -> TokenIdentifyingSubsystem.CAMERA_CONNECTED)
     * }
     *
     * @param methodRef the function to invoke on the subsystem
     * @param supplier the boolean function to run to determine if the function should be run
     */
    public static void scheduleInit(Runnable methodRef, BooleanSupplier supplier) {
        scheduleInit(new SimpleCommand(methodRef), supplier);
    }

    /**
     * Schedule a method on a subsystem to be run recurringly during the 'Init' phase of an opmode,
     * that also takes a parameter.
     * This can be used for vision, as well as running any system to help the
     * drive team ensure that the robot is appropriately positioned on the field.
     * This will only be run if the BooleanSupplier 'supplier' is also true!
     * {@code
     * // This command effectively calls robot.visionSystem.seeTheThing(Alliance.RED),
     * // but only if TokenIdentifyingSubsystem.CAMERA_CONNECTED is also true
     * CommandScheduler.scheduleInit(robot.visionSystem, TokenIdentifyingSubsystem::seeTheThing, Alliance.RED, () -> TokenIdentifyingSubsystem.CAMERA_CONNECTED)
     * }
     * @param methodRef the function to invoke on the subsystem
     * @param param the argument passed to the methodRef function
     * @param supplier the boolean function to run to determine if the function should be run
     * @param <T> The type of the parameter to pass to the method
     */
    public static <T> void scheduleInit(Consumer<T> methodRef, T param, BooleanSupplier supplier) {
        scheduleInit(new ParameterCommand<>(methodRef, param), supplier);
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

    public static <S extends Subsystem> void scheduleInit(S req, Consumer<S> methodRef) {
        scheduleInit(new SimpleRequiredCommand(req, methodRef));
    }

    public static <S extends Subsystem, T> void scheduleInit(S req, BiConsumer<S, T> methodRef, T param) {
        scheduleInit(new ParameterRequiredCommand<S, T>(req, methodRef, param));
    }

    public static void scheduleInit(Runnable methodRef) {
        scheduleInit(new SimpleCommand(methodRef));
    }

    public static <T> void scheduleInit(Consumer<T> methodRef, T param) {
        scheduleInit(new ParameterCommand<>(methodRef, param));
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

    public static <S extends Subsystem> void scheduleForState(
        S req,
        Consumer<S> methodRef,
        BooleanSupplier supplier,
        CommandOpMode.OpModeState... states
    ) {
        scheduleForState(new SimpleRequiredCommand(req, methodRef), supplier, states);
    }

    public static <S extends Subsystem, T> void scheduleForState(
        S req,
        BiConsumer<S, T> methodRef,
        T param,
        BooleanSupplier supplier,
        CommandOpMode.OpModeState... states
    ) {
        scheduleForState(new ParameterRequiredCommand<S, T>(req, methodRef, param), supplier, states);
    }

    public static void scheduleForState(
        Runnable methodRef,
        BooleanSupplier supplier,
        CommandOpMode.OpModeState... states
    ) {
        scheduleForState(new SimpleCommand(methodRef), supplier, states);
    }

    public static <T> void scheduleForState(
        Consumer<T> methodRef,
        T param,
        BooleanSupplier supplier,
        CommandOpMode.OpModeState... states
    ) {
        scheduleForState(new ParameterCommand<>(methodRef, param), supplier, states);
    }

    /**
     * Schedule a command to be run when the OpMode is one of the provided list of states.
     *
     * @param command The command to run
     * @param states  The list of states to schedule the command
     */
    public static void scheduleForState(Command command, CommandOpMode.OpModeState... states) {
        schedule(
            command.cancelUpon(() -> !opMode.getOpModeState().isState(states)),
            () -> opMode.getOpModeState().isState(states)
        );
    }

    public static <S extends Subsystem> void scheduleForState(
        S req,
        Consumer<S> methodRef,
        CommandOpMode.OpModeState... states
    ) {
        scheduleForState(new SimpleRequiredCommand(req, methodRef), states);
    }

    public static <S extends Subsystem, T> void scheduleForState(
        S req,
        BiConsumer<S, T> methodRef,
        T param,
        CommandOpMode.OpModeState... states
    ) {
        scheduleForState(new ParameterRequiredCommand<S, T>(req, methodRef, param), states);
    }

    public static void scheduleForState(Runnable methodRef, CommandOpMode.OpModeState... states) {
        scheduleForState(new SimpleCommand(methodRef), states);
    }

    public static <T> void scheduleForState(Consumer<T> methodRef, T param, CommandOpMode.OpModeState... states) {
        scheduleForState(new ParameterCommand<>(methodRef, param), states);
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

    public static <S extends Subsystem> void scheduleDefault(S req, Consumer<S> methodRef) {
        scheduleDefault(new SimpleRequiredCommand(req, methodRef), req);
    }

    public static <S extends Subsystem, T> void scheduleDefault(S req, BiConsumer<S, T> methodRef, T param) {
        scheduleDefault(new ParameterRequiredCommand<S, T>(req, methodRef, param), req);
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
            requirementMap.putIfAbsent(s, new LinkedHashSet<>());
            requirementMap.get(s).add(command);
            register(s);
        }
    }

    public static <S extends Subsystem> void schedule(S req, Consumer<S> methodRef, BooleanSupplier sup) {
        schedule(new SimpleRequiredCommand(req, methodRef), sup);
    }

    public static <S extends Subsystem, T> void schedule(
        S req,
        BiConsumer<S, T> methodRef,
        T param,
        BooleanSupplier sup
    ) {
        schedule(new ParameterRequiredCommand<S, T>(req, methodRef, param), sup);
    }

    public static void schedule(Runnable methodRef, BooleanSupplier sup) {
        schedule(new SimpleCommand(methodRef), sup);
    }

    public static <T> void schedule(Consumer<T> methodRef, T param, BooleanSupplier sup) {
        schedule(new ParameterCommand<>(methodRef, param), sup);
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
