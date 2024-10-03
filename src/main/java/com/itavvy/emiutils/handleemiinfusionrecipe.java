package com.itavvy.emiutils;

import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class handleemiinfusionrecipe implements EmiRecipe {

    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public handleemiinfusionrecipe(IInfusionRecipe recipe){
        this.id = recipe.getId();

        List<EmiIngredient> templist = new java.util.ArrayList<>(List.of());
        NonNullList<Ingredient> tempinput = recipe.getIngredients();
        for(int i = 0; i < tempinput.size(); i++){
            Ingredient tempitem = tempinput.get(i);
            EmiIngredient tempingredient = EmiIngredient.of(tempitem);
            templist.add(tempingredient);
        }
        input = templist;
        this.output = List.of(EmiStack.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return handleemiplugin.INFUSION_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 144;
    }

    @Override
    public int getDisplayHeight() {
        return 81;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 90,33);

        //2,4,6,8
        //1,3,5,7

        widgetHolder.addSlot(input.get(2), 0, 32);
        widgetHolder.addSlot(input.get(4), 32, 0);
        widgetHolder.addSlot(input.get(6), 32, 63);
        widgetHolder.addSlot(input.get(8), 64, 32);

        widgetHolder.addSlot(input.get(1), 6, 6);
        widgetHolder.addSlot(input.get(3), 6, 58);
        widgetHolder.addSlot(input.get(5), 58, 6);
        widgetHolder.addSlot(input.get(7), 58, 58);

        widgetHolder.addSlot(input.get(0), 32, 32);

        widgetHolder.addSlot(output.get(0), 123, 32).recipeContext(this);
    }
}
