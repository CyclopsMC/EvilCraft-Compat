package org.cyclops.evilcraftcompat.modcompat.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.api.capability.Capabilities;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;
import org.cyclops.evilcraft.core.blockentity.BlockEntityTickingTankInventory;
import org.cyclops.evilcraft.core.blockentity.tickaction.ITickAction;
import org.cyclops.evilcraft.core.blockentity.tickaction.TickComponent;

import javax.annotation.Nullable;

/**
 * Compatibility for worker capabilities.
 * @author rubensworks
 */
public class WorkerWorkingBlockEntityCompat<T extends BlockEntityTickingTankInventory<?>> implements ICapabilityConstructor<T, Direction, IWorker, BlockEntityType<T>> {

    @Override
    public BaseCapability<IWorker, Direction> getCapability() {
        return Capabilities.Worker.BLOCK;
    }

    @Nullable
    @Override
    public ICapabilityProvider<T, Direction, IWorker> createProvider(BlockEntityType<T> host) {
        return (blockEntity, side) -> new Worker<>(blockEntity);
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
