package com.lostclient.module.modules;

import com.lostclient.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

/** Attacks the entity under the crosshair at full cooldown while left click is held. */
public class Triggerbot extends Module {
    public Triggerbot() { super("Triggerbot", "Auto-hit at full cooldown while holding left click"); }

    @Override
    public void onTick(MinecraftClient mc) {
        if (mc.currentScreen != null || !mc.options.attackKey.isPressed()) return;
        if (mc.crosshairTarget instanceof EntityHitResult hit
                && hit.getEntity() instanceof LivingEntity target
                && target.isAlive()
                && mc.player.getAttackCooldownProgress(0f) >= 0.92f) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
