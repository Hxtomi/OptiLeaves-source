package net.norevy.optileaves;

import net.norevy.optileaves.config.Settings;
import net.norevy.optileaves.core.RenderBridge;

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
        return shouldCullFromNeighbor(state, view, pos.getX() + face.getStepX(), pos.getY() + face.getStepY(), pos.getZ() + face.getStepZ(), face);
    }

    public static boolean shouldCullFromNeighbor(BlockState state, BlockGetter view, int x, int y, int z, Direction face) {
        if (!Settings.INSTANCE.enabled || !(state.getBlock() instanceof LeavesBlock)) return false;
        int depth = RenderBridge.fancyLeavesActive() ? Settings.clampDepth(Settings.INSTANCE.depth) : 1;
        BlockPos.MutableBlockPos cursor = POSITION.get();
        // Set absolute coordinates each time: a nested getter cannot leave the cursor displaced.
        for (int distance = 1; distance <= depth; distance++) {
            cursor.set(x + face.getStepX() * (distance - 1),
                       y + face.getStepY() * (distance - 1),
                       z + face.getStepZ() * (distance - 1));
            if (!(view.getBlockState(cursor).getBlock() instanceof LeavesBlock)) return false;
        }
        return true;
    }
}
