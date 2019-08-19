package ru.glitchless.serverlocalization.lang;

import org.apache.logging.log4j.Logger;

public interface ILangChanger {
    void changeLanguage(Logger logger, String lang) throws LangNotFoundException;
}
