package com.lostclient.module.modules;

import com.lostclient.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/** Gently pulls the crosshair toward the closest player in your FOV while left click is held. */
public class AimAssist extends Module {
    private static final double RANGE = 4.5;
    private static final float FOV = 45f;      // max angle off-crosshair to consider
    private static final float SMOOTH = 0.12f; // 0..1, lower = subtler
    private static final float MAX_STEP = 4f;  // max degrees per tick

    public AimAssist() { super("Aim Assist", "Smooth aim toward nearest player while holding left click"); }

    @Override
    public void onTick(MinecraftClient mc) {
        if (mc.currentScreen != null || !mc.options.attackKey.isPressed()) return;

        var p = mc.player;
        Vec3d eye = p.getEyePos();
        float bestYaw = 0, bestPitch = 0, bestAngle = FOV;
        boolean found = false;

        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof PlayerEntity target) || target == p || !target.isAlive()) continue;
            if (p.distanceTo(target) > RANGE) continue;

            Vec3d diff = target.getBoundingBox().getCenter().subtract(eye);
            float yaw = (float) Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90f;
            float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, Math.hypot(diff.x, diff.z)));
            float dYaw = MathHelper.wrapDegrees(yaw - p.getYaw());
            float dPitch = pitch - p.getPitch();
            float angle = (float) Math.hypot(dYaw, dPitch);

            if (angle < bestAngle) {
                bestAngle = angle; bestYaw = dYaw; bestPitch = dPitch; found = true;
            }
        }
        if (!found) return;

        p.setYaw(p.getYaw() + MathHelper.clamp(bestYaw * SMOOTH, -MAX_STEP, MAX_STEP));
        p.setPitch(MathHelper.clamp(
                p.getPitch() + MathHelper.clamp(bestPitch * SMOOTH, -MAX_STEP, MAX_STEP), -90f, 90f));
    }
}
