package net.norevy.optileaves.ui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class MenuHook implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() { return OptiLeavesScreen::new; }
}
