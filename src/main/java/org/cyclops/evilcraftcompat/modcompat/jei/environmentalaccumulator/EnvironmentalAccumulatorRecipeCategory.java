package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.cyclopscore.helper.L10NHelpers;
import org.cyclops.evilcraft.block.EnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.CommonEnvironmentalAccumulatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeHandler;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeJEIBase;

import javax.annotation.Nonnull;

/**
 * Category for the Envir Acc recipes.
 * @author rubensworks
 */
public class EnvironmentalAccumulatorRecipeCategory extends CommonEnvironmentalAccumulatorRecipeCategory {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private final IDrawable background;
    private final IDrawableAnimated arrow;

    public EnvironmentalAccumulatorRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, Pair.of(2, 8), Pair.of(76, 8));
        ResourceLocation resourceLocation = new ResourceLocation(org.cyclops.evilcraft.Reference.MOD_ID + ":" + EnvironmentalAccumulator.getInstance().getGuiTexture("_jei"));
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 94, 54);
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation, 94, 0, 5, 34);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Nonnull
    @Override
    public String getUid() {
        return EnvironmentalAccumulatorRecipeJEI.CATEGORY;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return L10NHelpers.localize(EnvironmentalAccumulator.getInstance().getUnlocalizedName() + ".name");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        arrow.draw(minecraft, 44, 0);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipeWrapper, ingredients);
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 1, 27);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 75, 27);

        if(recipeWrapper instanceof EnvironmentalAccumulatorRecipeJEIBase) {
            EnvironmentalAccumulatorRecipeJEIBase recipe = (EnvironmentalAccumulatorRecipeJEIBase) recipeWrapper;
            recipeLayout.getItemStacks().set(INPUT_SLOT, recipe.getInput());
            recipeLayout.getItemStacks().set(OUTPUT_SLOT, recipe.getOutput());
        }
    }
}
