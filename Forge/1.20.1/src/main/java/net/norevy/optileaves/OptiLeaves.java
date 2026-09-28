package net.norevy.optileaves;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(OptiLeaves.ID)
public final class OptiLeaves {
    public static final String ID = "optileaves";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OptiLeaves() {
        Config.INSTANCE.load();
        LOGGER.info("OptiLeaves 2.0: enabled={}, depth={}", Config.INSTANCE.enabled, Config.INSTANCE.depth);
    }
}
