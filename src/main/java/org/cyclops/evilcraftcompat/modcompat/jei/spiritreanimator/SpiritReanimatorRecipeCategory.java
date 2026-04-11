package org.cyclops.evilcraftcompat.modcompat.jei.spiritreanimator;

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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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

    public static final IRecipeType<SpiritReanimatorRecipeJEI> TYPE = IRecipeType.create(Reference.MOD_ID, "spirit_reanimator", SpiritReanimatorRecipeJEI.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawable tankOverlay;

    public SpiritReanimatorRecipeCategory(IGuiHelper guiHelper) {
        Identifier resourceLocation = Identifier.fromNamespaceAndPath(Reference.MOD_ID, Reference.TEXTURE_PATH_GUI + "spirit_reanimator_gui_jei.png");
        this.background = guiHelper.createDrawable(resourceLocation, 0, 0, 130, 70);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RegistryEntries.BLOCK_SPIRIT_REANIMATOR.get()));
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(resourceLocation,
                146, 0, ContainerScreenSpiritReanimator.PROGRESSWIDTH, ContainerScreenSpiritReanimator.PROGRESSHEIGHT);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.TOP, false);
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
    public IRecipeType<SpiritReanimatorRecipeJEI> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable(RegistryEntries.BLOCK_SPIRIT_REANIMATOR.get().getDescriptionId());
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
                .add(recipe.getInputItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 88, 17)
                .add(Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ItemTags.EGGS)));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 6, 6)
                .setOverlay(tankOverlay, 0, 0)
                .setFluidRenderer(getMaxTankSize(), true, ContainerScreenSpiritReanimator.TANKWIDTH, ContainerScreenSpiritReanimator.TANKHEIGHT)
                .add(NeoForgeTypes.FLUID_STACK, recipe.getInputFluid());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 69, 47)
                .add(recipe.getOutputItem());
    }

    @Override
    public void draw(SpiritReanimatorRecipeJEI recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        arrow.draw(guiGraphics, 72, 21);

        // Draw duration
        Font fontRenderer = Minecraft.getInstance().font;
        MutableComponent duration = JEIEvilCraftConfig.getDurationSecondsTextComponent(recipe.getDuration());
        guiGraphics.text(fontRenderer, duration, (background.getWidth() - fontRenderer.width(duration)) / 2 + 46, 50, ARGB.opaque(0xFF808080), false);
    }
}
