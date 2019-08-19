package ru.glitchless.serverlocalization.lang;

import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class IC2LangChanger implements ILangChanger {
    @Override
    public void changeLanguage(Logger logger, String lang) throws LangNotFoundException {
        if (Loader.isModLoaded("ic2")) {
            InputStream is = null;
            try {
                is = IC2LangChanger.class.getResourceAsStream("/assets/ic2/lang_ic2/" + lang + ".properties");
                if (is == null) {
                    throw new LangNotFoundException(lang);
                }
                loadIC2Localization(is, getLanguageMapMap());
                logger.info("Change lang to " + lang + " for ic2 done!");
            } catch (IOException ex) {
                logger.error(ex);
                throw new LangNotFoundException(lang);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        logger.error(ex);
                    }
                }
            }
        }
    }

    private static void loadIC2Localization(InputStream inputStream, Map<String, String> out) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        for (Map.Entry<Object, Object> entries : properties.entrySet()) {
            Object key = entries.getKey();
            Object value = entries.getValue();

            if (((key instanceof String)) && ((value instanceof String))) {
                String newKey = (String) key;

                if ((!newKey.startsWith("achievement.")) &&
                        (!newKey.startsWith("itemGroup.")) &&
                        (!newKey.startsWith("death."))) {

                    newKey = "ic2." + newKey;
                }
                out.put(newKey, (String) value);
            }
        }
    }

    protected static Map<String, String> getLanguageMapMap() {
        for (Method method : LanguageMap.class.getDeclaredMethods()) {
            if (method.getReturnType() == LanguageMap.class) {
                method.setAccessible(true);
                Field mapField = getField(LanguageMap.class, Map.class);
                try {
                    return (Map) mapField.get(method.invoke(null, new Object[0]));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static Field getField(Class<?> clazz, Class<?> type) {
        Field ret = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (type.isAssignableFrom(field.getType())) {
                if (ret != null) {
                    return null;
                }
                field.setAccessible(true);
                ret = field;
            }
        }
        return ret;
    }
}
