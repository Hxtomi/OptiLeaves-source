package net.norevy.optileaves.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import net.minecraft.client.GraphicsStatus;

/** Resolve the renamed Sodium options type once; the render path uses a typed handle without reflection or boxing. */
final class SodiumLeaves {
    private static final MethodHandle IS_FANCY = resolve();

    private static MethodHandle resolve() {
        try {
            var lookup = MethodHandles.publicLookup();
            Class<?> client = Class.forName("net.caffeinemc.mods.sodium.client.SodiumClientMod");
            Method options = client.getMethod("options");
            var quality = options.getReturnType().getField("quality");
            var leaves = quality.getType().getField("leavesQuality");
            var fancy = leaves.getType().getMethod("isFancy", GraphicsStatus.class);
            MethodHandle currentQuality = MethodHandles.filterReturnValue(lookup.unreflect(options), lookup.unreflectGetter(quality));
            MethodHandle currentLeaves = MethodHandles.filterReturnValue(currentQuality, lookup.unreflectGetter(leaves));
            return MethodHandles.collectArguments(lookup.unreflect(fancy), 0, currentLeaves);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unsupported Sodium leaf-quality API", e);
        }
    }

    static boolean isFancy(GraphicsStatus graphics) {
        try {
            return (boolean) IS_FANCY.invokeExact(graphics);
        } catch (Throwable e) {
            throw new IllegalStateException("Cannot read Sodium leaf quality", e);
        }
    }
}
