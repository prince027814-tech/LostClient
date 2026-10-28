package com.lostclient.module.modules;

import com.lostclient.module.Module;
import net.minecraft.client.MinecraftClient;

/** After landing a hit, drops sprint for a couple of ticks so the next hit gets full knockback. */
public class AutoWTap extends Module {
    private float lastCooldown = 1f;
    private int resprintIn = 0;

    public AutoWTap() { super("Auto W-Tap", "Resets sprint after each hit"); }

    @Override
    public void onTick(MinecraftClient mc) {
        var p = mc.player;
        float now = p.getAttackCooldownProgress(0f);
        boolean justAttacked = lastCooldown > 0.8f && now < 0.3f;
        lastCooldown = now;

        if (resprintIn > 0) {
            resprintIn--;
            p.setSprinting(false);
            if (resprintIn == 0 && mc.options.forwardKey.isPressed()) p.setSprinting(true);
            return;
        }
        if (justAttacked && p.isSprinting()) {
            p.setSprinting(false);
            resprintIn = 2;
        }
    }
}
