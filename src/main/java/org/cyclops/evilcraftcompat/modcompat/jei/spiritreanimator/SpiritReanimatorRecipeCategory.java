package org.cyclops.evilcraftcompat.modcompat.jei.spiritreanimator;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.block.BlockSpiritReanimatorConfig;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenBloodInfuser;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenSpiritReanimator;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.JEIEvilCraftConfig;

import javax.annotation.Nonnull;

/**
 * Category for the Spirit Reanimator recipes.
 * @author rubensworks
 */
public class SpiritReanimatorRecipeCategory implements IRecipeCategory<SpiritReanimatorRecipeJEI> {

    public static final RecipeType<SpiritReanimatorRecipeJEI> TYPE = RecipeType.create(Reference.MOD_ID, "spirit_reanimator", SpiritReanimatorRecipeJEI.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawable tankOverlay;

    public SpiritReanimatorRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "spirit_reanimator_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 130, 70);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RegistryEntries.BLOCK_BLOOD_INFUSER));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation,
                146, 0, ContainerScreenSpiritReanimator.PROGRESSWIDTH, ContainerScreenSpiritReanimator.PROGRESSHEIGHT);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.TOP, false);
        this.tankOverlay = guiHelper.createDrawable(resourceLocation, 130, 0, ContainerScreenBloodInfuser.TANKWIDTH, ContainerScreenBloodInfuser.TANKHEIGHT);
    }

    @Override
    public RecipeType<SpiritReanimatorRecipeJEI> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable(RegistryEntries.BLOCK_SPIRIT_REANIMATOR.getDescriptionId());
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

    protected int getMaxTankSize() {
        return BlockSpiritReanimatorConfig.mBPerTick * BlockSpiritReanimatorConfig.requiredTicks;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritReanimatorRecipeJEI recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 17)
                .addItemStack(recipe.getInputItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 88, 17)
                .addItemStack(new ItemStack(Items.EGG));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 6, 6)
                .setOverlay(tankOverlay, 0, 0)
                .setFluidRenderer(getMaxTankSize(), true, ContainerScreenSpiritReanimator.TANKWIDTH, ContainerScreenSpiritReanimator.TANKHEIGHT)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.getInputFluid());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 69, 47)
                .addItemStack(recipe.getOutputItem());
    }

    @Override
    public void draw(SpiritReanimatorRecipeJEI recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 72, 21);

        // Draw duration
        Font fontRenderer = Minecraft.getInstance().font;
        MutableComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.getDuration());
        fontRenderer.drawInBatch(duration,
                (background.getWidth() - fontRenderer.width(duration)) / 2 + 46, 50, 0xFF808080, false,
                guiGraphics.pose().last().pose(), guiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
    }
}
