package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.JEIEvilCraftConfig;

import javax.annotation.Nonnull;

/**
 * Category for the Envir Acc recipes.
 * @author rubensworks
 */
public class EnvironmentalAccumulatorRecipeCategory extends CommonEnvironmentalAccumulatorRecipeCategory<EnvironmentalAccumulatorRecipeJEI> {

    public static final ResourceLocation NAME = new ResourceLocation(Reference.MOD_ID, "environmental_accumulator");

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public EnvironmentalAccumulatorRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, Pair.of(2, 8), Pair.of(76, 8));
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "environmental_accumulator_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 94, 54);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation, 94, 0, 5, 34);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return NAME;
    }

    @Override
    public Class<? extends EnvironmentalAccumulatorRecipeJEI> getRecipeClass() {
        return EnvironmentalAccumulatorRecipeJEI.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return new TranslationTextComponent(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR.getDescriptionId()).getString();
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(EnvironmentalAccumulatorRecipeJEI recipe, IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputItems());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutputItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EnvironmentalAccumulatorRecipeJEI recipe, IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipe, ingredients);
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 1, 27);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 75, 27);

        recipeLayout.getItemStacks().set(INPUT_SLOT, recipe.getInputItems());
        recipeLayout.getItemStacks().set(OUTPUT_SLOT, recipe.getOutputItem());
    }

    @Override
    public void draw(EnvironmentalAccumulatorRecipeJEI recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        super.draw(recipe, matrixStack, mouseX, mouseY);
        arrow.draw(matrixStack, 44, 0);

        // Draw duration
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        IFormattableTextComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.getDuration());
        fontRenderer.draw(matrixStack, duration,
                (background.getWidth() - fontRenderer.width(duration)), 48, 0xFF808080);
    }
}
