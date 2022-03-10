package org.cyclops.evilcraftcompat.modcompat.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.SimpleCapabilityConstructor;
import org.cyclops.evilcraftcompat.Capabilities;
import org.cyclops.evilcraft.blockentity.BlockEntityEnvironmentalAccumulator;

import javax.annotation.Nullable;

/**
 * Compatibility for envir acc worker capabilities.
 * @author rubensworks
 */
public class WorkerEnvirAccBlockEntityCompat extends SimpleCapabilityConstructor<IWorker, BlockEntityEnvironmentalAccumulator> {

    @Override
    public Capability<IWorker> getCapability() {
        return Capabilities.WORKER;
    }

    @Nullable
    @Override
    public ICapabilityProvider createProvider(BlockEntityEnvironmentalAccumulator host) {
        return new DefaultCapabilityProvider<IWorker>(Capabilities.WORKER, new Worker(host));
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
