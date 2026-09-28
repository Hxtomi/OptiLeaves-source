package net.norevy.optileaves.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.norevy.optileaves.LeafCulling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockFaceCullMixin {
    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void optileaves$cull(BlockState state, BlockGetter view, BlockPos pos, Direction face,
                                       BlockPos neighbor, CallbackInfoReturnable<Boolean> cir) {
        if (LeafCulling.shouldCull(state, view, pos, face)) cir.setReturnValue(false);
    }
}
