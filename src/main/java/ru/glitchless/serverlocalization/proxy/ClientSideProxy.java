package ru.glitchless.serverlocalization.proxy;

import net.minecraft.client.Minecraft;

import java.io.File;

public class ClientSideProxy implements ISideProxy {
    @Override
    public String getMinecraftVersion() {
        return Minecraft.getMinecraft().getVersion();
    }

    @Override
    public File getFile(String path) {
        return new File(Minecraft.getMinecraft().mcDataDir, path);
    }
}
