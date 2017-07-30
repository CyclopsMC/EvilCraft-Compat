package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.Data;
import org.cyclops.cyclopscore.recipe.custom.api.IRecipe;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.block.EnvironmentalAccumulator;
import org.cyclops.evilcraft.core.recipe.custom.EnvironmentalAccumulatorRecipeComponent;
import org.cyclops.evilcraft.core.recipe.custom.EnvironmentalAccumulatorRecipeProperties;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeJEIBase;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Recipe wrapper for Envir Acc recipes
 * @author rubensworks
 */
@Data
public class EnvironmentalAccumulatorRecipeJEI extends EnvironmentalAccumulatorRecipeJEIBase<EnvironmentalAccumulatorRecipeJEI> {

    public static final String CATEGORY = Reference.MOD_ID + ":environmentalAccumulator";

    public EnvironmentalAccumulatorRecipeJEI(IRecipe<EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeProperties> recipe) {
        super(recipe);
    }

    protected EnvironmentalAccumulatorRecipeJEI() {
        super();
    }

    @Override
    protected EnvironmentalAccumulatorRecipeJEI newInstance(IRecipe<EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeProperties> input) {
        return new EnvironmentalAccumulatorRecipeJEI(input);
    }

    public static List<EnvironmentalAccumulatorRecipeJEI> getAllRecipes() {
        return new EnvironmentalAccumulatorRecipeJEI().createAllRecipes();
    }
}
