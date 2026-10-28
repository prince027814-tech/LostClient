package com.lostclient.module;

import com.lostclient.module.modules.*;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();
    /** When true, the on-screen module list is hidden (modules keep running). */
    public boolean hidden = false;

    public ModuleManager() {
        // Order matters for per-tick behaviour.
        modules.add(new AimAssist());
        modules.add(new Triggerbot());
        modules.add(new AutoWTap());
        modules.add(new ShieldBreaker());
        modules.add(new JumpReset());
        modules.add(new AutoTotem());
        modules.add(new AutoTotemLegit());
    }

    public List<Module> all() { return modules; }

    public void tick(MinecraftClient mc) {
        for (Module m : modules) if (m.isEnabled()) m.onTick(mc);
    }
}
