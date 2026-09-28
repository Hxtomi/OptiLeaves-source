package net.norevy.optileaves.util;

import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;

public final class Compat {
    public static final boolean EMBEDDIUM = ModList.get().isLoaded("embeddium");

    private static final boolean SODIUM = ModList.get().isLoaded("sodium");

    private Compat() {}

    public static boolean isFancyLeaves() {
        if (EMBEDDIUM) return org.embeddedt.embeddium.impl.Embeddium.options().quality.leavesQuality.isFancy(Minecraft.getInstance().options.graphicsMode().get());
        if (SODIUM) return SodiumLeaves.isFancy(Minecraft.getInstance().options.graphicsMode().get());
        return Minecraft.useFancyGraphics();
    }
}
