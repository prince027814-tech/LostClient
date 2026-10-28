package com.lostclient.module.modules;

import com.lostclient.module.Module;
import com.lostclient.util.InvUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;

/** Keeps a totem in the offhand at all times. */
public class AutoTotem extends Module {
    private int cooldown = 0;

    public AutoTotem() { super("Auto Totem", "Always keep a totem in offhand"); }

    @Override
    public void onTick(MinecraftClient mc) {
        if (cooldown > 0) { cooldown--; return; }
        if (mc.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) return;
        int slot = InvUtil.findSlot(mc, Items.TOTEM_OF_UNDYING);
        if (slot == -1) return;
        InvUtil.swapToOffhand(mc, slot);
        cooldown = 3;
    }
}
