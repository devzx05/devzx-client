
package com.devzx.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class DevzxHud {
    private static final int NEON = 0xFF35F2D0;
    private static final int PANEL = 0xC9101420;

    private DevzxHud() {}

    public static void render(MinecraftClient client, DrawContext ctx) {
        if (client.player == null || client.options.hudHidden
                || client.currentScreen instanceof DevzxClickGui
                || client.currentScreen instanceof DevzxMenuScreen) {
            return;
        }

        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        if (DevzxClient.hudEnabled) {
            drawBrand(client, ctx, 8, 8);
        }

        if (DevzxClient.fpsEnabled) {
            drawCounter(client, ctx, "FPS  " + client.getCurrentFps(), 8, 38);
        }

        if (DevzxClient.cpsEnabled) {
            String cps = "CPS  " + DevzxClient.getCps();
            int cw = client.textRenderer.getWidth(cps) + 16;
            drawCounter(client, ctx, cps, w - cw - 8, 8);
        }

        if (DevzxClient.keystrokesEnabled) {
            drawKeystrokes(client, ctx, w - 82, h - 100);
        }

        if (DevzxClient.armorEnabled) {
            drawArmor(client, ctx, 8, h - 40);
        }

        // InGameHudMixin hides Minecraft's crosshair when this is enabled.
        if (DevzxClient.customCrosshairEnabled) {
            drawCrosshair(w / 2, h / 2, ctx);
        }
    }

    private static void drawBrand(
            MinecraftClient client, DrawContext ctx, int x, int y) {
        ctx.fill(x, y, x + 118, y + 25, PANEL);
        ctx.fill(x, y, x + 2, y + 25, NEON);

        ctx.drawTextWithShadow(
                client.textRenderer,
                Text.literal("devzx").formatted(Formatting.BOLD),
                x + 8, y + 4, NEON
        );

        ctx.drawTextWithShadow(
                client.textRenderer,
                "1.21.1",
                x + 8, y + 14, 0xFFE7EDF7
        );
    }

    private static void drawCounter(
            MinecraftClient client, DrawContext ctx,
            String label, int x, int y) {
        int width = client.textRenderer.getWidth(label) + 16;
        ctx.fill(x, y, x + width, y + 19, PANEL);
        ctx.fill(x, y, x + 2, y + 19, NEON);
        ctx.drawTextWithShadow(
                client.textRenderer, label, x + 8, y + 5, 0xFFFFFFFF
        );
    }

    private static void drawKeystrokes(
            MinecraftClient client, DrawContext ctx, int x, int y) {
        int keyW = 22;
        int keyH = 19;
        int gap = 3;

        drawKey(client, ctx, x + keyW + gap, y, "W",
                client.options.forwardKey.isPressed(), keyW);
        drawKey(client, ctx, x, y + keyH + gap, "A",
                client.options.leftKey.isPressed(), keyW);
        drawKey(client, ctx, x + keyW + gap, y + keyH + gap, "S",
                client.options.backKey.isPressed(), keyW);
        drawKey(client, ctx, x + (keyW + gap) * 2,
                y + keyH + gap, "D",
                client.options.rightKey.isPressed(), keyW);
        drawKey(client, ctx, x, y + (keyH + gap) * 2, "JUMP",
                client.options.jumpKey.isPressed(), keyW * 2 + gap);
    }

    private static void drawKey(
            MinecraftClient client, DrawContext ctx, int x, int y,
            String label, boolean down, int width) {
        ctx.fill(x, y, x + width, y + 19,
                down ? 0xDD14594F : PANEL);
        ctx.fill(x, y + 18, x + width, y + 19,
                down ? NEON : 0xFF343B4B);

        int textW = client.textRenderer.getWidth(label);
        ctx.drawTextWithShadow(
                client.textRenderer, label,
                x + (width - textW) / 2, y + 5,
                down ? 0xFFFFFFFF : 0xFFDCE5F2
        );
    }

    
private static void drawArmor(
        MinecraftClient client, DrawContext ctx, int x, int y) {
    int slot = 0;

    for (ItemStack stack : client.player.getArmorItems()) {
        int bx = x + slot * 23;
        ctx.fill(bx, y, bx + 21, y + 23, PANEL);

        if (!stack.isEmpty()) {
            ctx.drawItem(stack, bx + 2, y + 2);
            ctx.drawStackOverlay(
                    client.textRenderer, stack, bx + 2, y + 2
            );
        } else {
            ctx.drawTextWithShadow(
                    client.textRenderer, "-", bx + 8, y + 7,
                    0xFF687386
            );
        }
        slot++;
    }
}


    private static void drawCrosshair(
            int cx, int cy, DrawContext ctx) {
        ctx.fill(cx - 1, cy - 5, cx + 1, cy - 2, NEON);
        ctx.fill(cx - 1, cy + 2, cx + 1, cy + 5, NEON);
        ctx.fill(cx - 5, cy - 1, cx - 2, cy + 1, NEON);
        ctx.fill(cx + 2, cy - 1, cx + 5, cy + 1, NEON);
    }
}
