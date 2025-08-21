package org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
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
import org.cyclops.cyclopscore.recipe.ItemStackFromIngredient;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.blockentity.BlockEntityBloodInfuser;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenBloodInfuser;
import org.cyclops.evilcraft.core.blockentity.BlockEntityWorking;
import org.cyclops.evilcraft.core.recipe.type.RecipeBloodInfuser;
import org.cyclops.evilcraft.item.ItemPromise;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.JEIEvilCraftConfig;

import javax.annotation.Nonnull;

/**
 * Category for the Blood Infuser recipes.
 * @author rubensworks
 */
public class BloodInfuserRecipeCategory implements IRecipeCategory<RecipeHolder<RecipeBloodInfuser>> {

    public static final IRecipeHolderType<RecipeBloodInfuser> TYPE = IRecipeHolderType.create(RegistryEntries.RECIPETYPE_BLOOD_INFUSER.get());

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawable tankOverlay;

    public BloodInfuserRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "blood_infuser_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 130, 70);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RegistryEntries.BLOCK_BLOOD_INFUSER.get()));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation,
                146, 0, ContainerScreenBloodInfuser.PROGRESSWIDTH, ContainerScreenBloodInfuser.PROGRESSHEIGHT);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
        this.tankOverlay = guiHelper.createDrawable(resourceLocation, 130, 0, ContainerScreenBloodInfuser.TANKWIDTH, ContainerScreenBloodInfuser.TANKHEIGHT);
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
    public IRecipeType<RecipeHolder<RecipeBloodInfuser>> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable(RegistryEntries.BLOCK_BLOOD_INFUSER.get().getDescriptionId());
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    protected int getMaxTankSize(RecipeBloodInfuser bloodInfuserRecipe) {
        return BlockEntityBloodInfuser.LIQUID_PER_SLOT * BlockEntityWorking.getTankTierMultiplier(bloodInfuserRecipe.getInputTier().orElse(0));
    }

    protected ItemStack getPromise(RecipeBloodInfuser bloodInfuserRecipe) {
        if (bloodInfuserRecipe.getInputTier().orElse(0) == 0) {
            return null;
        }
        return new ItemStack(ItemPromise.getItem(bloodInfuserRecipe.getInputTier().orElseThrow()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<RecipeBloodInfuser> recipeHolder, IFocusGroup focuses) {
        RecipeBloodInfuser recipe = recipeHolder.value();

        IRecipeSlotBuilder inputSlot = builder.addSlot(RecipeIngredientRole.INPUT, 42, 28);
        recipe.getInputIngredient().ifPresent(inputSlot::add);
        IRecipeSlotBuilder inputSlotFluid = builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 6, 6)
                .setOverlay(tankOverlay, 0, 0)
                .setFluidRenderer(getMaxTankSize(recipe), true, ContainerScreenBloodInfuser.TANKWIDTH, ContainerScreenBloodInfuser.TANKHEIGHT);
        recipe.getInputFluid().ifPresent(fluidStack -> inputSlotFluid.add(NeoForgeTypes.FLUID_STACK, fluidStack));

        ItemStack promise = getPromise(recipe);
        if (promise != null) {
            builder.addSlot(RecipeIngredientRole.CRAFTING_STATION, 42, 8)
                    .add(promise);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 28)
                .add(recipe.getOutputItem().map(l -> Ingredient.of(l.getItem()), ItemStackFromIngredient::getIngredient));
    }

    @Override
    public void draw(RecipeHolder<RecipeBloodInfuser> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        arrow.draw(guiGraphics, 65, 28);

        // Draw duration
        Font fontRenderer = Minecraft.getInstance().font;
        MutableComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.value().getDuration());
        guiGraphics.drawString(fontRenderer, duration, (background.getWidth() - fontRenderer.width(duration)) / 2 + 12, 50, ARGB.opaque(0xFF808080), false);
    }
}
