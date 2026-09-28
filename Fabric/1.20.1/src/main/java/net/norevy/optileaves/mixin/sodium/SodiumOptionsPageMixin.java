package net.norevy.optileaves.mixin.sodium;

import com.google.common.collect.ImmutableList;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import net.norevy.optileaves.core.SodiumBridge;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Pseudo
@Mixin(targets="me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages",remap=false)
public abstract class SodiumOptionsPageMixin {
    @ModifyArg(method="performance", at=@At(value="INVOKE",target="Lme/jellysquid/mods/sodium/client/gui/options/OptionPage;<init>"), index=1, remap=false)
    private static ImmutableList<OptionGroup> optileaves$options(ImmutableList<OptionGroup> groups) {
        return SodiumBridge.append(groups);
    }
}
