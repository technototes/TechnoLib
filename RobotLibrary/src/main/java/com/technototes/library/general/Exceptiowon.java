package com.technototes.library.general;

public class Exceptiowon extends Exception {
    public Exceptiowon(String message){
        super(message
                .replaceAll("u", "uwu")
                .replaceAll("o", "owo")
                .replaceAll("i", "i-i")
                .replaceAll("r", "w")
        );
    }
}
