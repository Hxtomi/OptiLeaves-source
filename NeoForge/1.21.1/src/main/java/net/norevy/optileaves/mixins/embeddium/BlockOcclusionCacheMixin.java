package net.norevy.optileaves.mixins.embeddium;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.norevy.optileaves.LeafCulling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = {"org.embeddedt.embeddium.impl.render.chunk.compile.pipeline.BlockOcclusionCache",
        "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache"}, remap = false)
public abstract class BlockOcclusionCacheMixin {
    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true, remap = false)
    private void optileaves$cull(BlockState state, BlockGetter view, BlockPos pos, Direction face,
                                CallbackInfoReturnable<Boolean> cir) {
        if (LeafCulling.shouldCull(state, view, pos, face)) cir.setReturnValue(false);
    }
}
