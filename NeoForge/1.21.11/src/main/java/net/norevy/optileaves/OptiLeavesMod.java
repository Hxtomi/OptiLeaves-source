package net.norevy.optileaves;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.norevy.optileaves.config.Settings;
import net.norevy.optileaves.ui.OptiLeavesScreen;
import org.slf4j.Logger;
@Mod(value="optileaves",dist=Dist.CLIENT)
public final class OptiLeavesMod {
 public static final Logger LOGGER=LogUtils.getLogger();
 public OptiLeavesMod(ModContainer container) {
  Settings.INSTANCE.load();
  container.registerExtensionPoint(IConfigScreenFactory.class,(mod,parent)->new OptiLeavesScreen(parent));
  LOGGER.info("OptiLeaves 2.0: enabled={}, depth={}",Settings.INSTANCE.enabled,Settings.INSTANCE.depth);
 }
}
