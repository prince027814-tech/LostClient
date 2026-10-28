package com.lostclient.gui;

import com.lostclient.LostClient;
import com.lostclient.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

/** Black panel, white text. Click a row to toggle. Scroll if the list is long. */
public class ClickGui extends Screen {
    private static final int W = 190, ROW = 14, HEAD = 18;
    private int scroll = 0;

    public ClickGui() { super(Text.literal("Lost Client")); }

    private int panelX() { return (width - W) / 2; }
    private int panelY() { return 20; }
    private int visibleRows() { return Math.max(3, (height - 60 - HEAD) / ROW); }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        ctx.fill(0, 0, width, height, 0x88000000);
        List<Module> mods = LostClient.MODULES.all();
        int rows = Math.min(mods.size() + 1, visibleRows());
        int x = panelX(), y = panelY();

        ctx.fill(x, y, x + W, y + HEAD + rows * ROW + 4, 0xFF000000);
        ctx.drawBorder(x, y, W, HEAD + rows * ROW + 4, 0xFFFFFFFF);
        ctx.drawText(textRenderer, "LOST CLIENT", x + 6, y + 5, 0xFFFFFFFF, false);

        for (int i = 0; i < rows; i++) {
            int ry = y + HEAD + i * ROW;
            boolean hover = mx >= x && mx < x + W && my >= ry && my < ry + ROW;
            if (hover) ctx.fill(x + 1, ry, x + W - 1, ry + ROW, 0xFF222222);

            int idx = i + scroll;
            if (idx == 0) {
                label(ctx, x, ry, "Hide all modules", !LostClient.MODULES.hidden ? "OFF" : "ON",
                        LostClient.MODULES.hidden);
            } else if (idx - 1 < mods.size()) {
                Module m = mods.get(idx - 1);
                label(ctx, x, ry, m.getName(), m.isEnabled() ? "ON" : "OFF", m.isEnabled());
            }
        }
    }

    private void label(DrawContext ctx, int x, int ry, String name, String state, boolean on) {
        ctx.drawText(textRenderer, name, x + 6, ry + 3, 0xFFFFFFFF, false);
        int c = on ? 0xFFFFFFFF : 0xFF777777;
        ctx.drawText(textRenderer, state, x + W - 6 - textRenderer.getWidth(state), ry + 3, c, false);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int x = panelX(), y = panelY();
        if (mx >= x && mx < x + W && my >= y + HEAD) {
            int i = (int) ((my - y - HEAD) / ROW) + scroll;
            List<Module> mods = LostClient.MODULES.all();
            if (i == 0) {
                LostClient.MODULES.hidden = !LostClient.MODULES.hidden;
                return true;
            } else if (i - 1 >= 0 && i - 1 < mods.size()) {
                mods.get(i - 1).toggle();
                return true;
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double h, double v) {
        int max = Math.max(0, LostClient.MODULES.all().size() + 1 - visibleRows());
        scroll = Math.max(0, Math.min(max, scroll - (int) Math.signum(v)));
        return true;
    }

    @Override
    public boolean shouldPause() { return false; }
}
