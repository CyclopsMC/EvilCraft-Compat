package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.blockentity.BlockEntityBloodInfuser;
import org.cyclops.evilcraft.blockentity.BlockEntitySanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraft.blockentity.BlockEntitySpiritFurnace;
import org.cyclops.evilcraft.blockentity.BlockEntitySpiritReanimator;
import org.cyclops.evilcraft.client.gui.container.*;
import org.cyclops.evilcraft.core.blockentity.BlockEntityWorking;
import org.cyclops.evilcraft.core.client.gui.container.ContainerScreenTileWorking;
import org.cyclops.evilcraft.inventory.container.*;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.RegistryEntriesCompat;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator.EnvironmentalAccumulatorRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.sanguinaryenvironmentalaccumulator.SanguinaryEnvironmentalAccumulatorRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace.SpiritFurnaceRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace.SpiritFurnaceRecipeJEI;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritreanimator.SpiritReanimatorRecipeCategory;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritreanimator.SpiritReanimatorRecipeJEI;

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
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_BLOOD_EXTRACTOR, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_BLOOD_PEARL_OF_TELEPORTATION, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_DARK_TANK, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_INVIGORATING_PENDANT, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_PRIMED_PENDANT, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_KINETICATOR, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_KINETICATOR_REPELLING, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_MACE_OF_DISTORTION, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_MACE_OF_DESTRUCTION, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_NECROMANCER_STAFF, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_FLESH_REJUVENATED, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_ENTANGLED_CHALICE, subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_BOX_OF_ETERNAL_CLOSURE, new SubtypeInterpreterBoxOfEternalClosure());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new BloodInfuserRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new EnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SanguinaryEnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritReanimatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritFurnaceRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(BloodInfuserRecipeCategory.TYPE, BloodInfuserRecipeJEI.getAllRecipes());
        registry.addRecipes(EnvironmentalAccumulatorRecipeCategory.TYPE, EnvironmentalAccumulatorRecipeJEI.getAllRecipes());
        registry.addRecipes(SanguinaryEnvironmentalAccumulatorRecipeCategory.TYPE, SanguinaryEnvironmentalAccumulatorRecipeJEI.getAllRecipes());
        registry.addRecipes(SpiritReanimatorRecipeCategory.TYPE, SpiritReanimatorRecipeJEI.getAllRecipes());
        registry.addRecipes(SpiritFurnaceRecipeCategory.TYPE, SpiritFurnaceRecipeJEI.getAllRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_BLOOD_INFUSER), BloodInfuserRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR), EnvironmentalAccumulatorRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_SANGUINARY_ENVIRONMENTAL_ACCUMULATOR), SanguinaryEnvironmentalAccumulatorRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_SPIRIT_REANIMATOR), SpiritReanimatorRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_SPIRIT_FURNACE), SpiritFurnaceRecipeCategory.TYPE);

        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER_WOODEN), RecipeTypes.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER), RecipeTypes.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER_WOODEN_EMPOWERED), RecipeTypes.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.ITEM_EXALTED_CRAFTER_EMPOWERED), RecipeTypes.CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
        registry.addRecipeTransferHandler(ContainerBloodInfuser.class, null, BloodInfuserRecipeCategory.TYPE,
                1, 1, BlockEntityBloodInfuser.SLOTS + BlockEntityWorking.INVENTORY_SIZE_UPGRADES, 36);
        registry.addRecipeTransferHandler(ContainerSanguinaryEnvironmentalAccumulator.class, null, SanguinaryEnvironmentalAccumulatorRecipeCategory.TYPE,
                0, 1, BlockEntitySanguinaryEnvironmentalAccumulator.SLOTS + BlockEntityWorking.INVENTORY_SIZE_UPGRADES, 36);
        registry.addRecipeTransferHandler(ContainerSpiritReanimator.class, null, SpiritReanimatorRecipeCategory.TYPE,
                1, 2, BlockEntitySpiritReanimator.SLOTS + BlockEntityWorking.INVENTORY_SIZE_UPGRADES, 36);
        registry.addRecipeTransferHandler(ContainerSpiritFurnace.class, null, SpiritFurnaceRecipeCategory.TYPE,
                1, 1, BlockEntitySpiritFurnace.SLOTS + BlockEntityWorking.INVENTORY_SIZE_UPGRADES, 36);

        registry.addRecipeTransferHandler(ContainerExaltedCrafter.class, null, RecipeTypes.CRAFTING,
                0, 9, 10, 27 + 36);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
        registry.addRecipeClickArea(ContainerScreenBloodInfuser.class,
                ContainerScreenTileWorking.UPGRADES_OFFSET_X + ContainerScreenBloodInfuser.PROGRESSTARGETX, ContainerScreenBloodInfuser.PROGRESSTARGETY,
                ContainerScreenBloodInfuser.PROGRESSWIDTH, ContainerScreenBloodInfuser.PROGRESSHEIGHT,
                BloodInfuserRecipeCategory.TYPE);
        registry.addRecipeClickArea(ContainerScreenSanguinaryEnvironmentalAccumulator.class,
                ContainerScreenTileWorking.UPGRADES_OFFSET_X + ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSTARGETX, ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSTARGETY,
                ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSWIDTH, ContainerScreenSanguinaryEnvironmentalAccumulator.PROGRESSHEIGHT,
                SanguinaryEnvironmentalAccumulatorRecipeCategory.TYPE);
        registry.addRecipeClickArea(ContainerScreenSpiritReanimator.class,
                ContainerScreenTileWorking.UPGRADES_OFFSET_X + ContainerScreenSpiritReanimator.PROGRESSTARGETX, ContainerScreenSpiritReanimator.PROGRESSTARGETY,
                ContainerScreenSpiritReanimator.PROGRESSWIDTH, ContainerScreenSpiritReanimator.PROGRESSHEIGHT,
                SpiritReanimatorRecipeCategory.TYPE);
        registry.addRecipeClickArea(ContainerScreenSpiritFurnace.class,
                ContainerScreenTileWorking.UPGRADES_OFFSET_X + ContainerScreenSpiritFurnace.PROGRESSTARGETX, ContainerScreenSpiritFurnace.PROGRESSTARGETY,
                ContainerScreenSpiritFurnace.PROGRESSWIDTH, ContainerScreenSpiritFurnace.PROGRESSHEIGHT,
                SpiritFurnaceRecipeCategory.TYPE);

        registry.addRecipeClickArea(ContainerScreenExaltedCrafter.class,
                88, 32, 28, 23, RecipeTypes.CRAFTING);

        registry.addGuiScreenHandler(ContainerScreenOriginsOfDarkness.class, (screen) -> null);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MOD_ID, "main");
    }

    public static MutableComponent getDurationSecondsTextComponent(int durationTicks) {
        String seconds = new DecimalFormat("#.##").format((double) durationTicks / MinecraftHelpers.SECOND_IN_TICKS);
        return Component.translatable("gui.jei.category.smelting.time.seconds", seconds);
    }
}
