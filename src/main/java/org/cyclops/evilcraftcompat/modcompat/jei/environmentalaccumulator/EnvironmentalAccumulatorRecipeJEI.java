package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import com.google.common.collect.Lists;
import org.cyclops.evilcraft.core.recipe.type.RecipeEnvironmentalAccumulator;

import java.util.List;

/**
 * Recipe wrapper for Envir Acc recipes
 * @author rubensworks
 */
public class EnvironmentalAccumulatorRecipeJEI extends CommonEnvironmentalAccumulatorRecipeJEI<EnvironmentalAccumulatorRecipeJEI> {

    public EnvironmentalAccumulatorRecipeJEI(RecipeEnvironmentalAccumulator recipe) {
        super(recipe);
    }

    protected EnvironmentalAccumulatorRecipeJEI() {
        super();
    }

    @Override
    protected EnvironmentalAccumulatorRecipeJEI newInstance(RecipeEnvironmentalAccumulator recipe) {
        return new EnvironmentalAccumulatorRecipeJEI(recipe);
    }

    public static List<EnvironmentalAccumulatorRecipeJEI> getAllRecipes() {
        return Lists.newArrayList(new EnvironmentalAccumulatorRecipeJEI().createAllRecipes());
    }

}
