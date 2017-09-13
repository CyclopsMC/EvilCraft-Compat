package org.cyclops.evilcraftcompat.modcompat.crafttweaker.handlers;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import org.cyclops.cyclopscore.modcompat.crafttweaker.handlers.RecipeRegistryHandler;
import org.cyclops.cyclopscore.recipe.custom.Recipe;
import org.cyclops.cyclopscore.recipe.custom.component.IngredientRecipeComponent;
import org.cyclops.evilcraft.block.BloodInfuser;
import org.cyclops.evilcraft.core.recipe.custom.DurationXpRecipeProperties;
import org.cyclops.evilcraft.core.recipe.custom.IngredientFluidStackAndTierRecipeComponent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.evilcraft.BloodInfuser")
@ZenRegister
public class BloodInfuserHandler extends RecipeRegistryHandler<BloodInfuser, IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> {

    private static final BloodInfuserHandler INSTANCE = new BloodInfuserHandler();

    @Override
    protected BloodInfuser getMachine() {
        return BloodInfuser.getInstance();
    }

    @Override
    protected String getRegistryName() {
        return "BloodInfuser";
    }

    @ZenMethod
    public static void addRecipe(IIngredient inputIngredient, ILiquidStack inputFluid, int tier,
                                 IItemStack outputStack, int duration, int xp) {
        INSTANCE.add(new Recipe<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties>(
                new IngredientFluidStackAndTierRecipeComponent(RecipeRegistryHandler.toIngredient(inputIngredient), RecipeRegistryHandler.toFluid(inputFluid), tier),
                new IngredientRecipeComponent(RecipeRegistryHandler.toStack(outputStack)),
                new DurationXpRecipeProperties(duration, xp)));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient inputIngredient, ILiquidStack inputFluid, int tier,
                                    IItemStack outputStack, int duration, int xp) {
        INSTANCE.remove(new Recipe<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties>(
                new IngredientFluidStackAndTierRecipeComponent(RecipeRegistryHandler.toIngredient(inputIngredient), RecipeRegistryHandler.toFluid(inputFluid), tier),
                new IngredientRecipeComponent(RecipeRegistryHandler.toStack(outputStack)),
                new DurationXpRecipeProperties(duration, xp)));
    }

    @ZenMethod
    public static void removeRecipesWithOutput(IItemStack outputStack) {
        INSTANCE.remove(
                new IngredientRecipeComponent(RecipeRegistryHandler.toStack(outputStack))
        );
    }
}
