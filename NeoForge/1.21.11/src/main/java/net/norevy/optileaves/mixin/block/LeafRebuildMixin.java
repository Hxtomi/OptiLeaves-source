package net.norevy.optileaves.mixin.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.norevy.optileaves.config.Settings;
import net.norevy.optileaves.core.RenderBridge;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class LeafRebuildMixin {
    @Unique private static final Direction[] OPTILEAVES_DIRECTIONS = Direction.values();

    @Inject(method = "sendBlockUpdated", at = @At("HEAD"))
    private void optileaves$blockChanged(BlockPos pos, BlockState oldState,
                                        BlockState newState, int flags, CallbackInfo ci) {
        optileaves$markDistantFaces(pos, oldState, newState);
    }

    @Inject(method = "setBlocksDirty(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V", at = @At("HEAD"))
    private void optileaves$blockDirty(BlockPos pos, BlockState oldState, BlockState newState, CallbackInfo ci) {
        optileaves$markDistantFaces(pos, oldState, newState);
    }

    @Unique
    private void optileaves$markDistantFaces(BlockPos pos, BlockState oldState, BlockState newState) {
        if (!Settings.INSTANCE.enabled || Settings.INSTANCE.depth <= 1) return;
        if ((oldState.getBlock() instanceof LeavesBlock) == (newState.getBlock() instanceof LeavesBlock)) return;
        if (!RenderBridge.fancyLeavesActive()) return;
        int depth = Settings.clampDepth(Settings.INSTANCE.depth);
        for (Direction face : OPTILEAVES_DIRECTIONS) {
            int x = (pos.getX() + face.getStepX() * depth) >> 4;
            int y = (pos.getY() + face.getStepY() * depth) >> 4;
            int z = (pos.getZ() + face.getStepZ() * depth) >> 4;
            // Vanilla already rebuilds the one-block neighborhood. Add only sections beyond it.
            if (x != (pos.getX() + face.getStepX()) >> 4 ||
                y != (pos.getY() + face.getStepY()) >> 4 ||
                z != (pos.getZ() + face.getStepZ()) >> 4) {
                Minecraft.getInstance().levelRenderer.setSectionDirty(x, y, z);
            }
        }
    }
}
