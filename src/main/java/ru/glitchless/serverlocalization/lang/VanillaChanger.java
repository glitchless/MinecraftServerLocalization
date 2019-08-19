package ru.glitchless.serverlocalization.lang;

import net.minecraft.util.text.translation.LanguageMap;
import org.apache.logging.log4j.Logger;
import ru.glitchless.serverlocalization.downloader.AssetsHelper;

import java.io.*;

public class VanillaChanger implements ILangChanger {
    @Override
    public void changeLanguage(Logger logger, String lang) throws LangNotFoundException {
        final File assetFile = AssetsHelper.getLangFile(logger, lang);
        if (assetFile == null) {
            throw new LangNotFoundException(lang);
        }
        final InputStream is;
        try {
            is = new FileInputStream(assetFile);
        } catch (FileNotFoundException e) {
            throw new LangNotFoundException(lang);
        }

        try {
            LanguageMap.inject(is);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
        logger.info("Change lang to " + lang + " for vanilla done!");
    }
}
