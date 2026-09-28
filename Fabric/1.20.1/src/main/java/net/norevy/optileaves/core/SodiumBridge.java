package net.norevy.optileaves.core;

import net.norevy.optileaves.config.Settings;

import com.google.common.collect.ImmutableList;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.*;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class SodiumBridge {
    private static final OptionStorage<Settings> STORAGE = new OptionStorage<>() {
        public Settings getData() { return Settings.INSTANCE; }
        public void save() { Settings.INSTANCE.save(); }
    };

    private SodiumBridge() {}

    public static boolean fancyLeaves() {
        return SodiumClientMod.options().quality.leavesQuality.isFancy(Minecraft.getInstance().options.graphicsMode().get());
    }

    public static ImmutableList<OptionGroup> append(ImmutableList<OptionGroup> groups) {
        return ImmutableList.<OptionGroup>builder().addAll(groups).add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, STORAGE)
                        .setName(Component.literal("OptiLeaves"))
                        .setTooltip(Component.literal("Cull internal faces between leaf blocks."))
                        .setControl(TickBoxControl::new)
                        .setBinding((config, value) -> config.enabled = value, config -> config.enabled)
                        .setImpact(OptionImpact.MEDIUM)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD).build())
                .add(OptionImpl.createBuilder(int.class, STORAGE)
                        .setName(Component.literal("Leaf culling depth"))
                        .setTooltip(Component.literal("Consecutive leaf blocks required to hide a face. 1 culls the most; 4 preserves more detail. Fast leaves use 1."))
                        .setControl(option -> new SliderControl(option, 1, 4, 1, ControlValueFormatter.number()))
                        .setBinding((config, value) -> config.depth = Settings.clampDepth(value), config -> config.depth)
                        .setImpact(OptionImpact.MEDIUM)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD).build())
                .build()).build();
    }
}
