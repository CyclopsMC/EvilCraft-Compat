package org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenBloodInfuser;
import org.cyclops.evilcraft.core.tileentity.TileWorking;
import org.cyclops.evilcraft.item.ItemPromise;
import org.cyclops.evilcraft.tileentity.TileBloodInfuser;
import org.cyclops.evilcraftcompat.Reference;

import javax.annotation.Nonnull;

/**
 * Category for the Blood Infuser recipes.
 * @author rubensworks
 */
public class BloodInfuserRecipeCategory implements IRecipeCategory<BloodInfuserRecipeJEI> {

    public static final ResourceLocation NAME = new ResourceLocation(Reference.MOD_ID, "blood_infuser");

    private static final int INPUT_SLOT = 0;
    private static final int UPGRADE_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int FLUID_SLOT = 3;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawable tankOverlay;

    public BloodInfuserRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "blood_infuser_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 130, 70);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(RegistryEntries.BLOCK_BLOOD_INFUSER));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation,
                146, 0, ContainerScreenBloodInfuser.PROGRESSWIDTH, ContainerScreenBloodInfuser.PROGRESSHEIGHT);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
        this.tankOverlay = guiHelper.createDrawable(resourceLocation, 130, 0, ContainerScreenBloodInfuser.TANKWIDTH, ContainerScreenBloodInfuser.TANKHEIGHT);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return NAME;
    }

    @Override
    public Class<? extends BloodInfuserRecipeJEI> getRecipeClass() {
        return BloodInfuserRecipeJEI.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return new TranslationTextComponent(RegistryEntries.BLOCK_BLOOD_INFUSER.getTranslationKey()).getString();
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

    protected int getMaxTankSize(org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI bloodInfuserRecipe) {
        return TileBloodInfuser.LIQUID_PER_SLOT * TileWorking.getTankTierMultiplier(bloodInfuserRecipe.getInputTier());
    }

    protected ItemStack getPromise(org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI bloodInfuserRecipe) {
        if(bloodInfuserRecipe.getInputTier() == 0) {
            return null;
        }
        return new ItemStack(ItemPromise.getItem(bloodInfuserRecipe.getInputTier()));
    }

    @Override
    public void setIngredients(BloodInfuserRecipeJEI recipe, IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputItems());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutputItem());
        ingredients.setInput(VanillaTypes.FLUID, recipe.getInputFluid());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BloodInfuserRecipeJEI recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 41, 27);
        recipeLayout.getItemStacks().init(UPGRADE_SLOT, false, 41, 7);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 95, 27);

        recipeLayout.getItemStacks().set(INPUT_SLOT, recipe.getInputItems());
        recipeLayout.getItemStacks().set(UPGRADE_SLOT, getPromise(recipe));
        recipeLayout.getItemStacks().set(OUTPUT_SLOT, recipe.getOutputItem());

        recipeLayout.getFluidStacks().init(FLUID_SLOT, true, 6, 6,
                ContainerScreenBloodInfuser.TANKWIDTH, ContainerScreenBloodInfuser.TANKHEIGHT, getMaxTankSize(recipe), true, tankOverlay);
        recipeLayout.getFluidStacks().set(FLUID_SLOT, recipe.getInputFluid());
    }

    @Override
    public void draw(BloodInfuserRecipeJEI recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 65, 28);
    }
}
