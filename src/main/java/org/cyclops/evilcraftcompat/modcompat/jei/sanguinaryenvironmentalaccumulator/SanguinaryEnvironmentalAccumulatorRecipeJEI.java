package org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import net.minecraft.world.item.crafting.Recipe;
import org.cyclops.cyclopscore.helper.CraftingHelpers;
import org.cyclops.evilcraft.core.recipe.type.RecipeEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.CommonEnvironmentalAccumulatorRecipeJEI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Recipe wrapper for Sanguinary Envir Acc recipes
 * @author rubensworks
 */
public class SanguinaryEnvironmentalAccumulatorRecipeJEI extends CommonEnvironmentalAccumulatorRecipeJEI<SanguinaryEnvironmentalAccumulatorRecipeJEI> {

    private static final Map<Recipe<?>, SanguinaryEnvironmentalAccumulatorRecipeJEI> RECIPE_WRAPPERS_2 = Maps.newIdentityHashMap();

    public SanguinaryEnvironmentalAccumulatorRecipeJEI(RecipeEnvironmentalAccumulator recipe) {
        super(recipe);
    }

    protected SanguinaryEnvironmentalAccumulatorRecipeJEI() {
        super();
    }

    @Override
    protected SanguinaryEnvironmentalAccumulatorRecipeJEI newInstance(RecipeEnvironmentalAccumulator input) {
        return new SanguinaryEnvironmentalAccumulatorRecipeJEI(input);
    }

    public static List<SanguinaryEnvironmentalAccumulatorRecipeJEI> getAllRecipes() {
        Collection<SanguinaryEnvironmentalAccumulatorRecipeJEI> allRecipes = new SanguinaryEnvironmentalAccumulatorRecipeJEI().createAllRecipes();
        return new ArrayList<>(allRecipes);
    }

    // Needed because the RECIPE_WRAPPERS would otherwise just return the envir acc instances, and fail
    public Collection<SanguinaryEnvironmentalAccumulatorRecipeJEI> createAllRecipes() {
        return Collections2.transform(CraftingHelpers.getClientRecipes(this.getRecipeType()), new Function<RecipeEnvironmentalAccumulator, SanguinaryEnvironmentalAccumulatorRecipeJEI>() {
            @Nullable
            public SanguinaryEnvironmentalAccumulatorRecipeJEI apply(RecipeEnvironmentalAccumulator input) {
                if (!RECIPE_WRAPPERS_2.containsKey(input)) {
                    RECIPE_WRAPPERS_2.put(input, SanguinaryEnvironmentalAccumulatorRecipeJEI.this.newInstance(input));
                }

                return RECIPE_WRAPPERS_2.get(input);
            }
        });
    }
}
