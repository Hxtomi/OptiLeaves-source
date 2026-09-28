package net.norevy.optileaves.common;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.norevy.optileaves.configuration.OptiLeavesScreen;
import net.norevy.optileaves.configuration.Configuration;
import org.slf4j.Logger;

@Mod(value = OptiLeavesMod.MOD_ID, dist = Dist.CLIENT)
public final class OptiLeavesMod {
    public static final String MOD_ID = "optileaves";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OptiLeavesMod(ModContainer container) {
        Configuration.INSTANCE.load();
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new OptiLeavesScreen(parent));
        LOGGER.info("OptiLeaves 2.0: enabled={}, depth={}", Configuration.INSTANCE.enabled, Configuration.INSTANCE.depth);
    }
}
