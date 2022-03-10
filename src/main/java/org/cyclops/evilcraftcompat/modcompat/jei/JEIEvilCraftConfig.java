package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenBloodInfuser;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenExaltedCrafter;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenOriginsOfDarkness;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenSanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraft.core.client.gui.container.ContainerScreenTileWorking;
import org.cyclops.evilcraft.core.blockentity.BlockEntityWorking;
import org.cyclops.evilcraft.inventory.container.ContainerBloodInfuser;
import org.cyclops.evilcraft.inventory.container.ContainerExaltedCrafter;
import org.cyclops.evilcraft.inventory.container.ContainerSanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraft.blockentity.BlockEntityBloodInfuser;
import org.cyclops.evilcraft.blockentity.BlockEntitySanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.RegistryEntriesCompat;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeJEI;

import java.text.DecimalFormat;

/**
 * Helper for registering JEI manager.
 * @author rubensworks
 *
 */
@JeiPlugin
public class JEIEvilCraftConfig implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistration subtypeRegistry) {
        SubtypeInterpreterActivatableFluidContainer subtypeInterpreter = new SubtypeInterpreterActivatableFluidContainer();
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntries.ITEM_BLOOD_EXTRACTOR, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntries.ITEM_BLOOD_PEARL_OF_TELEPORTATION, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntries.ITEM_DARK_TANK, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntriesCompat.ITEM_INVIGORATING_PENDANT, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntriesCompat.ITEM_PRIMED_PENDANT, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntriesCompat.ITEM_KINETICATOR, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntriesCompat.ITEM_KINETICATOR_REPELLING, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntries.ITEM_MACE_OF_DISTORTION, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntriesCompat.ITEM_MACE_OF_DESTRUCTION, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntriesCompat.ITEM_NECROMANCER_STAFF, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntriesCompat.ITEM_FLESH_REJUVENATED, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(RegistryEntries.ITEM_ENTANGLED_CHALICE, subtypeInterpreter);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new BloodInfuserRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new EnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SanguinaryEnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(BloodInfuserRecipeJEI.getAllRecipes(), BloodInfuserRecipeCategory.NAME);
        registry.addRecipes(EnvironmentalAccumulatorRecipeJEI.getAllRecipes(), EnvironmentalAccumulatorRecipeCategory.NAME);
        registry.addRecipes(SanguinaryEnvironmentalAccumulatorRecipeJEI.getAllRecipes(), SanguinaryEnvironmentalAccumulatorRecipeCategory.NAME);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_BLOOD_INFUSER), BloodInfuserRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR), EnvironmentalAccumulatorRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_SANGUINARY_ENVIRONMENTAL_ACCUMULATOR), SanguinaryEnvironmentalAccumulatorRecipeCategory.NAME);

        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER_WOODEN), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER_WOODEN_EMPOWERED), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER_EMPOWERED), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
        registry.addRecipeTransferHandler(ContainerBloodInfuser.class, BloodInfuserRecipeCategory.NAME,
                1, 1, BlockEntityBloodInfuser.SLOTS + BlockEntityWorking.INVENTORY_SIZE_UPGRADES, 36);
        registry.addRecipeTransferHandler(ContainerSanguinaryEnvironmentalAccumulator.class, SanguinaryEnvironmentalAccumulatorRecipeCategory.NAME,
                0, 1, BlockEntitySanguinaryEnvironmentalAccumulator.SLOTS + BlockEntityWorking.INVENTORY_SIZE_UPGRADES, 36);

        registry.addRecipeTransferHandler(ContainerExaltedCrafter.class, VanillaRecipeCategoryUid.CRAFTING,
                0, 9, 10, 27 + 36);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
        registry.addRecipeClickArea(ContainerScreenBloodInfuser.class,
                ContainerScreenTileWorking.UPGRADES_OFFSET_X + ContainerScreenBloodInfuser.PROGRESSTARGETX, ContainerScreenBloodInfuser.PROGRESSTARGETY,
                ContainerScreenBloodInfuser.PROGRESSWIDTH, ContainerScreenBloodInfuser.PROGRESSHEIGHT,
                BloodInfuserRecipeCategory.NAME);
        registry.addRecipeClickArea(ContainerScreenSanguinaryEnvironmentalAccumulator.class,
                ContainerScreenTileWorking.UPGRADES_OFFSET_X + ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSTARGETX, ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSTARGETY,
                ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSWIDTH, ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSHEIGHT,
                SanguinaryEnvironmentalAccumulatorRecipeCategory.NAME);

        registry.addRecipeClickArea(ContainerScreenExaltedCrafter.class,
                88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);

        registry.addGuiScreenHandler(ContainerScreenOriginsOfDarkness.class, (screen) -> null);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MOD_ID, "main");
    }

    public static MutableComponent getDurationSecondsTextComponent(int durationTicks) {
        String seconds = new DecimalFormat("#.##").format((double) durationTicks / MinecraftHelpers.SECOND_IN_TICKS);
        return new TranslatableComponent("gui.jei.category.smelting.time.seconds", seconds);
    }
}
