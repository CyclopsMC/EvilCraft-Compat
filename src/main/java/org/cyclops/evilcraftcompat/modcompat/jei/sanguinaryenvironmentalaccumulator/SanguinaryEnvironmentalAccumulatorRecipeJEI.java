package org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator;

import lombok.EqualsAndHashCode;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cyclops.cyclopscore.recipe.custom.api.IRecipe;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.block.EnvironmentalAccumulator;
import org.cyclops.evilcraft.core.recipe.custom.EnvironmentalAccumulatorRecipeComponent;
import org.cyclops.evilcraft.core.recipe.custom.EnvironmentalAccumulatorRecipeProperties;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeJEIBase;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Recipe wrapper for Sanguinary Envir Acc recipes
 * @author rubensworks
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class SanguinaryEnvironmentalAccumulatorRecipeJEI extends EnvironmentalAccumulatorRecipeJEIBase<SanguinaryEnvironmentalAccumulatorRecipeJEI> {

    public static final String CATEGORY = Reference.MOD_ID + ":sanguinaryEnvironmentalAccumulator";

    public SanguinaryEnvironmentalAccumulatorRecipeJEI(IRecipe<EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeProperties> recipe) {
        super(recipe);
    }

    protected SanguinaryEnvironmentalAccumulatorRecipeJEI() {
        super();
    }

    @Override
    protected SanguinaryEnvironmentalAccumulatorRecipeJEI newInstance(IRecipe<EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeProperties> input) {
        return new SanguinaryEnvironmentalAccumulatorRecipeJEI(input);
    }

    public static List<SanguinaryEnvironmentalAccumulatorRecipeJEI> getAllSanguinaryRecipes() {
        return new SanguinaryEnvironmentalAccumulatorRecipeJEI().createAllRecipes();
    }
}
