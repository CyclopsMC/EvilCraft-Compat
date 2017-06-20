package org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeJEI;

import javax.annotation.Nonnull;

/**
 * Handler for the Envir Acc recipes.
 * @author rubensworks
 */
public class SanguinaryEnvironmentalAccumulatorRecipeHandler implements IRecipeHandler<SanguinaryEnvironmentalAccumulatorRecipeJEI> {

    public static final String CATEGORY = Reference.MOD_ID + ":sanguinaryEnvironmentalAccumulator";

    @Nonnull
    @Override
    public Class<SanguinaryEnvironmentalAccumulatorRecipeJEI> getRecipeClass() {
        return SanguinaryEnvironmentalAccumulatorRecipeJEI.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull SanguinaryEnvironmentalAccumulatorRecipeJEI recipe) {
        return CATEGORY;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull SanguinaryEnvironmentalAccumulatorRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull SanguinaryEnvironmentalAccumulatorRecipeJEI recipe) {
        return recipe != null;
    }

}
