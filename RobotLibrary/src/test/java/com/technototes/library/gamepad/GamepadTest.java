package com.technototes.library.gamepad;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;

public class GamepadTest {
    private Gamepad gamepad;
    private CommandGamepad commandGamepad;

    @BeforeEach
    public void setup() {
        gamepad = new Gamepad();
        commandGamepad = new CommandGamepad(gamepad);
    }

    @Test
    public void buttonTest() {
        CommandButton commandButton = commandGamepad.a;
        gamepad.a = false;
        commandButton.periodic();
        gamepad.a = true;
        commandButton.periodic();
        Assertions.assertTrue(commandButton.isPressed());
        Assertions.assertTrue(commandButton.isJustPressed());
        Assertions.assertTrue(commandButton.isToggled());
        Assertions.assertTrue(commandButton.isJustToggled());
        commandButton.periodic();
        Assertions.assertTrue(commandButton.isPressed());
        Assertions.assertFalse(commandButton.isJustPressed());
        Assertions.assertTrue(commandButton.isToggled());
        Assertions.assertFalse(commandButton.isJustToggled());
        gamepad.a = false;
        commandButton.periodic();
        gamepad.a = true;
        commandButton.periodic();
        Assertions.assertTrue(commandButton.isInverseToggled());
    }
}
