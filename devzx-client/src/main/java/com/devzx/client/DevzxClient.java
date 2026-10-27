package com.devzx.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class DevzxClient implements ClientModInitializer {
    public static final String BRAND = "devzx client";
    public static boolean hudEnabled = true;
    public static boolean keystrokesEnabled = true;
    public static boolean cpsEnabled = true;
    public static boolean armorEnabled = true;
    public static boolean customCrosshairEnabled = true;
    public static boolean cosmeticsEnabled = true;
    public static boolean animationsEnabled = true;
    public static boolean fpsOptimizationEnabled = true;

    private static KeyBinding clickGuiKey;
    private static KeyBinding menuKey;
    private static boolean wasLeftDown;
    private static long[] clickTimes = new long[32];
    private static int clickIndex;

    @Override
    public void onInitializeClient() {
        clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.devzxclient.clickgui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.devzxclient"));
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.devzxclient.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_CONTROL,
                "category.devzxclient"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new DevzxClickGui());
            }
            while (menuKey.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new DevzxMenuScreen());
            }
            countClicks(client);
        });
        HudRenderCallback.EVENT.register((drawContext, tickCounter) ->
                DevzxHud.render(MinecraftClient.getInstance(), drawContext));
    }

    private static void countClicks(MinecraftClient client) {
        if (client.getWindow() == null || client.currentScreen != null) return;
        boolean leftDown = GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT)
                == GLFW.GLFW_PRESS;
        if (leftDown && !wasLeftDown) {
            clickTimes[clickIndex] = System.currentTimeMillis();
            clickIndex = (clickIndex + 1) % clickTimes.length;
        }
        wasLeftDown = leftDown;
    }

    public static int getCps() {
        long cutoff = System.currentTimeMillis() - 1000;
        int count = 0;
        for (long time : clickTimes) if (time >= cutoff) count++;
        return count;
    }
}
