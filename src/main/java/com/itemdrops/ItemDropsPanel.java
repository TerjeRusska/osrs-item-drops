package com.itemdrops;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Slf4j
class ItemDropsPanel extends PluginPanel {

    private final JPanel display = new JPanel();

    private final MaterialTabGroup tabGroup = new MaterialTabGroup(display);
    private final MaterialTab searchTab;

    @Getter
    private final ItemDropsSearchPanel searchPanel;

    @Inject
    ItemDropsPanel(ItemDropsSearchPanel searchPanel) {
        super(false);
        this.searchPanel = searchPanel;

        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        searchTab = new MaterialTab("Search", tabGroup, searchPanel);
        tabGroup.setBorder(new EmptyBorder(5, 0, 0, 0));
        tabGroup.addTab(searchTab);
        tabGroup.select(searchTab);

        add(tabGroup, BorderLayout.NORTH);
        add(display, BorderLayout.CENTER);
    }
}
