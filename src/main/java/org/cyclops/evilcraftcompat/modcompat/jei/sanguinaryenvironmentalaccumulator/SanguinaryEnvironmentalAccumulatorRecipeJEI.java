package org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.cyclops.cyclopscore.helper.CraftingHelpers;
import org.cyclops.evilcraft.core.recipe.type.RecipeEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.CommonEnvironmentalAccumulatorRecipeJEI;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Recipe wrapper for Sanguinary Envir Acc recipes
 * @author rubensworks
 */
public class SanguinaryEnvironmentalAccumulatorRecipeJEI extends CommonEnvironmentalAccumulatorRecipeJEI<SanguinaryEnvironmentalAccumulatorRecipeJEI> {

    private static final Map<ResourceLocation, SanguinaryEnvironmentalAccumulatorRecipeJEI> RECIPE_WRAPPERS_2 = Maps.newIdentityHashMap();

    public SanguinaryEnvironmentalAccumulatorRecipeJEI(RecipeEnvironmentalAccumulator recipe) {
        super(recipe);
    }

    protected SanguinaryEnvironmentalAccumulatorRecipeJEI() {
        super();
    }

    @Override
    protected SanguinaryEnvironmentalAccumulatorRecipeJEI newInstance(RecipeHolder<RecipeEnvironmentalAccumulator> input) {
        return new SanguinaryEnvironmentalAccumulatorRecipeJEI(input.value());
    }

    public static List<SanguinaryEnvironmentalAccumulatorRecipeJEI> getAllRecipes() {
        return new SanguinaryEnvironmentalAccumulatorRecipeJEI().createAllRecipes();
    }

    // Needed because the RECIPE_WRAPPERS would otherwise just return the envir acc instances, and fail
    public List<SanguinaryEnvironmentalAccumulatorRecipeJEI> createAllRecipes() {
        return Lists.newArrayList(Collections2.transform(CraftingHelpers.getClientRecipes(this.getRecipeType()), new Function<RecipeHolder<RecipeEnvironmentalAccumulator>, SanguinaryEnvironmentalAccumulatorRecipeJEI>() {
            @Nullable
            public SanguinaryEnvironmentalAccumulatorRecipeJEI apply(RecipeHolder<RecipeEnvironmentalAccumulator> input) {
                if (!RECIPE_WRAPPERS_2.containsKey(input.id())) {
                    RECIPE_WRAPPERS_2.put(input.id(), SanguinaryEnvironmentalAccumulatorRecipeJEI.this.newInstance(input));
                }

                return RECIPE_WRAPPERS_2.get(input.id());
            }
        }).iterator());
    }
}
