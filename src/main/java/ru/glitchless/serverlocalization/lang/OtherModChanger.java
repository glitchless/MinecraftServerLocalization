package ru.glitchless.serverlocalization.lang;

import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class OtherModChanger implements ILangChanger {
	@Override
	public void changeLanguage(Logger logger, String lang) throws LangNotFoundException {
		int len = lang.length();
		for (ModContainer container : Loader.instance().getActiveModList()) {
			char[][] tmp = new char[2][];
			tmp[0] = lang.toLowerCase().toCharArray();
			tmp[1] = lang.toUpperCase().toCharArray();
			for (int i = 0; i < 1 << len; i++) {
				String value = "";
				for (int j = 0; j < len; j++) {
					value += tmp[(i >> j) % 2][j];
				}
				InputStream is = OtherModChanger.class.getResourceAsStream("/assets/" + container.getModId() + "/lang/" + value + ".lang");
				if (is == null) {
					continue;
				}
				LanguageMap.inject(is);
				logger.info("Change lang to " + value + " for " + container.getModId() + " done!");
				break;
			}
		}
	}
}
