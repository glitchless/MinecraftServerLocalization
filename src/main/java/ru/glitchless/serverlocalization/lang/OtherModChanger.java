package ru.glitchless.serverlocalization.lang;

import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class OtherModChanger implements ILangChanger {
    @Override
    public void changeLanguage(Logger logger, String lang) throws LangNotFoundException {
        for (ModContainer container : Loader.instance().getActiveModList()) {
            InputStream is = OtherModChanger.class.getResourceAsStream("/assets/" + container.getModId() + "/lang/" + lang + ".lang");
            if (is == null) {
                return;
            }
            LanguageMap.inject(is);
            logger.info("Change lang to " + lang + " for " + container.getModId() + " done!");
        }
    }
}
