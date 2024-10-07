package com.itavvy.emiutils;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;

@EmiEntrypoint
public class handleemiplugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiutils.getlist();

        //HIDE RECIPES
        Iterator<ItemStack> itemlist = emiutils.bannedlist.iterator();
        while(itemlist.hasNext()){
            ItemStack stack = itemlist.next();
            EmiStack emistack = EmiStack.of(stack);
            if(stack.hasTag()){
                emiRegistry.setDefaultComparison(emistack, Comparison.compareNbt());
            }
            emiRegistry.removeEmiStacks(emistack);
        }
    }
}
