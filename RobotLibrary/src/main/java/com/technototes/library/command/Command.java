package com.technototes.library.command;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.technototes.library.subsystem.DeviceSubsystem;
import com.technototes.library.subsystem.Subsystem;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * The root Command class
 *
 * @author Alex Stedman
 */
@FunctionalInterface
public interface Command extends Runnable, Supplier<Command.CommandState> {
    // These are *static* fields of the Command interface, because interfaces aren't allowed
    // to have member fields (because they're interfaces...)

    /**
     * The Command to Current State of the Command lookup
     */
    Map<Command, CommandState> stateMap = new HashMap<>();
    /**
     * The Command to Total Time Spent Running lookup
     */
    Map<Command, ElapsedTime> timeMap = new HashMap<>();
    /**
     * The Command to Required Subsystems lookup
     */
    Map<Command, Set<Subsystem>> requirementMap = new HashMap<>();

    /**
     * Add requirement subsystems to command
     * <p>
     * Requirements are subsystems upon which this command depends. When executing a command,
     * any commands that are currently being executed which depend upon the subsystem(s) required
     * will be cancelled by the CommandScheduler.
     *
     * @param requirements The subsystems this command uses
     * @return this
     */
    default Command addRequirements(Subsystem... requirements) {
        getRequirements().addAll(Arrays.asList(requirements));
        return this;
    }

    /**
     * Init the command
     * <p>
     * Defaults to doing nothing
     */
    default void initialize() {}

    /**
     * Execute the command
     * <p>
     * This is (probably) where the majority of your command belongs
     */
    void execute();

    /**
     * Return if the command is finished
     * <p>
     * Defaults to returning *true* which means that the command will execute once and complete.
     *
     * @return Is the command finished
     */
    default boolean isFinished() {
        return true;
    }

    /**
     * End the command
     * <p>
     * Defaults to doing nothing
     *
     * @param cancel True if the command was cancelled, False if it ended naturally
     */
    default void end(boolean cancel) {}

    /**
     * Run a command or series of ParallelCommands after this one
     *
     * @param c the list of commands to run
     * @return the new SequentialCommandGroup of this Command, then the next set
     */
    default SequentialCommandGroup andThen(Command... c) {
        return new SequentialCommandGroup(this, c.length == 1 ? c[0] : new ParallelCommandGroup(c));
    }

    /**
     * Delay the command for some time
     *
     * @param sec The number of sections to delay
     * @return The new SequentialCommandGroup of this command and a WaitCommand
     */
    default SequentialCommandGroup sleep(double sec) {
        return andThen(new WaitCommand(sec));
    }

    /**
     * Delay the command for some time
     *
     * @param sup A function that returns the number of sections to delay
     * @return The new SequentialCommandGroup of this command and a WaitCommand
     */
    default SequentialCommandGroup sleep(DoubleSupplier sup) {
        return andThen(new WaitCommand(sup));
    }

    /**
     * After this command, wait until the condition function is true
     *
     * @param condition The function that returns true when ready to proceed
     * @return the new SequentialCommandGroup of this command and a Conditional Command
     * for waiting
     */
    default SequentialCommandGroup waitUntil(BooleanSupplier condition) {
        return andThen(new ConditionalCommand(condition));
    }

    /**
     * Run this command in parallel with additional commands
     *
     * @param c the command (or list of commands) to be run in parallel
     * @return a ParallelCommandGroup of this command, and all the c commands
     */
    default ParallelCommandGroup alongWith(Command... c) {
        Command[] c1 = new Command[c.length + 1];
        c1[0] = this;
        System.arraycopy(c, 0, c1, 1, c.length);
        return new ParallelCommandGroup(c1);
    }

    /**
     * Runs all the commands specified in parallel *until this command is completed*
     *
     * @param c The command or list of commands to run in parallel with this command
     * @return a new ParallelDeadlineGroup with this command, and all the other commands
     */
    default ParallelDeadlineGroup deadline(Command... c) {
        return new ParallelDeadlineGroup(this, c);
    }

    /**
     * Runs all the commands in parallel (including this command) until one of them finishes
     *
     * @param c the additional commands to run in parallel
     * @return a ParallelRaceGroup
     */
    default ParallelRaceGroup raceWith(Command... c) {
        Command[] c1 = new Command[c.length + 1];
        c1[0] = this;
        System.arraycopy(c, 0, c1, 1, c.length);
        return new ParallelRaceGroup(c1);
    }

    /**
     * Creates a conditional command out of this
     *
     * @param condition The condition to run the command under
     * @return the conditional command
     */
    default ConditionalCommand asConditional(BooleanSupplier condition) {
        return new ConditionalCommand(condition, this);
    }

    /**
     * Runs this command until it either finishes, or the timeout has elapsed
     *
     * @param seconds The maximum number of seconds to wait
     * @return A ParallelRaceGroup of this command and a WaitCommand
     */
    default ParallelRaceGroup withTimeout(double seconds) {
        return raceWith(new WaitCommand(seconds));
    }

