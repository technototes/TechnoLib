package com.technototes.library.general;

public class SkillException extends Exception {
    public SkillException(){
        this("This");
    }
    public SkillException(String s){
        super(s+" seems like a skill issue imo.");
    }
}
