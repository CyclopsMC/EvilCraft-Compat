package org.cyclops.evilcraftcompat.modcompat.minetweaker.handlers;

import mezz.jei.api.recipe.IRecipeWrapper;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import org.cyclops.cyclopscore.modcompat.jei.IJeiRecipeWrapperWrapper;
import org.cyclops.cyclopscore.modcompat.minetweaker.handlers.RecipeRegistryHandler;
import org.cyclops.cyclopscore.recipe.custom.Recipe;
import org.cyclops.cyclopscore.recipe.custom.api.IRecipe;
import org.cyclops.cyclopscore.recipe.custom.component.IngredientRecipeComponent;
import org.cyclops.evilcraft.block.BloodInfuser;
import org.cyclops.evilcraft.core.recipe.custom.DurationXpRecipeProperties;
import org.cyclops.evilcraft.core.recipe.custom.IngredientFluidStackAndTierRecipeComponent;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.evilcraft.BloodInfuser")
public class BloodInfuserHandler extends RecipeRegistryHandler<BloodInfuser, IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> {

    private static final org.cyclops.evilcraftcompat.modcompat.minetweaker.handlers.BloodInfuserHandler INSTANCE = new org.cyclops.evilcraftcompat.modcompat.minetweaker.handlers.BloodInfuserHandler();

    @Override
    protected BloodInfuser getMachine() {
        return BloodInfuser.getInstance();
    }

    @Override
    protected String getRegistryName() {
        return "BloodInfuser";
    }

    @Override
    protected IJeiRecipeWrapperWrapper<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> createJeiWrapperWrapper() {
        return new IJeiRecipeWrapperWrapper<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties>() {
            @Override
            public IRecipeWrapper wrap(IRecipe<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> recipe) {
                return new BloodInfuserRecipeJEI(recipe);
            }
        };
    }

    @ZenMethod
    public static void addRecipe(IItemStack inputStack, ILiquidStack inputFluid, int tier,
                                 IItemStack outputStack, int duration, int xp) {
        INSTANCE.add(new Recipe<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties>(
                new IngredientFluidStackAndTierRecipeComponent(RecipeRegistryHandler.toStack(inputStack), RecipeRegistryHandler.toFluid(inputFluid), tier),
                new IngredientRecipeComponent(RecipeRegistryHandler.toStack(outputStack)),
                new DurationXpRecipeProperties(duration, xp)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack inputStack, ILiquidStack inputFluid, int tier,
                                    IItemStack outputStack, int duration, int xp) {
        INSTANCE.remove(new Recipe<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties>(
                new IngredientFluidStackAndTierRecipeComponent(RecipeRegistryHandler.toStack(inputStack), RecipeRegistryHandler.toFluid(inputFluid), tier),
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
