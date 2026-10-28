package com.lostclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.screen.slot.SlotActionType;

public final class InvUtil {
    private InvUtil() {}

    /** Finds an item in the player screen handler's main + hotbar slots (9..44). */
    public static int findSlot(MinecraftClient mc, Item item) {
        var slots = mc.player.playerScreenHandler.slots;
        for (int i = 9; i <= 44; i++) {
            if (slots.get(i).getStack().isOf(item)) return i;
        }
        return -1;
    }

    /** Swaps the given screen-handler slot with the offhand (button 40 = offhand swap). */
    public static void swapToOffhand(MinecraftClient mc, int slot) {
        mc.interactionManager.clickSlot(
                mc.player.playerScreenHandler.syncId, slot, 40, SlotActionType.SWAP, mc.player);
    }
}
