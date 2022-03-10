package org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenBloodInfuser;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenSanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraft.blockentity.tickaction.sanguinaryenvironmentalaccumulator.AccumulateItemTickAction;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.JEIEvilCraftConfig;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.CommonEnvironmentalAccumulatorRecipeCategory;

import javax.annotation.Nonnull;

/**
 * Category for the Sanguinary Envir Acc recipes.
 * @author rubensworks
 */
public class SanguinaryEnvironmentalAccumulatorRecipeCategory extends CommonEnvironmentalAccumulatorRecipeCategory<SanguinaryEnvironmentalAccumulatorRecipeJEI> {

    public static final ResourceLocation NAME = new ResourceLocation(Reference.MOD_ID, "sanguinary_environmental_accumulator");

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FLUID_SLOT = 2;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawable tankOverlay;

    public SanguinaryEnvironmentalAccumulatorRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, Pair.of(42, 8), Pair.of(96, 8));
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "sanguinary_environmental_accumulator_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 130, 70);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(RegistryEntries.BLOCK_SANGUINARY_ENVIRONMENTAL_ACCUMULATOR));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation,
                146, 0, ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSWIDTH, ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSHEIGHT);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
        this.tankOverlay = guiHelper.createDrawable(resourceLocation, 130, 0, ContainerScreenBloodInfuser.TANKWIDTH, ContainerScreenBloodInfuser.TANKHEIGHT);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return NAME;
    }

    @Override
    public Class<? extends SanguinaryEnvironmentalAccumulatorRecipeJEI> getRecipeClass() {
        return SanguinaryEnvironmentalAccumulatorRecipeJEI.class;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return new TranslatableComponent(RegistryEntries.BLOCK_SANGUINARY_ENVIRONMENTAL_ACCUMULATOR.getDescriptionId());
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
    public void setIngredients(SanguinaryEnvironmentalAccumulatorRecipeJEI recipe, IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputItems());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutputItem());
    }

    @Override
    public void draw(SanguinaryEnvironmentalAccumulatorRecipeJEI recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        super.draw(recipe, matrixStack, mouseX, mouseY);
        arrow.draw(matrixStack, 65, 28);

        // Draw duration
        Font fontRenderer = Minecraft.getInstance().font;
        MutableComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.getDuration());
        fontRenderer.draw(matrixStack, duration,
                (background.getWidth() - fontRenderer.width(duration)) / 2 + 12, 50, 0xFF808080);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SanguinaryEnvironmentalAccumulatorRecipeJEI recipe, IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipe, ingredients);
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 41, 27);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 95, 27);

        recipeLayout.getItemStacks().set(INPUT_SLOT, recipe.getInputItems());
        recipeLayout.getItemStacks().set(OUTPUT_SLOT, recipe.getOutputItem());

        FluidStack fluidStack = new FluidStack(RegistryEntries.FLUID_BLOOD, AccumulateItemTickAction.getUsage(recipe.getCooldownTime()));
        recipeLayout.getFluidStacks().init(FLUID_SLOT, true, 6, 6,
                ContainerScreenBloodInfuser.TANKWIDTH, ContainerScreenBloodInfuser.TANKHEIGHT, fluidStack.getAmount(), true, tankOverlay);
        recipeLayout.getFluidStacks().set(FLUID_SLOT, fluidStack);
    }
}
