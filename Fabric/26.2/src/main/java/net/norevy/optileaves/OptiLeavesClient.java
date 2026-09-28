package net.norevy.optileaves;

import net.fabricmc.api.ClientModInitializer;
import net.norevy.optileaves.config.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OptiLeavesClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("OptiLeaves");
    public void onInitializeClient() {
        Settings.INSTANCE.load();
        LOGGER.info("OptiLeaves 2.0 ready: enabled={}, depth={}", cfg().enabled, cfg().depth);
    }
    public static Settings cfg() { return Settings.INSTANCE; }
}
