package net.norevy.optileaves.mixin.sodium;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.norevy.optileaves.LeafCulling;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Pseudo
@Mixin(targets="net.caffeinemc.mods.sodium.client.render.model.AbstractBlockRenderContext", remap=false)
public abstract class SodiumOcclusionMixin {
    @Shadow protected BlockAndTintGetter level;
    @Shadow protected BlockState state;
    @Shadow protected BlockPos pos;
    @Inject(method="shouldDrawSide", at=@At("HEAD"), cancellable=true)
    private void optileaves$cull(Direction face, CallbackInfoReturnable<Boolean> cir) {
        if (face != null && LeafCulling.shouldCull(state, level, pos, face)) cir.setReturnValue(false);
    }
}
