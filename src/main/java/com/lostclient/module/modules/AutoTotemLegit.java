package com.lostclient.module.modules;

import com.lostclient.module.Module;
import com.lostclient.util.InvUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;

/** Human-like: only swaps while your inventory is open, with a short reaction delay. */
public class AutoTotemLegit extends Module {
    private int delay = -1;

    public AutoTotemLegit() { super("Auto Totem Legit", "Swap totem only with inventory open + delay"); }

    @Override
    public void onTick(MinecraftClient mc) {
        if (!(mc.currentScreen instanceof InventoryScreen)
                || mc.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            delay = -1;
            return;
        }
        int slot = InvUtil.findSlot(mc, Items.TOTEM_OF_UNDYING);
        if (slot == -1) return;
        if (delay == -1) delay = 3 + mc.world.random.nextInt(4); // 3-6 ticks
        if (delay-- == 0) {
            InvUtil.swapToOffhand(mc, slot);
            delay = -1;
        }
    }
}
