
package com.devzx.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class DevzxClickGui extends Screen {
private static final int TEAL = 0xFF35F2D0;
private static final int BACKGROUND = 0xFF090C12;
private static final int PANEL = 0xFF0D121A;
private static final int CARD = 0xFF171F2A;
private static final int TEXT = 0xFFFFFFFF;
private static final int MUTED = 0xFFB8C5D6;

    private static final String[] CATEGORIES = {
        "Visuals", "Performance", "Interface"
    };

    private int panelX, panelY, panelW, panelH;
    private int sidebarW = 112;
    private int selectedCategory = 0;

    private static final int CARD_H = 42;
    private static final int CARD_GAP = 6;

    public DevzxClickGui() {
        super(Text.literal("devzx client"));
    }

    @Override
protected void init() {
    panelW = 340;
    panelH = 210;
    panelX = (width - panelW) / 2;
    panelY = (height - panelH) / 2;
}
    

    @Override
    public void render(
            DrawContext ctx, int mouseX, int mouseY, float delta) {

        // Darken the world behind the menu.
        ctx.fill(0, 0, width, height, 0xFF05070B);

        // Main window and teal top edge.
        ctx.fill(panelX, panelY,
                panelX + panelW, panelY + panelH, BACKGROUND);
        ctx.fill(panelX, panelY,
                panelX + panelW, panelY + 2, TEAL);

        // Header.
        ctx.drawTextWithShadow(
        textRenderer, "devzx",
        panelX + 16, panelY + 13, TEAL);

ctx.drawTextWithShadow(
        textRenderer, "CLIENT  /  1.21.1",
        panelX + 67, panelY + 14, MUTED);

ctx.drawTextWithShadow(
        textRenderer, "X",
        panelX + panelW - 20, panelY + 14, TEXT);

        ctx.fill(panelX + 1, panelY + 34,
                panelX + panelW - 1, panelY + 35, 0xFF26313D);

        // Sidebar.
        int sideX = panelX + 1;
        int sideY = panelY + 35;
        int sideBottom = panelY + panelH - 1;

        ctx.fill(sideX, sideY,
                sideX + sidebarW, sideBottom, 0xFF0D121A);

        
ctx.drawTextWithShadow(
        textRenderer, "CATEGORIES",
        sideX + 12, sideY + 15, MUTED);


        for (int i = 0; i < CATEGORIES.length; i++) {
            int itemY = sideY + 31 + i * 31;
            boolean selected = selectedCategory == i;
            boolean hovered = inside(
                    mouseX, mouseY,
                    sideX + 7, itemY,
                    sidebarW - 14, 25);

            if (selected) {
                ctx.fill(sideX + 7, itemY,
                        sideX + sidebarW - 7, itemY + 25,
                        0xFF17312F);
                ctx.fill(sideX + 7, itemY,
                        sideX + 9, itemY + 25, TEAL);
            } else if (hovered) {
                ctx.fill(sideX + 7, itemY,
                        sideX + sidebarW - 7, itemY + 25,
                        0xFF19212C);
            }

            
ctx.drawTextWithShadow(
        textRenderer, CATEGORIES[i],
        sideX + 15, itemY + 8,
        selected ? TEAL : TEXT);

        }

        
ctx.drawTextWithShadow(
        textRenderer, "RIGHT SHIFT",
        sideX + 12, sideBottom - 28, MUTED);

ctx.drawTextWithShadow(
        textRenderer, "to toggle",
        sideX + 12, sideBottom - 17, MUTED);


        // Content area.
        int contentX = panelX + sidebarW + 16;
        int contentW = panelW - sidebarW - 28;
        int contentY = panelY + 49;

        
ctx.drawTextWithShadow(
        textRenderer, CATEGORIES[selectedCategory],
        contentX, contentY, TEXT);

ctx.drawTextWithShadow(
        textRenderer, "Toggle modules to customize your client",
        contentX, contentY + 15, MUTED);


        int cardY = contentY + 35;

        if (selectedCategory == 0) {
            drawModule(ctx, mouseX, mouseY, contentX, cardY,
                    contentW, "HUD", "Brand display",
                    () -> DevzxClient.hudEnabled);
            drawModule(ctx, mouseX, mouseY, contentX,
                    cardY + CARD_H + CARD_GAP, contentW,
                    "FPS Counter", "Live frame-rate display",
                    () -> DevzxClient.fpsEnabled);
            drawModule(ctx, mouseX, mouseY, contentX,
                    cardY + (CARD_H + CARD_GAP) * 2, contentW,
                    "CPS Counter", "Clicks per second",
                    () -> DevzxClient.cpsEnabled);
            drawModule(ctx, mouseX, mouseY, contentX,
                    cardY + (CARD_H + CARD_GAP) * 3, contentW,
                    "Keystrokes", "Show movement keys",
                    () -> DevzxClient.keystrokesEnabled);
            drawModule(ctx, mouseX, mouseY, contentX,
                    cardY + (CARD_H + CARD_GAP) * 4, contentW,
                    "Armor Status", "Display equipped armor",
                    () -> DevzxClient.armorEnabled);
            drawModule(ctx, mouseX, mouseY, contentX,
                    cardY + (CARD_H + CARD_GAP) * 5, contentW,
                    "Neon Crosshair", "Custom teal crosshair",
                    () -> DevzxClient.customCrosshairEnabled);
        } else if (selectedCategory == 1) {
            drawModule(ctx, mouseX, mouseY, contentX, cardY,
                    contentW, "FPS Helper", "Performance setting toggle",
                    () -> DevzxClient.fpsOptimizationEnabled);
        } else {
            drawModule(ctx, mouseX, mouseY, contentX, cardY,
                    contentW, "Animations", "Animation option",
                    () -> DevzxClient.animationsEnabled);
            drawModule(ctx, mouseX, mouseY, contentX,
                    cardY + CARD_H + CARD_GAP, contentW,
                    "Cosmetics Theme", "Client theme option",
                    () -> DevzxClient.cosmeticsEnabled);
        }

        super.render(ctx, mouseX, mouseY, delta);
    }

    private void drawModule(
            DrawContext ctx, int mouseX, int mouseY,
            int x, int y, int w, String name, String description,
            java.util.function.BooleanSupplier enabled) {

        boolean hovered = inside(mouseX, mouseY, x, y, w, CARD_H);
        boolean on = enabled.getAsBoolean();

        ctx.fill(x, y, x + w, y + CARD_H,
                hovered ? 0xFF202B38 : CARD);
        ctx.fill(x, y, x + 2, y + CARD_H,
                on ? TEAL : 0xFF303A47);

        
ctx.drawTextWithShadow(
        textRenderer, name, x + 11, y + 9, TEXT);

ctx.drawTextWithShadow(
        textRenderer, description, x + 11, y + 25, MUTED);


        int switchW = 28;
        int switchH = 14;
        int sx = x + w - switchW - 11;
        int sy = y + 17;

        ctx.fill(sx, sy, sx + switchW, sy + switchH,
                on ? 0xFF176C60 : 0xFF343E4B);

        int dotX = on ? sx + switchW - 11 : sx + 3;
        ctx.fill(dotX, sy + 3, dotX + 8, sy + 11,
                on ? TEAL : 0xFFB4BFCC);
    }

    private static boolean inside(
            double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w
                && my >= y && my < y + h;
    }

    @Override
    public boolean mouseClicked(
            double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);

        int sideX = panelX + 1;
        int sideY = panelY + 35;

        for (int i = 0; i < CATEGORIES.length; i++) {
            int itemY = sideY + 31 + i * 31;
            if (inside(mouseX, mouseY,
                    sideX + 7, itemY, sidebarW - 14, 25)) {
                selectedCategory = i;
                return true;
            }
        }

        int contentX = panelX + sidebarW + 16;
        int contentW = panelW - sidebarW - 28;
        int cardY = panelY + 49 + 35;

        if (selectedCategory == 0) {
            if (hit(mouseX, mouseY, contentX, cardY, contentW))
                DevzxClient.hudEnabled = !DevzxClient.hudEnabled;
            else if (hit(mouseX, mouseY, contentX,
                    cardY + 57, contentW))
                DevzxClient.fpsEnabled = !DevzxClient.fpsEnabled;
            else if (hit(mouseX, mouseY, contentX,
                    cardY + 114, contentW))
                DevzxClient.cpsEnabled = !DevzxClient.cpsEnabled;
            else if (hit(mouseX, mouseY, contentX,
                    cardY + 171, contentW))
                DevzxClient.keystrokesEnabled = !DevzxClient.keystrokesEnabled;
            else if (hit(mouseX, mouseY, contentX,
                    cardY + 228, contentW))
                DevzxClient.armorEnabled = !DevzxClient.armorEnabled;
            else if (hit(mouseX, mouseY, contentX,
                    cardY + 285, contentW))
                DevzxClient.customCrosshairEnabled =
                        !DevzxClient.customCrosshairEnabled;
            else if (inside(mouseX, mouseY,
                    panelX + panelW - 28, panelY + 5, 25, 28))
                close();
        } else if (selectedCategory == 1) {
            if (hit(mouseX, mouseY, contentX, cardY, contentW))
                DevzxClient.fpsOptimizationEnabled =
                        !DevzxClient.fpsOptimizationEnabled;
        } else {
            if (hit(mouseX, mouseY, contentX, cardY, contentW))
                DevzxClient.animationsEnabled =
                        !DevzxClient.animationsEnabled;
            else if (hit(mouseX, mouseY, contentX,
                    cardY + 57, contentW))
                DevzxClient.cosmeticsEnabled =
                        !DevzxClient.cosmeticsEnabled;
        }

        return true;
    }

    private boolean hit(
            double mx, double my, int x, int y, int w) {
        return inside(mx, my, x, y, w, CARD_H);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
