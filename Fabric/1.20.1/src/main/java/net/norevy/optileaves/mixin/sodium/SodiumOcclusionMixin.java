package net.norevy.optileaves.mixin.sodium;

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
@Mixin(targets = {"me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache",
        "me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache"}, remap = false)
public abstract class SodiumOcclusionMixin {
    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true, remap = false)
    private void optileaves$cull(BlockState state, BlockGetter view, BlockPos pos, Direction face,
                                CallbackInfoReturnable<Boolean> cir) {
        if (LeafCulling.shouldCull(state, view, pos, face)) cir.setReturnValue(false);
    }
}
