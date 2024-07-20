package org.cyclops.evilcraftcompat.modcompat.capabilities;

import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraft.RegistryEntries;

/**
 * @author rubensworks
 */
public class CommonCapabilitiesCompatInitializer implements ICompatInitializer {
    @Override
    public void initialize() {
        // Capabilities
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_BLOOD_CHEST, new WorkerWorkingBlockEntityCompat<>());
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_BLOOD_INFUSER, new WorkerWorkingBlockEntityCompat<>());
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_COLOSSAL_BLOOD_CHEST, new WorkerWorkingBlockEntityCompat<>());
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_SANGUINARY_ENVIRONMENTAL_ACCUMULATOR, new WorkerWorkingBlockEntityCompat<>());
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_SPIRIT_FURNACE, new WorkerWorkingBlockEntityCompat<>());
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_SPIRIT_REANIMATOR, new WorkerWorkingBlockEntityCompat<>());
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_ENVIRONMENTAL_ACCUMULATOR, new WorkerEnvirAccBlockEntityCompat());
        EvilCraft._instance.getCapabilityConstructorRegistry().registerBlockEntity(RegistryEntries.BLOCK_ENTITY_BLOOD_INFUSER, new RecipeHandlerBloodInfuserBlockEntityCompat());
    }
}
