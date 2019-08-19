package ru.glitchless.serverlocalization.config;

import net.minecraftforge.common.config.Config;
import ru.glitchless.serverlocalization.ServerLocalization;

@Config(modid = ServerLocalization.MODID)
public class ServerLocalizationConfig {
    @Config.Comment("server lang")
    public static String lang = "en_ud";
}
