package com.itemdrops;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ItemDropsPluginTest {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(ItemDropsPlugin.class);
        RuneLite.main(args);
    }
}