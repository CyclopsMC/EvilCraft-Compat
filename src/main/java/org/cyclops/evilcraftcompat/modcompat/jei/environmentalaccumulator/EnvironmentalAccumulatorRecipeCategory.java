package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
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

    public static final RecipeType<EnvironmentalAccumulatorRecipeJEI> TYPE = RecipeType.create(Reference.MOD_ID, "environmental_accumulator", EnvironmentalAccumulatorRecipeJEI.class);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public EnvironmentalAccumulatorRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, Pair.of(2, 8), Pair.of(76, 8));
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "environmental_accumulator_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 94, 54);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation, 94, 0, 5, 34);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return TYPE.getUid();
    }

    @Override
    public Class<? extends EnvironmentalAccumulatorRecipeJEI> getRecipeClass() {
        return TYPE.getRecipeClass();
    }

    @Override
    public RecipeType<EnvironmentalAccumulatorRecipeJEI> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return new TranslatableComponent(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR.getDescriptionId());
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
    public void setRecipe(IRecipeLayoutBuilder builder, EnvironmentalAccumulatorRecipeJEI recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 28)
                .addItemStacks(recipe.getInputItems());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 28)
                .addItemStack(recipe.getOutputItem());
    }

    @Override
    public void draw(EnvironmentalAccumulatorRecipeJEI recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, matrixStack, mouseX, mouseY);
        arrow.draw(matrixStack, 44, 0);

        // Draw duration
        Font fontRenderer = Minecraft.getInstance().font;
        MutableComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.getDuration());
        fontRenderer.draw(matrixStack, duration,
                (background.getWidth() - fontRenderer.width(duration)), 48, 0xFF808080);
    }
}
