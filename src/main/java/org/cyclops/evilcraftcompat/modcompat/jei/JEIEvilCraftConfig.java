package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.blockentity.BlockEntityBloodInfuser;
import org.cyclops.evilcraft.blockentity.BlockEntitySanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenBloodInfuser;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenExaltedCrafter;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenOriginsOfDarkness;
import org.cyclops.evilcraft.client.gui.container.ContainerScreenSanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraft.core.blockentity.BlockEntityWorking;
import org.cyclops.evilcraft.core.client.gui.container.ContainerScreenTileWorking;
import org.cyclops.evilcraft.inventory.container.ContainerBloodInfuser;
import org.cyclops.evilcraft.inventory.container.ContainerExaltedCrafter;
import org.cyclops.evilcraft.inventory.container.ContainerSanguinaryEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.RegistryEntriesCompat;
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
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_BLOOD_EXTRACTOR.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_BLOOD_PEARL_OF_TELEPORTATION.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_DARK_TANK.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_INVIGORATING_PENDANT.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_PRIMED_PENDANT.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_KINETICATOR.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_KINETICATOR_REPELLING.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_MACE_OF_DISTORTION.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_MACE_OF_DESTRUCTION.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_NECROMANCER_STAFF.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntriesCompat.ITEM_FLESH_REJUVENATED.get(), subtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, RegistryEntries.ITEM_ENTANGLED_CHALICE.get(), subtypeInterpreter);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new BloodInfuserRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new EnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SanguinaryEnvironmentalAccumulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(BloodInfuserRecipeCategory.TYPE, BloodInfuserRecipeJEI.getAllRecipes());
        registry.addRecipes(EnvironmentalAccumulatorRecipeCategory.TYPE, EnvironmentalAccumulatorRecipeJEI.getAllRecipes());
        registry.addRecipes(SanguinaryEnvironmentalAccumulatorRecipeCategory.TYPE, SanguinaryEnvironmentalAccumulatorRecipeJEI.getAllRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_BLOOD_INFUSER.get()), BloodInfuserRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_ENVIRONMENTAL_ACCUMULATOR.get()), EnvironmentalAccumulatorRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(RegistryEntries.BLOCK_SANGUINARY_ENVIRONMENTAL_ACCUMULATOR.get()), SanguinaryEnvironmentalAccumulatorRecipeCategory.TYPE);

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

        registry.addRecipeClickArea(ContainerScreenExaltedCrafter.class,
                88, 32, 28, 23, RecipeTypes.CRAFTING);

        registry.addGuiScreenHandler(ContainerScreenOriginsOfDarkness.class, (screen) -> null);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "main");
    }

    public static MutableComponent getDurationSecondsTextComponent(int durationTicks) {
        String seconds = new DecimalFormat("#.##").format((double) durationTicks / MinecraftHelpers.SECOND_IN_TICKS);
        return Component.translatable("gui.jei.category.smelting.time.seconds", seconds);
    }
}
