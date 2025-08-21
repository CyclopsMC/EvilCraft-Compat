package org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenBloodInfuser;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenSpiritReanimator;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.JEIEvilCraftConfig;

import javax.annotation.Nonnull;

/**
 * Category for the Spirit Furnace recipes.
 * @author rubensworks
 */
public class SpiritFurnaceRecipeCategory implements IRecipeCategory<SpiritFurnaceRecipeJEI> {

    public static final IRecipeType<SpiritFurnaceRecipeJEI> TYPE = IRecipeType.create(Reference.MOD_ID, "spirit_furnace", SpiritFurnaceRecipeJEI.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawable tankOverlay;

    public SpiritFurnaceRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "spirit_furnace_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 130, 70);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RegistryEntries.BLOCK_SPIRIT_FURNACE.get()));
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
    public IRecipeType<SpiritFurnaceRecipeJEI> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable(RegistryEntries.BLOCK_SPIRIT_FURNACE.get().getDescriptionId());
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritFurnaceRecipeJEI recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 28)
                .add(recipe.getInputItem());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 6, 6)
                .setOverlay(tankOverlay, 0, 0)
                .setFluidRenderer(recipe.getInputFluid().getAmount(), true, ContainerScreenSpiritReanimator.TANKWIDTH, ContainerScreenSpiritReanimator.TANKHEIGHT)
                .add(NeoForgeTypes.FLUID_STACK, recipe.getInputFluid());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 28)
                .addItemStacks(recipe.getOutputItems());
    }

    @Override
    public void draw(SpiritFurnaceRecipeJEI recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        arrow.draw(guiGraphics, 65, 28);

        // Draw duration
        Font fontRenderer = Minecraft.getInstance().font;
        MutableComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.getDuration());
        guiGraphics.drawString(fontRenderer, duration, (background.getWidth() - fontRenderer.width(duration)) / 2 + 12, 50, ARGB.opaque(0xFF808080), false);
    }
}
