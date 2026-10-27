package com.devzx.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class DevzxHud {
    private static final int NEON = 0xFF35F2D0;
    private static final int PANEL = 0xB9101420;
    private static long startTime = System.currentTimeMillis();

    private DevzxHud() {}

    public static void render(MinecraftClient client, DrawContext ctx) {
        if (client.player == null || client.options.hudHidden || client.currentScreen instanceof DevzxClickGui
                || client.currentScreen instanceof DevzxMenuScreen) return;
        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();
        int y = 8;

        if (DevzxClient.hudEnabled) {
            ctx.fill(8, y, 118, y + 24, PANEL);
            ctx.fill(8, y, 10, y + 24, NEON);
            ctx.drawTextWithShadow(client.textRenderer, Text.literal("devzx").formatted(Formatting.AQUA, Formatting.BOLD), 15, y + 4, NEON);
            ctx.drawTextWithShadow(client.textRenderer, Text.literal("1.21.1  •  " + client.getCurrentFps() + " FPS"), 15, y + 14, 0xFFE7EDF7);
        }

        if (DevzxClient.cpsEnabled) {
            String cps = "CPS  " + DevzxClient.getCps();
            int width = client.textRenderer.getWidth(cps) + 12;
            ctx.fill(w - width - 8, 8, w - 8, 25, PANEL);
            ctx.fill(w - width - 8, 8, w - width - 6, 25, NEON);
            ctx.drawTextWithShadow(client.textRenderer, cps, w - width - 2, 13, 0xFFFFFFFF);
        }

        if (DevzxClient.keystrokesEnabled) drawKeystrokes(client, ctx, w - 77, h - 77);
        if (DevzxClient.armorEnabled) drawArmor(client, ctx, 8, h - 42);
        if (DevzxClient.customCrosshairEnabled) drawCrosshair(client, ctx, w / 2, h / 2);
    }

    private static void drawKeystrokes(MinecraftClient client, DrawContext ctx, int x, int y) {
        int keyW = 21, keyH = 18, gap = 3;
        drawKey(client, ctx, x + keyW + gap, y, "W", client.options.forwardKey.isPressed());
        drawKey(client, ctx, x, y + keyH + gap, "A", client.options.leftKey.isPressed());
        drawKey(client, ctx, x + keyW + gap, y + keyH + gap, "S", client.options.backKey.isPressed());
        drawKey(client, ctx, x + (keyW + gap) * 2, y + keyH + gap, "D", client.options.rightKey.isPressed());
        drawKey(client, ctx, x, y + (keyH + gap) * 2, "JUMP", client.options.jumpKey.isPressed(), keyW * 2 + gap);
    }

    private static void drawKey(MinecraftClient client, DrawContext ctx, int x, int y, String label, boolean down) {
        drawKey(client, ctx, x, y, label, down, 21);
    }

    private static void drawKey(MinecraftClient client, DrawContext ctx, int x, int y, String label, boolean down, int width) {
        ctx.fill(x, y, x + width, y + 18, down ? 0xDD14594F : PANEL);
        ctx.fill(x, y + 17, x + width, y + 18, down ? NEON : 0xFF343B4B);
        int textW = client.textRenderer.getWidth(label);
        ctx.drawTextWithShadow(client.textRenderer, label, x + (width - textW) / 2, y + 5, down ? 0xFFFFFFFF : 0xFFDCE5F2);
    }

    private static void drawArmor(MinecraftClient client, DrawContext ctx, int x, int y) {
        int slot = 0;
        for (ItemStack stack : client.player.getArmorItems()) {
            int bx = x + slot * 22;
            ctx.fill(bx, y, bx + 20, y + 28, PANEL);
            if (!stack.isEmpty()) {
                ctx.drawItem(stack, bx + 2, y + 2);
                ctx.drawItemInSlot(client.textRenderer, stack, bx + 2, y + 2);
            } else {
                ctx.drawTextWithShadow(client.textRenderer, "-", bx + 7, y + 9, 0xFF687386);
            }
            slot++;
        }
    }

    private static void drawCrosshair(MinecraftClient client, DrawContext ctx, int cx, int cy) {
        // Paint a tiny dark backing over the vanilla crosshair, then draw the neon replacement.
        ctx.fill(cx - 5, cy - 5, cx + 5, cy + 5, 0x990A0D14);
        ctx.fill(cx - 1, cy - 5, cx + 1, cy - 2, NEON);
        ctx.fill(cx - 1, cy + 2, cx + 1, cy + 5, NEON);
        ctx.fill(cx - 5, cy - 1, cx - 2, cy + 1, NEON);
        ctx.fill(cx + 2, cy - 1, cx + 5, cy + 1, NEON);
    }
}
