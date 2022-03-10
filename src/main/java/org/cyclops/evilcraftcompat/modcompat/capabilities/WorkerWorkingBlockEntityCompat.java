package org.cyclops.evilcraftcompat.modcompat.capabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.SimpleCapabilityConstructor;
import org.cyclops.evilcraft.core.blockentity.BlockEntityTickingTankInventory;
import org.cyclops.evilcraft.core.blockentity.tickaction.ITickAction;
import org.cyclops.evilcraft.core.blockentity.tickaction.TickComponent;
import org.cyclops.evilcraftcompat.Capabilities;

import javax.annotation.Nullable;

/**
 * Compatibility for worker capabilities.
 * @author rubensworks
 */
public class WorkerWorkingBlockEntityCompat extends SimpleCapabilityConstructor<IWorker, BlockEntityTickingTankInventory> {

    @Override
    public Capability<IWorker> getCapability() {
        return Capabilities.WORKER;
    }

    @Nullable
    @Override
    public ICapabilityProvider createProvider(BlockEntityTickingTankInventory host) {
        return new DefaultCapabilityProvider<IWorker>(Capabilities.WORKER, new Worker<BlockEntityTickingTankInventory<?>>(host));
    }

    public static class Worker<T extends BlockEntityTickingTankInventory<?>> implements IWorker {

        private final T provider;

        public Worker(T provider) {
            this.provider = provider;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean hasWork() {
            for(TickComponent ticker : provider.getTickers()) {
                ItemStack itemStack = provider.getInventory().getItem(ticker.getSlot());
                if(!itemStack.isEmpty()) {
                    ITickAction tickAction;
                    int actionOffset = 0;
                    while((tickAction = ticker.getTickAction(itemStack.getItem(), actionOffset++)) != null
                            && tickAction.canTick(provider, itemStack, ticker.getSlot(), ticker.getTick())) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canWork() {
            return !provider.getLevel().hasNeighborSignal(provider.getBlockPos());
        }
    }
}
