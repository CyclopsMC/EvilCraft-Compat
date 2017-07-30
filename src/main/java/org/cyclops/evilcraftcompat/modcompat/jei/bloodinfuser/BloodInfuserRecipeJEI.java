package org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser;

import com.google.common.collect.Lists;
import lombok.Data;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.cyclops.cyclopscore.modcompat.jei.RecipeRegistryJeiRecipeWrapper;
import org.cyclops.cyclopscore.recipe.custom.api.IRecipe;
import org.cyclops.cyclopscore.recipe.custom.api.IRecipeRegistry;
import org.cyclops.cyclopscore.recipe.custom.component.IngredientRecipeComponent;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.block.BloodInfuser;
import org.cyclops.evilcraft.core.recipe.custom.DurationXpRecipeProperties;
import org.cyclops.evilcraft.core.recipe.custom.IngredientFluidStackAndTierRecipeComponent;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

/**
 * Recipe wrapper for Blood Infuser recipes
 * @author rubensworks
 */
@Data
public class BloodInfuserRecipeJEI extends RecipeRegistryJeiRecipeWrapper<BloodInfuser, IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties, BloodInfuserRecipeJEI> {

    public static final String CATEGORY = Reference.MOD_ID + ":bloodInfuser";

    private final FluidStack fluidStack;
    private final int upgrade;
    private final List<ItemStack> input;
    private final List<ItemStack> output;
    private final String xpString;

    public BloodInfuserRecipeJEI(IRecipe<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> recipe) {
        super(recipe);
        this.fluidStack = recipe.getInput().getFluidStack();
        this.upgrade = recipe.getInput().getTier();
        this.input = recipe.getInput().getItemStacks();
        this.output = recipe.getOutput().getItemStacks();
        this.xpString = Translator.translateToLocalFormatted("gui.jei.category.smelting.experience", recipe.getProperties().getXp());
    }

    protected BloodInfuserRecipeJEI() {
        super(null);
        this.fluidStack = null;
        this.upgrade = -1;
        this.input = null;
        this.output = null;
        this.xpString = null;
    }

    @Override
    protected IRecipeRegistry<BloodInfuser, IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> getRecipeRegistry() {
        return BloodInfuser.getInstance().getRecipeRegistry();
    }

    @Override
    protected BloodInfuserRecipeJEI newInstance(IRecipe<IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> input) {
        return new BloodInfuserRecipeJEI(input);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, input);
        ingredients.setOutputs(ItemStack.class, output);
        ingredients.setInputs(FluidStack.class, Lists.newArrayList(fluidStack));
    }

    public static List<BloodInfuserRecipeJEI> getAllRecipes() {
        return new BloodInfuserRecipeJEI().createAllRecipes();
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        FontRenderer fontRendererObj = minecraft.fontRenderer;
        fontRendererObj.drawString(this.xpString, 100 - fontRendererObj.getStringWidth(this.xpString) / 2, 5, Color.gray.getRGB());
    }
}
