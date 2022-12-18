package com.civilization.christmas.modules.daily_gifts.core;

import java.util.ArrayList;

public class Bundle extends ItemChance {
    public String name;
    public ArrayList<Gift> gifts;
    public String color;

    public Bundle(String name, ItemType type, float chance, ArrayList<Gift> gifts, String color) {
        super(type, chance);
        this.name = name;
        this.gifts = gifts;
        this.color = color;
    }
}
