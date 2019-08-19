package ru.glitchless.serverlocalization.lang;

public class LangNotFoundException extends Exception {
    public LangNotFoundException(String lang) {
        super("Not found " + lang);
    }
}
