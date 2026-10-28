package com.lostclient;

import com.lostclient.gui.ClickGui;
import com.lostclient.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class LostClient implements ClientModInitializer {
    public static final ModuleManager MODULES = new ModuleManager();
    private static KeyBinding menuKey;
    private static KeyBinding hideKey;

    @Override
    public void onInitializeClient() {
        menuKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lostclient.menu", GLFW.GLFW_KEY_RIGHT_SHIFT, "category.lostclient"));
        hideKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lostclient.hide", GLFW.GLFW_KEY_H, "category.lostclient"));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (menuKey.wasPressed()) mc.setScreen(new ClickGui());
            while (hideKey.wasPressed()) MODULES.hidden = !MODULES.hidden;
            if (mc.player != null && mc.world != null) MODULES.tick(mc);
        });

        HudRenderCallback.EVENT.register((ctx, tickCounter) -> {
            if (MODULES.hidden) return;
            MinecraftClient mc = MinecraftClient.getInstance();
            int y = 4;
            for (var m : MODULES.all()) {
                if (!m.isEnabled()) continue;
                int w = mc.textRenderer.getWidth(m.getName());
                ctx.fill(2, y - 1, 6 + w + 2, y + 10, 0xCC000000);
                ctx.drawText(mc.textRenderer, m.getName(), 4, y, 0xFFFFFFFF, false);
                y += 11;
            }
        });
    }
}
