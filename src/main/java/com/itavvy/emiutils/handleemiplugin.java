package com.itavvy.emiutils;

import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.blakebr0.mysticalagriculture.init.ModBlocks;
import com.blakebr0.mysticalagriculture.init.ModRecipeTypes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Iterator;
import java.util.List;

@EmiEntrypoint
public class handleemiplugin implements EmiPlugin {

    //INFUSION STUFF
    public static final ResourceLocation INFUSION_SPRITE = new ResourceLocation("emimysticalag","textures/jei/infusion.png");
    public static final EmiStack INFUSION_WORKSTATION = EmiStack.of(ModBlocks.INFUSION_ALTAR.get().asItem().getDefaultInstance());
    public static final EmiRecipeCategory INFUSION_CATEGORY =
            new EmiRecipeCategory(
                new ResourceLocation("mysticalagriculture", "infusion"),
                INFUSION_WORKSTATION,
                new EmiTexture(INFUSION_SPRITE, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiutils.getlist();

        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        //HIDE RECIPES
        Iterator<ItemStack> itemlist = emiutils.bannedlist.iterator();
        while(itemlist.hasNext()){
            emiRegistry.removeEmiStacks(EmiStack.of(itemlist.next()));
        }

        //INFUSION STUFF
        emiRegistry.addCategory(INFUSION_CATEGORY);
        emiRegistry.addWorkstation(INFUSION_CATEGORY,INFUSION_WORKSTATION);

        List<IInfusionRecipe> recipelist = manager.getAllRecipesFor(ModRecipeTypes.INFUSION.get());
        Iterator<IInfusionRecipe> temp = recipelist.iterator();

        while(temp.hasNext()){
            IInfusionRecipe temprecipe = temp.next();
            emiRegistry.addRecipe(new handleemiinfusionrecipe(temprecipe));
        }

    }
}
