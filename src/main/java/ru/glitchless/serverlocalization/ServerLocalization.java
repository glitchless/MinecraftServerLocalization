package ru.glitchless.serverlocalization;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ru.glitchless.serverlocalization.config.ServerLocalizationConfig;
import ru.glitchless.serverlocalization.lang.*;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = ServerLocalization.MODID,
        name = ServerLocalization.NAME,
        version = ServerLocalization.VERSION,
        acceptableRemoteVersions = "*")
public class ServerLocalization {
    public static final String MODID = "serverlocalization";
    public static final String NAME = "Server Localization";
    public static final String VERSION = "1.0";

    private static List<ILangChanger> langChangerList = new ArrayList<>();
    private static Logger logger;

    public static void addLangChanger(ILangChanger langChanger) {
        langChangerList.add(langChanger);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        addLangChanger(new VanillaChanger());
        addLangChanger(new OtherModChanger());
        addLangChanger(new IC2LangChanger());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        final String lang = ServerLocalizationConfig.lang.toLowerCase();
        for (ILangChanger langChanger : langChangerList) {
            try {
                logger.info("Applying lang for " + langChanger.getClass().getSimpleName());
                langChanger.changeLanguage(logger, lang);
            } catch (LangNotFoundException ex) {
                logger.error("Failed " + langChanger.getClass().getSimpleName(), ex);
            }
        }
    }
}
