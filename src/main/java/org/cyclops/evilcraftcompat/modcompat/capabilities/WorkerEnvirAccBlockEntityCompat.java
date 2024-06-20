package org.cyclops.evilcraftcompat.modcompat.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.api.capability.Capabilities;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;
import org.cyclops.evilcraft.blockentity.BlockEntityEnvironmentalAccumulator;

import javax.annotation.Nullable;

/**
 * Compatibility for envir acc worker capabilities.
 * @author rubensworks
 */
public class WorkerEnvirAccBlockEntityCompat implements ICapabilityConstructor<BlockEntityEnvironmentalAccumulator, Direction, IWorker, BlockEntityType<BlockEntityEnvironmentalAccumulator>> {

    @Override
    public BaseCapability<IWorker, Direction> getCapability() {
        return Capabilities.Worker.BLOCK;
    }

    @Nullable
    @Override
    public ICapabilityProvider<BlockEntityEnvironmentalAccumulator, Direction, IWorker> createProvider(BlockEntityType<BlockEntityEnvironmentalAccumulator> host) {
        return (blockEntity, side) -> new Worker(blockEntity);
    }

    public static class Worker implements IWorker {

        private final BlockEntityEnvironmentalAccumulator provider;

        public Worker(BlockEntityEnvironmentalAccumulator provider) {
            this.provider = provider;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean hasWork() {
            return provider.getRecipe() != null;
        }

        @Override
        public boolean canWork() {
            return true;
        }
    }
}
