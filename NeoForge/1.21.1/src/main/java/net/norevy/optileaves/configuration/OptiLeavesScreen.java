package net.norevy.optileaves.configuration;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class OptiLeavesScreen extends Screen {
    private final Screen parent;
    private boolean enabled = Configuration.INSTANCE.enabled;
    private int depth = Configuration.clampDepth(Configuration.INSTANCE.depth);

    public OptiLeavesScreen(Screen parent) {
        super(Component.literal("OptiLeaves"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        addRenderableWidget(Button.builder(enabledLabel(), button -> {
            enabled = !enabled;
            button.setMessage(enabledLabel());
        }).bounds(width / 2 - 100, height / 2 - 35, 200, 20).build());
        addRenderableWidget(Button.builder(depthLabel(), button -> {
            depth = depth % 4 + 1;
            button.setMessage(depthLabel());
        }).bounds(width / 2 - 100, height / 2 - 10, 200, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> onClose())
                .bounds(width / 2 - 100, height / 2 + 55, 200, 20).build());
    }

    private Component enabledLabel() { return Component.literal("OptiLeaves: " + (enabled ? "ON" : "OFF")); }
    private Component depthLabel() { return Component.literal("Leaf culling depth: " + depth); }

    @Override
    public void onClose() {
        boolean changed = enabled != Configuration.INSTANCE.enabled || depth != Configuration.INSTANCE.depth;
        Configuration.INSTANCE.enabled = enabled;
        Configuration.INSTANCE.depth = depth;
        Configuration.INSTANCE.save();
        if (changed && minecraft.level != null) minecraft.levelRenderer.allChanged();
        minecraft.setScreen(parent);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(font, title, width / 2, height / 2 - 65, 0xffffff);
        graphics.drawCenteredString(font, "1 culls more; 4 preserves more detail.", width / 2, height / 2 + 20, 0xaaaaaa);
        graphics.drawCenteredString(font, "Fast leaves always use depth 1.", width / 2, height / 2 + 33, 0xaaaaaa);
    }
}
