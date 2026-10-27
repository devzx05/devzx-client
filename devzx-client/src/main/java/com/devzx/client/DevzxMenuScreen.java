package com.devzx.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class DevzxMenuScreen extends Screen {
    public DevzxMenuScreen() { super(Text.literal("devzx client")); }

    @Override
    protected void init() {
        int center = width / 2;
        addDrawableChild(ButtonWidget.builder(Text.literal("Open ClickGUI"), b -> client.setScreen(new DevzxClickGui()))
                .dimensions(center - 90, height / 2 + 8, 180, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), b -> close())
                .dimensions(center - 90, height / 2 + 38, 180, 22).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.fill(0, 0, width, height, 0xAA080B12);
        ctx.fill(width / 2 - 130, height / 2 - 60, width / 2 + 130, height / 2 + 70, 0xF0101420);
        ctx.fill(width / 2 - 130, height / 2 - 60, width / 2 + 130, height / 2 - 57, 0xFF35F2D0);
        String title = "devzx client";
        ctx.drawTextWithShadow(textRenderer, title, width / 2 - textRenderer.getWidth(title) / 2, height / 2 - 37, 0xFF35F2D0);
        String sub = "DARK  •  COMPETITIVE  •  NEON";
        ctx.drawTextWithShadow(textRenderer, sub, width / 2 - textRenderer.getWidth(sub) / 2, height / 2 - 18, 0xFFE0E8F5);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override public boolean shouldPause() { return false; }
}
