package com.itemdrops;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
        name = "Item Drops",
        description = "Shows all monsters that drop a selected item"
)
public class ItemDropsPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private ClientToolbar clientToolbar;

    private NavigationButton navButton;

    @Override
    protected void startUp() {
        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
        navButton = NavigationButton.builder()
                .tooltip("Item Drops")
                .icon(icon)
                .priority(7)
                .build();
        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() {
        clientToolbar.removeNavigation(navButton);
    }
}
