package net.norevy.optileaves.core;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public final class RenderBridge {
    public static final boolean HAS_SODIUM = FabricLoader.getInstance().isModLoaded("sodium");
    private RenderBridge() {}
    public static boolean fancyLeavesActive() {
        return HAS_SODIUM ? SodiumLeaves.isFancy(Minecraft.getInstance().options.graphicsMode().get())
                : Minecraft.useFancyGraphics();
    }
}
