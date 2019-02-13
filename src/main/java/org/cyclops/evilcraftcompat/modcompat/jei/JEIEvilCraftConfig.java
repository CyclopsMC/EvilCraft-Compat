package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.block.*;
import org.cyclops.evilcraft.client.gui.container.GuiBloodInfuser;
import org.cyclops.evilcraft.client.gui.container.GuiExaltedCrafter;
import org.cyclops.evilcraft.client.gui.container.GuiSanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraft.core.client.gui.container.GuiWorking;
import org.cyclops.evilcraft.core.recipe.DisplayStandRecipe;
import org.cyclops.evilcraft.item.*;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeTransferInfo;
import org.cyclops.evilcraftcompat.modcompat.jei.displaystand.DisplayStandRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.exaltedcrafter.ExaltedCrafterRecipeTransferInfo;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeTransferInfo;

import javax.annotation.Nonnull;

/**
 * Helper for registering JEI manager.
 * @author rubensworks
 *
 */
@JEIPlugin
public class JEIEvilCraftConfig implements IModPlugin {

    public static IJeiHelpers JEI_HELPER;

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        org.cyclops.evilcraftcompat.modcompat.jei.SubtypeInterpreterActivatableFluidContainer subtypeInterpreter = new SubtypeInterpreterActivatableFluidContainer();
        if (Configs.isEnabled(BloodExtractorConfig.class)) subtypeRegistry.registerSubtypeInterpreter(BloodExtractor.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(BloodPearlOfTeleportationConfig.class)) subtypeRegistry.registerSubtypeInterpreter(BloodPearlOfTeleportation.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(DarkTankConfig.class)) subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(DarkTank.getInstance()), subtypeInterpreter);
        if (Configs.isEnabled(InvigoratingPendantConfig.class)) subtypeRegistry.registerSubtypeInterpreter(InvigoratingPendant.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(PrimedPendantConfig.class)) subtypeRegistry.registerSubtypeInterpreter(PrimedPendant.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(KineticatorConfig.class)) subtypeRegistry.registerSubtypeInterpreter(Kineticator.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(MaceOfDistortionConfig.class)) subtypeRegistry.registerSubtypeInterpreter(MaceOfDistortion.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(MaceOfDestructionConfig.class)) subtypeRegistry.registerSubtypeInterpreter(MaceOfDestruction.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(NecromancerStaffConfig.class)) subtypeRegistry.registerSubtypeInterpreter(NecromancerStaff.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(RejuvenatedFleshConfig.class)) subtypeRegistry.registerSubtypeInterpreter(RejuvenatedFlesh.getInstance(), subtypeInterpreter);
        if (Configs.isEnabled(EntangledChaliceConfig.class)) subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(EntangledChalice.getInstance()), subtypeInterpreter);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        if (Configs.isEnabled(BloodInfuserConfig.class)) registry.addRecipeCategories(new BloodInfuserRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        if (Configs.isEnabled(EnvironmentalAccumulatorConfig.class)) registry.addRecipeCategories(new EnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        if (Configs.isEnabled(SanguinaryEnvironmentalAccumulatorConfig.class)) registry.addRecipeCategories(new SanguinaryEnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        JEI_HELPER = registry.getJeiHelpers();
        if(JEIModCompat.canBeUsed) {
            // Blood Infuser
            if (Configs.isEnabled(BloodInfuserConfig.class)) {
                registry.addRecipes(BloodInfuserRecipeJEI.getAllRecipes(), BloodInfuserRecipeJEI.CATEGORY);
                registry.addRecipeClickArea(GuiBloodInfuser.class,
                        GuiWorking.UPGRADES_OFFSET_X + GuiBloodInfuser.PROGRESSTARGETX, GuiBloodInfuser.PROGRESSTARGETY,
                        GuiBloodInfuser.PROGRESSWIDTH, GuiBloodInfuser.PROGRESSHEIGHT,
                        BloodInfuserRecipeJEI.CATEGORY);
                registry.getRecipeTransferRegistry().addRecipeTransferHandler(new BloodInfuserRecipeTransferInfo());
                registry.addRecipeCatalyst(new ItemStack(BloodInfuser.getInstance()), BloodInfuserRecipeJEI.CATEGORY);
            }

            // Envir Acc
            if (Configs.isEnabled(EnvironmentalAccumulatorConfig.class)) {
                registry.addRecipes(EnvironmentalAccumulatorRecipeJEI.getAllRecipes(), EnvironmentalAccumulatorRecipeJEI.CATEGORY);
                registry.addRecipeCatalyst(new ItemStack(EnvironmentalAccumulator.getInstance()), EnvironmentalAccumulatorRecipeJEI.CATEGORY);
            }

            // Sanguinary Envir Acc
            if (Configs.isEnabled(SanguinaryEnvironmentalAccumulatorConfig.class)) {
                registry.addRecipes(SanguinaryEnvironmentalAccumulatorRecipeJEI.getAllSanguinaryRecipes(), SanguinaryEnvironmentalAccumulatorRecipeJEI.CATEGORY);
                registry.addRecipeClickArea(GuiSanguinaryEnvironmentalAccumulator.class, GuiSanguinaryEnvironmentalAccumulator.PROGRESSTARGETX,
                        GuiSanguinaryEnvironmentalAccumulator.PROGRESSTARGETY, GuiSanguinaryEnvironmentalAccumulator.PROGRESSWIDTH,
                        GuiSanguinaryEnvironmentalAccumulator.PROGRESSHEIGHT, SanguinaryEnvironmentalAccumulatorRecipeJEI.CATEGORY);
                registry.getRecipeTransferRegistry().addRecipeTransferHandler(new SanguinaryEnvironmentalAccumulatorRecipeTransferInfo());
                registry.addRecipeCatalyst(new ItemStack(SanguinaryEnvironmentalAccumulator.getInstance()), SanguinaryEnvironmentalAccumulatorRecipeJEI.CATEGORY);
            }

            // Exalted Crafter
            if (Configs.isEnabled(ExaltedCrafterConfig.class)) {
                registry.addRecipeClickArea(GuiExaltedCrafter.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
                registry.getRecipeTransferRegistry().addRecipeTransferHandler(new ExaltedCrafterRecipeTransferInfo());
                NonNullList<ItemStack> exaltedCrafters = NonNullList.create();
                ExaltedCrafter.getInstance().getSubItems(CreativeTabs.SEARCH, exaltedCrafters);
                for (ItemStack exaltedCrafter : exaltedCrafters) {
                    registry.addRecipeCatalyst(exaltedCrafter, VanillaRecipeCategoryUid.CRAFTING);
                }
            }

            // Display Stand
            if (Configs.isEnabled(DisplayStandConfig.class)) {
                registry.handleRecipes(DisplayStandRecipe.class,
                        (recipe) -> new DisplayStandRecipeJEI(JEIEvilCraftConfig.JEI_HELPER, recipe),
                        VanillaRecipeCategoryUid.CRAFTING);
            }

            // Ignore items
            if (Configs.isEnabled(BloodStainedBlockConfig.class)) JEI_HELPER.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(BloodStainedBlock.getInstance()));
            if (Configs.isEnabled(InvisibleRedstoneBlockConfig.class)) JEI_HELPER.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(InvisibleRedstoneBlock.getInstance()));
        }
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

    }
}
