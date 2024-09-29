package com.itavvy.emimysticalag;

import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class handleemiinfusionrecipe implements EmiRecipe {

    private ResourceLocation id;
    private List<EmiIngredient> input;
    private List<EmiStack> output;


    public handleemiinfusionrecipe(IInfusionRecipe recipe){
        this.id = recipe.getId();
        this.input = List.of();
        for(var i = 0; i < 9; i++){
            this.input.add(EmiIngredient.of(recipe.getIngredients().get(i)));
        }
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

        widgetHolder.addSlot(input.get(0), 0, 32);
        widgetHolder.addSlot(input.get(1), 6, 6);
        widgetHolder.addSlot(input.get(2), 6, 58);
        widgetHolder.addSlot(input.get(3), 32, 0);
        widgetHolder.addSlot(input.get(4), 32, 63);
        widgetHolder.addSlot(input.get(5), 58, 6);
        widgetHolder.addSlot(input.get(6), 58, 58);
        widgetHolder.addSlot(input.get(7), 64, 32);
        widgetHolder.addSlot(input.get(8), 32, 32);

        widgetHolder.addSlot(output.get(0), 123, 32).recipeContext(this);
    }
}
