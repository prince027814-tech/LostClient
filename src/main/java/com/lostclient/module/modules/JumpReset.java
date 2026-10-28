package com.lostclient.module.modules;

import com.lostclient.module.Module;
import net.minecraft.client.MinecraftClient;

/** Jumps right when you take a hit to cut horizontal knockback. */
public class JumpReset extends Module {
    public JumpReset() { super("Jump Reset", "Jump on hit to reduce knockback"); }

    @Override
    public void onTick(MinecraftClient mc) {
        var p = mc.player;
        if (p.hurtTime >= 9 && p.isOnGround()) p.jump();
    }
}
