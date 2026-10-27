
package com.devzx.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class DevzxMenuScreen extends Screen {
    private static final int TEAL = 0xFF35F2D0;
    private static final int BG = 0xFF080B11;
    private static final int PANEL = 0xFF101720;
    private static final int CARD = 0xFF19232E;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int MUTED = 0xFFB8C5D6;

    private int panelX, panelY;
    private final int panelW = 340;
    private final int panelH = 210;

    public DevzxMenuScreen() {
        super(Text.literal("devzx client"));
    }

    @Override
    protected void init() {
        panelX = (width - panelW) / 2;
        panelY = (height - panelH) / 2;
    }

    @Override
    public void render(
            DrawContext ctx, int mouseX, int mouseY, float delta) {

        // Solid background for clear, readable text.
        ctx.fill(0, 0, width, height, BG);

        // Main panel and teal accent.
        ctx.fill(panelX, panelY,
                panelX + panelW, panelY + panelH, PANEL);
        ctx.fill(panelX, panelY,
                panelX + panelW, panelY + 3, TEAL);

        // Header.
        ctx.drawTextWithShadow(
                textRenderer, "devzx client",
                panelX + 18, panelY + 17, TEAL);
        ctx.drawTextWithShadow(
                textRenderer, "CLIENT MENU  /  1.21.1",
                panelX + 18, panelY + 34, MUTED);

        // Open ClickGUI card.
        drawCard(ctx, mouseX, mouseY,
                panelX + 18, panelY + 66, panelW - 36, 42,
                "OPEN CLICKGUI", "Customize your HUD and visuals");

        // Close menu card.
        drawCard(ctx, mouseX, mouseY,
                panelX + 18, panelY + 116, panelW - 36, 42,
                "CLOSE MENU", "Return to the game");

        ctx.drawTextWithShadow(
                textRenderer, "RIGHT SHIFT  /  CLICKGUI",
                panelX + 18, panelY + panelH - 19, MUTED);
    }

    private void drawCard(
            DrawContext ctx, int mouseX, int mouseY,
            int x, int y, int w, int h,
            String title, String subtitle) {

        boolean hovered = inside(mouseX, mouseY, x, y, w, h);

        ctx.fill(x, y, x + w, y + h,
                hovered ? 0xFF22343D : CARD);
        ctx.fill(x, y, x + 3, y + h, TEAL);

        ctx.drawTextWithShadow(
                textRenderer, title, x + 12, y + 8, WHITE);
        ctx.drawTextWithShadow(
                textRenderer, subtitle, x + 12, y + 24, MUTED);

        if (hovered) {
            ctx.fill(x + w - 5, y + 8,
                    x + w - 3, y + h - 8, TEAL);
        }
    }

    private static boolean inside(
            double mx, double my,
            int x, int y, int w, int h) {
        return mx >= x && mx < x + w
                && my >= y && my < y + h;
    }

    @Override
    public boolean mouseClicked(
            double mouseX, double mouseY, int button) {

        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        if (inside(mouseX, mouseY,
                panelX + 18, panelY + 66, panelW - 36, 42)) {
            if (client != null) {
                client.setScreen(new DevzxClickGui());
            }
            return true;
        }

        if (inside(mouseX, mouseY,
                panelX + 18, panelY + 116, panelW - 36, 42)) {
            close();
            return true;
        }

        return true;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
