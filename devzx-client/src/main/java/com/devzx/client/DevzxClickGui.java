
package com.devzx.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class DevzxClickGui extends Screen {
    private static final int NEON = 0xFF35F2D0;
    private static final int PANEL = 0xF0101420;

    private int panelX, panelY;
    private final int panelW = 340;
    private final int panelH = 290;

    public DevzxClickGui() {
        super(Text.literal("devzx client | ClickGUI"));
    }

    @Override
    protected void init() {
        panelX = (width - panelW) / 2;
        panelY = (height - panelH) / 2;
        clearChildren();

        int x1 = panelX + 16;
        int x2 = panelX + 178;
        int y = panelY + 54;
        int bw = 146;
        int bh = 20;
        int gap = 27;

        addToggle(x1, y, bw, bh, "HUD", 
                () -> DevzxClient.hudEnabled,
                v -> DevzxClient.hudEnabled = v);
        addToggle(x2, y, bw, bh, "FPS Counter",
                () -> DevzxClient.fpsEnabled,
                v -> DevzxClient.fpsEnabled = v);

        addToggle(x1, y + gap, bw, bh, "CPS Counter",
                () -> DevzxClient.cpsEnabled,
                v -> DevzxClient.cpsEnabled = v);
        addToggle(x2, y + gap, bw, bh, "Keystrokes",
                () -> DevzxClient.keystrokesEnabled,
                v -> DevzxClient.keystrokesEnabled = v);

        addToggle(x1, y + gap * 2, bw, bh, "Armor Status",
                () -> DevzxClient.armorEnabled,
                v -> DevzxClient.armorEnabled = v);
        addToggle(x2, y + gap * 2, bw, bh, "Neon Crosshair",
                () -> DevzxClient.customCrosshairEnabled,
                v -> DevzxClient.customCrosshairEnabled = v);

        addToggle(x1, y + gap * 3, bw, bh, "Animations",
                () -> DevzxClient.animationsEnabled,
                v -> DevzxClient.animationsEnabled = v);
        addToggle(x2, y + gap * 3, bw, bh, "FPS Helper",
                () -> DevzxClient.fpsOptimizationEnabled,
                v -> DevzxClient.fpsOptimizationEnabled = v);

        addToggle(x1, y + gap * 4, bw, bh, "Cosmetics Theme",
                () -> DevzxClient.cosmeticsEnabled,
                v -> DevzxClient.cosmeticsEnabled = v);

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Done"), b -> close()
        ).dimensions(panelX + 110, panelY + 258, 120, 22).build());
    }

    private void addToggle(
            int x, int y, int w, int h, String label,
            java.util.function.BooleanSupplier getter,
            java.util.function.Consumer<Boolean> setter) {
        addDrawableChild(ButtonWidget.builder(
                Text.literal(label + ": " +
                        (getter.getAsBoolean() ? "ON" : "OFF")),
                b -> {
                    boolean next = !getter.getAsBoolean();
                    setter.accept(next);
                    b.setMessage(Text.literal(
                            label + ": " + (next ? "ON" : "OFF")));
                }
        ).dimensions(x, y, w, h).build());
    }

    @Override
    public void render(
            DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.fill(panelX, panelY,
                panelX + panelW, panelY + panelH, PANEL);
        ctx.fill(panelX, panelY,
                panelX + panelW, panelY + 3, NEON);

        ctx.drawTextWithShadow(
                textRenderer, "devzx client",
                panelX + 16, panelY + 14, NEON);
        ctx.drawTextWithShadow(
                textRenderer, "HUD  /  VISUALS  /  PERFORMANCE",
                panelX + 16, panelY + 32, 0xFFABB7C9);

        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
