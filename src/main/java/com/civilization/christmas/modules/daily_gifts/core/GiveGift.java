package com.civilization.christmas.modules.daily_gifts.core;

import com.civilization.christmas.modules.daily_gifts.DailyGifts;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class GiveGift {
//    private static class GiftItems {
//        public ArrayList<Gift> items;
//        private final float chance;
//        public float newChance;
//
//        public GiftItems(ArrayList<Gift> items, float chance) {
//            this.items = items;
//            this.chance = chance;
//        }
//    }

//    private static final GiftItems commonItems = new GiftItems(new ArrayList<Gift>() {{
//        add(new Gift("minecraft:coal", "Уголь", "", 16));
//        add(new Gift("minecraft:snowball", "Снежок", "", 64));
//        add(new Gift("minecraft:iron_ingot", "Железный слиток", "", 8));
//        add(new Gift("minecraft:gunpowder", "Порох", "", 4));
//    }}, 5.f);
//
//    private static final GiftItems rareItems = new GiftItems(new ArrayList<Gift>() {{
//        add(new Gift("minecraft:diamond", "Алмаз", "", 4));
//        add(new Gift("minecraft:emerald", "Изумруд", "", 8));
//        add(new Gift("minecraft:netherite_ingot", "Незеритовый слиток", "", 1));
//        add(new Gift("minecraft:diamond_axe", "Топор бога", "{display:{Name:'[{\"text\":\"Топор бога\",\"italic\":false,\"bold\":true}]',Lore:['[{\"text\":\"Только великий войн может совладать с такой мощью!\"}]']},Enchantments:[{id:efficiency,lvl:5},{id:fire_aspect,lvl:2},{id:sharpness,lvl:10}]}", 1));
//    }}, 2.f);

//    private static final GiftItems[] allItems = {
//        commonItems,
//        rareItems,
//    };

    private static float[] calculateNewChance(ArrayList<ItemChance> items, float playerChance) {
        float[] newChances = new float[items.size()];

        for (int i = 0; i < items.size(); i++) {
            ItemChance item = items.get(i);
            if (item.type == ItemChance.ItemType.COMMON) {
                newChances[i] = item.chance / playerChance;
            } else {
                newChances[i] = item.chance * playerChance;
            }
        }

        return newChances;
    }

    private static float getSumChance(float[] itemChances) {
        float sum = 0.f;
        for(var item : itemChances) {
            sum += item;
        }
        return sum;
    }

    @Nullable
    private static ItemChance getRandomItem(ArrayList<ItemChance> itemChances, float playerChance) {
        float[] newItemChances = calculateNewChance(itemChances, playerChance);

        float rand = new Random().nextFloat() * getSumChance(newItemChances);
        float stepChance = 0.f;

        for(int i = 0; i < newItemChances.length; i++) {
            if (rand > stepChance && rand < stepChance + newItemChances[i]) {
                return itemChances.get(i);
            }
            stepChance += newItemChances[i];
        }

        return null;
    }

    @Nullable
    public static Map.Entry<Bundle, Gift> getGift(float playerChance) {
        Bundle randomBundle = (Bundle) getRandomItem(new ArrayList<ItemChance>(DailyGifts.INSTANCE.bundles), playerChance);

        if (randomBundle != null) {
            return Map.entry(randomBundle, (Gift) Objects.requireNonNull(
                    getRandomItem(new ArrayList<ItemChance>(randomBundle.gifts), playerChance)));
        }

        return null;
    }
}
