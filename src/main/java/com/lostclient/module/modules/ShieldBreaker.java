package com.lostclient.module.modules;

import com.lostclient.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.hit.EntityHitResult;

/** Switches to an axe when the crosshair target is blocking, then switches back. */
public class ShieldBreaker extends Module {
    private int previousSlot = -1;

    public ShieldBreaker() { super("Shield Breaker", "Auto-swap to axe vs blocking targets"); }

    @Override
    public void onDisable() { previousSlot = -1; }

    @Override
    public void onTick(MinecraftClient mc) {
        var inv = mc.player.getInventory();
        boolean targetBlocking = mc.options.attackKey.isPressed()
                && mc.crosshairTarget instanceof EntityHitResult hit
                && hit.getEntity() instanceof LivingEntity le
                && le.isBlocking();

        if (targetBlocking) {
            for (int i = 0; i < 9; i++) {
                if (inv.getStack(i).getItem() instanceof AxeItem) {
                    if (inv.selectedSlot != i) {
                        if (previousSlot == -1) previousSlot = inv.selectedSlot;
                        inv.selectedSlot = i;
                    }
                    return;
                }
            }
        } else if (previousSlot != -1) {
            inv.selectedSlot = previousSlot;
            previousSlot = -1;
        }
    }
}
