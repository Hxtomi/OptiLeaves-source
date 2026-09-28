package net.norevy.optileaves;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

/** Stateless decisions: no world cache to become stale after edits or dimension changes. */
public final class LeafCulling {
    private static final ThreadLocal<BlockPos.MutableBlockPos> POSITION =
            ThreadLocal.withInitial(BlockPos.MutableBlockPos::new);

    private LeafCulling() {}

    public static boolean shouldCull(BlockState state, BlockGetter view, BlockPos pos, Direction face) {
        if (!Config.INSTANCE.enabled || !(state.getBlock() instanceof LeavesBlock)) return false;
        int depth = RendererCompat.fancyLeaves() ? Config.clampDepth(Config.INSTANCE.depth) : 1;
        BlockPos.MutableBlockPos cursor = POSITION.get();
        // Set absolute coordinates each time: a nested getter cannot leave the cursor displaced.
        for (int distance = 1; distance <= depth; distance++) {
            cursor.set(pos.getX() + face.getStepX() * distance,
                       pos.getY() + face.getStepY() * distance,
                       pos.getZ() + face.getStepZ() * distance);
            if (!(view.getBlockState(cursor).getBlock() instanceof LeavesBlock)) return false;
        }
        return true;
    }
}
