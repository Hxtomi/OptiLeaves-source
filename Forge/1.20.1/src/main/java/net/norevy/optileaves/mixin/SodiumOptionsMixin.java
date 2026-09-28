package net.norevy.optileaves.mixin;

import com.google.common.collect.ImmutableList;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import net.norevy.optileaves.SodiumOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.*;

/** Modify the list passed into the page, preserving Embeddium's page identity and other mods' options. */
@Pseudo
@Mixin(targets = "me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages", remap = false)
public abstract class SodiumOptionsMixin {
    @ModifyArg(method = "performance", at = @At(value = "INVOKE",
            target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionPage;<init>(Lorg/embeddedt/embeddium/client/gui/options/OptionIdentifier;Lnet/minecraft/network/chat/Component;Lcom/google/common/collect/ImmutableList;)V"),
            index = 2, require = 0, remap = false)
    private static ImmutableList<OptionGroup> optileaves$modern(ImmutableList<OptionGroup> groups) {
        return SodiumOptions.append(groups);
    }

    @ModifyArg(method = "performance", at = @At(value = "INVOKE",
            target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionPage;<init>(Lnet/minecraft/network/chat/Component;Lcom/google/common/collect/ImmutableList;)V"),
            index = 1, require = 0, remap = false)
    private static ImmutableList<OptionGroup> optileaves$legacy(ImmutableList<OptionGroup> groups) {
        return SodiumOptions.append(groups);
    }
}
