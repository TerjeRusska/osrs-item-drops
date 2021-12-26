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
import java.util.ArrayList;

@Slf4j
class ItemDropsPanel extends PluginPanel {

    private final JPanel display = new JPanel();

    @Getter
    private final MaterialTabGroup tabGroup = new MaterialTabGroup(display);

    @Getter
    private final MaterialTab searchTab;
    @Getter
    private final MaterialTab resultsTab;

    @Getter
    private final ItemDropsSearchPanel searchPanel;
    @Getter
    private final ItemDropsResultsPanel resultsPanel;

    @Inject
    ItemDropsPanel(ItemDropsSearchPanel searchPanel, ItemDropsResultsPanel resultsPanel) {
        super(false);
        this.searchPanel = searchPanel;
        this.resultsPanel = resultsPanel;

        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        searchTab = new MaterialTab("Item Search", tabGroup, searchPanel);
        resultsTab = new MaterialTab("Drop Sources", tabGroup, resultsPanel);
        tabGroup.setBorder(new EmptyBorder(5, 0, 0, 0));
        tabGroup.addTab(searchTab);
        tabGroup.addTab(resultsTab);
        tabGroup.select(searchTab);

        add(tabGroup, BorderLayout.NORTH);
        add(display, BorderLayout.CENTER);
    }

    void switchToResultsTab(String itemName, ArrayList<ItemDrop> itemDrops) {
        resultsPanel.populate(itemDrops);
        tabGroup.select(resultsTab);
    }
}
