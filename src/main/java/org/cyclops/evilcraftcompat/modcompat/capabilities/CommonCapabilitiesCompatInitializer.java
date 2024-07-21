package org.cyclops.evilcraftcompat.modcompat.capabilities;

import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

/**
 * @author rubensworks
 */
public class CommonCapabilitiesCompatInitializer implements ICompatInitializer {
    @Override
    public void initialize() {
        // Capabilities
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_BLOOD_CHEST, new WorkerWorkingBlockEntityCompat<>());
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_BLOOD_INFUSER, new WorkerWorkingBlockEntityCompat<>());
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_COLOSSAL_BLOOD_CHEST, new WorkerWorkingBlockEntityCompat<>());
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_SANGUINARY_ENVIRONMENTAL_ACCUMULATOR, new WorkerWorkingBlockEntityCompat<>());
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_SPIRIT_FURNACE, new WorkerWorkingBlockEntityCompat<>());
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_SPIRIT_REANIMATOR, new WorkerWorkingBlockEntityCompat<>());
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_ENVIRONMENTAL_ACCUMULATOR, new WorkerEnvirAccBlockEntityCompat());
        EvilCraftCompat._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_BLOOD_INFUSER, new RecipeHandlerBloodInfuserBlockEntityCompat());
    }
}
