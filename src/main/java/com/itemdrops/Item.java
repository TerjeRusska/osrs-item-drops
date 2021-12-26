package com.itemdrops;

import lombok.Value;
import net.runelite.client.util.AsyncBufferedImage;

import java.util.ArrayList;

@Value
class Item {
    private final AsyncBufferedImage icon;
    private final String name;
    private final int itemId;
    private final int haPrice;
    private ArrayList<ItemDrop> drops;
}
