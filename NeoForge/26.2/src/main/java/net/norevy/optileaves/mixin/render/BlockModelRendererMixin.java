package net.norevy.optileaves.mixin.render;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.norevy.optileaves.LeafCulling;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(ModelBlockRenderer.class)
public abstract class BlockModelRendererMixin {
    @Shadow @Final private boolean cull;
    @Inject(method="shouldRenderFace(Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/core/BlockPos;)Z", at=@At("HEAD"), cancellable=true)
    private void optileaves$cull(BlockAndTintGetter view, BlockPos pos, BlockState state, Direction face, BlockPos neighbor, CallbackInfoReturnable<Boolean> cir) {
        if (cull && LeafCulling.shouldCull(state, view, pos, face)) cir.setReturnValue(false);
    }
}
