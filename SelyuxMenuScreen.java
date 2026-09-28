package com.fadhil.selyuxmenu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class SelyuxMenuScreen extends Screen {
    private static final int BUTTON_W = 310;
    private static final int BUTTON_H = 40;
    private static final int GAP = 6;

    protected SelyuxMenuScreen() {
        super(Text.literal("Selyux Menu"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int leftX = centerX - BUTTON_W - 5;
        int rightX = centerX + 5;
        int topY = this.height / 2 - 145;

        // Left column
        addButton(leftX, topY, "Homes", "/home");
        addButton(leftX, topY + (BUTTON_H + GAP), "Auction", "/ah");
        addButton(leftX, topY + 2 * (BUTTON_H + GAP), "Shop", "/shop");
        addButton(leftX, topY + 3 * (BUTTON_H + GAP), "Shard Shop", "/shardshop");
        addButton(leftX, topY + 4 * (BUTTON_H + GAP), "Pay", "/pay");
        addButton(leftX, topY + 5 * (BUTTON_H + GAP), "Stats", "/stats");

        // Right column
        addButton(rightX, topY, "RTP", "/rtp");
        addButton(rightX, topY + (BUTTON_H + GAP), "RTP Queue", "/rtpqueue");
        addButton(rightX, topY + 2 * (BUTTON_H + GAP), "Orders", "/orders");
        addButton(rightX, topY + 3 * (BUTTON_H + GAP), "Sell", "/sell");
        addButton(rightX, topY + 4 * (BUTTON_H + GAP), "Teleport", null);
        addButton(rightX, topY + 5 * (BUTTON_H + GAP), "Leaderboards", "/leaderboards");

        // Bottom settings
        addButton(centerX - BUTTON_W / 2, topY + 6 * (BUTTON_H + GAP) + 8,
                "Settings", "/settings");
    }

    private void addButton(int x, int y, String label, String command) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal(label), button -> runCommand(command))
                .dimensions(x, y, BUTTON_W, BUTTON_H)
                .build());
    }

    private void runCommand(String command) {
        if (command == null) {
            MinecraftClient.getInstance().setScreen(new TeleportScreen(this));
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.networkHandler.sendChatCommand(command.substring(1));
        }
        close();
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(null);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Transparent/dimmed background, keeping the world visible like the reference.
        context.fill(0, 0, width, height, 0x55000000);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private static class TeleportScreen extends Screen {
        private final Screen parent;

        protected TeleportScreen(Screen parent) {
            super(Text.literal("Teleport"));
            this.parent = parent;
        }

        @Override
        protected void init() {
            int w = 320;
            int x = (this.width - w) / 2;
            int y = this.height / 2 - 50;

            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal("TPA"),
                    b -> openCommand("tpa")
            ).dimensions(x, y, w, 40).build());

            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal("TPAHERE"),
                    b -> openCommand("tpahere")
            ).dimensions(x, y + 46, w, 40).build());

            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal("Back"),
                    b -> MinecraftClient.getInstance().setScreen(parent)
            ).dimensions(x, y + 92, w, 40).build());
        }

        private void openCommand(String command) {
            MinecraftClient.getInstance().setScreen(
                    new net.minecraft.client.gui.screen.ChatScreen("/" + command + " ")
            );
        }

        @Override
        public boolean shouldPause() {
            return false;
        }
    }

}
