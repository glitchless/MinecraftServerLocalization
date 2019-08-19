package ru.glitchless.serverlocalization.proxy;

import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

public class ServerSideProxy implements ISideProxy {
    @Override
    public String getMinecraftVersion() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getMinecraftVersion();
    }

    @Override
    public File getFile(String path) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getFile(path);
    }
}
