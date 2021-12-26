package com.itemdrops;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

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

    @Getter(AccessLevel.PACKAGE)
    private Map<Integer, Item> dropTablePerItem;

    @Override
    protected void startUp() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("drop_table_per_item.json");
        assert inputStream != null;
        Type mapType = new TypeToken<Map<Integer, Item>>() {}.getType();
        dropTablePerItem = new Gson().fromJson(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8), mapType);

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

    void lookupItemDropSources(int itemId) {
        if (dropTablePerItem.containsKey(itemId)) {
            String itemName = dropTablePerItem.get(itemId).getName();
            ArrayList<ItemDrop> itemDrops = dropTablePerItem.get(itemId).getDrops();
            panel.switchToResultsTab(itemName, itemDrops);
        }
        else {
            log.info("This item is not dropped by anyone :(");
        }
    }
}
