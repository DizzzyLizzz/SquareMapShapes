package com.dizzzylizzz.sms;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    public static final ModConfigSpec.IntValue radius1 = BUILDER
            .comment("radius of safe zone marker")
            .defineInRange("radius1", 500, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<List<Integer>> shape1Center = BUILDER
            .comment("where would you like the center of your safe zone marker to be? (X,Z 0,0 is default)")
            .define("safeZoneCenter", List.of(0, 0));

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
