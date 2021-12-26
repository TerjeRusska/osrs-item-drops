package com.itemdrops;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    private ClientToolbar clientToolbar;

    @Getter(AccessLevel.PACKAGE)
    private NavigationButton navButton;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private ItemDropsPanel panel;

    @Override
    protected void startUp() {
        panel = injector.getInstance(ItemDropsPanel.class);
        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
        navButton = NavigationButton.builder()
                .tooltip("Item Drops")
                .icon(icon)
                .priority(7)
                .panel(panel)
                .build();
        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() {
        clientToolbar.removeNavigation(navButton);
    }
}