    /**
     * Runs this command until it finishes, or until the condition supplied is true
     *
     * @param condition A boolean function that returns true if the command should be cancelled
     * @return A ParallelRaceGroup of this command and a ConditionalCommand of condition
     */
    default ParallelRaceGroup cancelUpon(BooleanSupplier condition) {
        return raceWith(new ConditionalCommand(condition));
    }

    /**
     * Runs this command only if the choiceCondition is met (i.e. returns true)
     *
     * @param choiceCondition a boolean function that determins if this command should run or not
     * @return a ChoiceCommand with this command as the "true" command.
     */
    default ChoiceCommand onlyIf(BooleanSupplier choiceCondition) {
        return new ChoiceCommand(choiceCondition, this);
    }

    /**
     * Run the commmand
     */
    @Override
    default void run() {
        switch (getState()) {
            case RESET:
                getRuntime().reset();
                setState(CommandState.STARTED);
                return;
            case STARTED:
                setState(CommandState.INITIALIZING);
                return;
            case INITIALIZING:
                initialize();
                setState(CommandState.EXECUTING);
            // no return for fallthrough
            case EXECUTING:
                execute();
                if (isFinished()) setState(CommandState.FINISHED);
                return;
            case INTERRUPTED:
                // state to allow
                setState(CommandState.CANCELLED);
                return;
            case CANCELLED:
            case FINISHED:
                end(isCancelled());
                setState(CommandState.RESET);
        }
    }

    /**
     * The command state enum
     */
    enum CommandState {
        /**
         * The command has just be scheduled
         */
        RESET,
        /**
         * The command has been triggered
         */
        STARTED,
        /**
         * The command is initializing after having been triggered
         */
        INITIALIZING,
        /**
         * The command is running normally
         */
        EXECUTING,
        /**
         * The command has completed successfully
         */
        FINISHED,
        /**
         * The command is pending cancellation (but has not yet processed the cancellation)
         */
        INTERRUPTED,
        /**
         * The command has been cancelled
         */
        CANCELLED,
    }

    /**
     * Return the amount of time since the command was first initialized
     *
     * @return The runtime as an {@link ElapsedTime}
     */
    default ElapsedTime getRuntime() {
        ElapsedTime t = timeMap.putIfAbsent(this, new ElapsedTime());
        return t != null ? t : timeMap.get(this);
    }

    /**
     * Return the command state: Probably don't use this
     *
     * @return The state as an {@link CommandState}
     */
    default CommandState getState() {
        return stateMap.getOrDefault(this, CommandState.RESET);
    }

    /**
     * Set the command state: DEFINITELY DO NOT USE THIS!
     *
     * @param s The state to set this command to
     * @return This command
     */
    default Command setState(CommandState s) {
        stateMap.put(this, s);
        return this;
    }

    /**
     * Return the subsystem requirements for this command
     *
     * @return The {@link DeviceSubsystem} requirements
     */
    default Set<Subsystem> getRequirements() {
        requirementMap.putIfAbsent(this, new LinkedHashSet<>());
        return requirementMap.get(this);
    }

    /**
     * Is this command finished?
     *
     * @return True if the command has finished, or has been cancelled
     */
    default boolean justFinished() {
        return (getState() == CommandState.FINISHED || getState() == CommandState.CANCELLED);
    }

    /**
     * Is this command completed?
     *
     * @return True if the command has finished and NOT been cancelled.
     */
    default boolean justFinishedNoCancel() {
        return getState() == CommandState.FINISHED;
    }

    /**
     * Has the command just be started?
     *
     * @return True if the command was started, but hasn't yet been run
     */
    default boolean justStarted() {
        return getState() == CommandState.STARTED;
    }

    /**
     * Is the command in some state of running/started/finished/cancelled
     *
     * @return True if the command is not in the RESET state
     */
    default boolean isRunning() {
        return getState() != CommandState.RESET;
    }

    /**
     * Exactly what it says
     *
     * @return True if the command has been cancelled
     */
    default boolean isCancelled() {
        return getState() == CommandState.CANCELLED;
    }

    /**
     * If the command is running, interrupt it such that it can be cancelled
     */
    default void cancel() {
        if (isRunning() && !justFinished()) setState(CommandState.INTERRUPTED);
    }

    /**
     * Clear out the state, time, and requirement maps. Be careful with this one!
     */
    static void clear() {
        stateMap.clear();
        timeMap.clear();
        requirementMap.clear();
    }

    /**
     * This is a helper to create a new command from an existing command, but with additional
     * subsystem requirements
     *
     * @param c The command to add the extra subsystem
     * @param s The subsystem (or list of subsystems) to add to the commands requiremets
     * @return The new command
     */
    static Command create(Command c, Subsystem... s) {
        return c.addRequirements(s);
    }

    /**
     * Gets the current state of the command (Supplier&lt;CommandState&gt;)
     *
     * @return The current CommandState of this command
     */
    @Override
    default CommandState get() {
        return getState();
    }
}
