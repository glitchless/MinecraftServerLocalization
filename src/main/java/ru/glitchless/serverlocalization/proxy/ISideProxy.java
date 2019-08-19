package ru.glitchless.serverlocalization.proxy;

import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

public interface ISideProxy {
    String getMinecraftVersion();
    File getFile(String path);

    public static ISideProxy getInstance() {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            return new ClientSideProxy();
        } else {
            return new ServerSideProxy();
        }
    }
}
