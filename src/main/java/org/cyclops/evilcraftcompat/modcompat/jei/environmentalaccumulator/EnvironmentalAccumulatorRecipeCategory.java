package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.cyclopscore.recipe.ItemStackFromIngredient;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.core.recipe.type.RecipeEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.JEIEvilCraftConfig;

import javax.annotation.Nonnull;

/**
 * Category for the Envir Acc recipes.
 * @author rubensworks
 */
public class EnvironmentalAccumulatorRecipeCategory extends CommonEnvironmentalAccumulatorRecipeCategory {

    public static final IRecipeHolderType<RecipeEnvironmentalAccumulator> TYPE = IRecipeHolderType.create(RegistryEntries.RECIPETYPE_ENVIRONMENTAL_ACCUMULATOR.get());

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public EnvironmentalAccumulatorRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, Pair.of(2, 8), Pair.of(76, 8));
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "environmental_accumulator_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 94, 54);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR.get()));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation, 94, 0, 5, 34);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public int getWidth() {
        return this.background.getWidth();
    }

    @Override
    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public IRecipeType<RecipeHolder<RecipeEnvironmentalAccumulator>> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR.get().getDescriptionId());
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<RecipeEnvironmentalAccumulator> recipeHolder, IFocusGroup focuses) {
        RecipeEnvironmentalAccumulator recipe = recipeHolder.value();

        builder.addSlot(RecipeIngredientRole.INPUT, 2, 28)
                .add(recipe.getInputIngredient());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 28)
                .add(recipe.getOutputItem().map(l -> Ingredient.of(l.getItem()), ItemStackFromIngredient::getIngredient));
    }

    @Override
    public void draw(RecipeHolder<RecipeEnvironmentalAccumulator> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.background.draw(guiGraphics);
        arrow.draw(guiGraphics, 44, 0);

        // Draw duration
        Font fontRenderer = Minecraft.getInstance().font;
        MutableComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.value().getDuration());
        guiGraphics.drawString(fontRenderer, duration, (background.getWidth() - fontRenderer.width(duration)) / 2, 48, ARGB.opaque(0xFF808080), false);
    }
}
