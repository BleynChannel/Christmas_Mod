package com.civilization.christmas.modules.daily_gifts.core;

public class Gift extends ItemChance {
    public String id;
    public String name;
    public String nbt;
    public int amount;

    public Gift(String id, String name, ItemType type, float chance, String nbt, int amount) {
        super(type, chance);
        this.id = id;
        this.name = name;
        this.nbt = nbt;
        this.amount = amount;
    }
}