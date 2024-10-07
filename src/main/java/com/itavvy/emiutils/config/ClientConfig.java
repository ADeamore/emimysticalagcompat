package com.itavvy.emiutils.config;

import com.itavvy.emiutils.emiutils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<String>> MASTER_LIST;
    public static final ForgeConfigSpec.ConfigValue<List<String>> COMPOUND_TAGS;

    static{
        BUILDER.push("EMIUtils Removed Items List");

        MASTER_LIST = BUILDER.comment("Begin banned item list").define("BannedList",new ArrayList<String>());
        COMPOUND_TAGS = BUILDER.comment("Begin banned tag list").define("BannedTags",new ArrayList<String>());

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
