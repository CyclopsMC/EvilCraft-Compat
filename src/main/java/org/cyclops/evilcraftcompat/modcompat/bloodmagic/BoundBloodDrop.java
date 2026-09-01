package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.cyclopscore.helper.FluidHelpers;
import org.cyclops.cyclopscore.helper.L10NHelpers;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.cyclopscore.item.DamageIndicatedItemFluidContainer;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.core.fluid.FluidContainerItemWrapperWithSimulation;
import org.cyclops.evilcraft.core.helper.ItemHelpers;
import wayoftime.bloodmagic.common.item.IBindable;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * Can convert soul network life essence to blood.
 * @author rubensworks
 *
 */
public class BoundBloodDrop extends DamageIndicatedItemFluidContainer implements IBindable {

    private static BoundBloodDrop _instance = null;

    /**
     * Get the unique instance.
     * @return The instance.
     */
    public static BoundBloodDrop getInstance() {
        return _instance;
    }

    public BoundBloodDrop(ItemConfig eConfig) {
        super(new Item.Properties(), FluidHelpers.BUCKET_VOLUME, () -> RegistryEntries.FLUID_BLOOD);
        _instance = this;
    }

    @Override
    public boolean onBind(Player player, ItemStack stack) {
        return true;
    }

    private static int getCurrentEssence(UUID uuid) {
        return ClientSoulNetworkHandler.getInstance().getCurrentEssence(uuid);
    }

    private static int getMaxEssence(UUID uuid) {
        return Math.max(getCurrentEssence(uuid), ClientSoulNetworkHandler.getInstance().getMaxEssence(uuid));
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return ItemHelpers.isActivated(itemStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemStack, world, list, flag);
        L10NHelpers.addStatusInfo(list, ItemHelpers.isActivated(itemStack),
                getDescriptionId() + ".info.auto_supply");
        Binding binding = getBinding(itemStack);
        if (binding != null) {
            String owner = binding.getOwnerName();
            if (owner == null || owner.isEmpty()) {
                owner = ChatFormatting.ITALIC + L10NHelpers.localize(getDescriptionId() + ".info.current_owner.none");
            }
            list.add(Component.literal(L10NHelpers.localize(getDescriptionId() + ".info.current_owner", owner)));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                ItemHelpers.toggleActivation(itemStack);
            }
            return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level world, Entity entity, int slot, boolean selected) {
        if (ItemHelpers.isActivated(itemStack)) {
            FluidUtil.getFluidHandler(itemStack).ifPresent(h ->
                    ItemHelpers.updateAutoFill(h, world, entity, BoundBloodDropConfig.autoFillBuckets));
        }
        super.inventoryTick(itemStack, world, entity, slot, selected);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandler(stack, FluidHelpers.BUCKET_VOLUME);
    }

    public static class FluidHandler extends FluidContainerItemWrapperWithSimulation {

        public FluidHandler(ItemStack container, int capacity) {
            super(container, capacity, RegistryEntries.FLUID_BLOOD);
        }

        @Nullable
        protected UUID getUuid() {
            Binding binding = getBinding();
            if (binding == null) {
                return null;
            }
            return binding.getOwnerId();
        }

        @Override
        public int getCapacity() {
            UUID uuid = getUuid();
            if (uuid == null) {
                return 0;
            }
            if (MinecraftHelpers.isClientSideThread()) {
                return getMaxEssence(uuid);
            }
            return NetworkHelper.getMaximumForTier(NetworkHelper.getSoulNetwork(uuid).getOrbTier());
        }

        @Nullable
        protected Binding getBinding() {
            return BoundBloodDrop.getInstance().getBinding(getContainer());
        }

        @Override
        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            IFluidHandler.FluidAction doFill = shouldDoFill(resource, action);
            UUID uuid = getUuid();
            if (uuid == null) {
                return 0;
            }
            int essence = getCurrentEssence(uuid);
            int maxFill = Math.max(0, getCapacity() - essence);
            int filled = Math.min(maxFill, resource.getAmount());
            if (doFill.execute() && !MinecraftHelpers.isClientSideThread()) {
                NetworkHelper.getSoulNetwork(uuid).setCurrentEssence(essence + filled);
            }
            return filled;
        }

        @Override
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            UUID uuid = getUuid();
            if (uuid == null) {
                return FluidStack.EMPTY;
            }
            int essence = getCurrentEssence(uuid);
            FluidStack toDrain = new FluidStack(RegistryEntries.FLUID_BLOOD, maxDrain);
            int drainEssence = Math.min(essence, toDrain == null ? 0 : toDrain.getAmount());
            if (action.execute() && !MinecraftHelpers.isClientSideThread()) {
                NetworkHelper.getSoulNetwork(uuid).setCurrentEssence(essence - drainEssence);
            }
            FluidStack drained = new FluidStack(RegistryEntries.FLUID_BLOOD, drainEssence);
            return wrapSimulatedDrained(drained, action);
        }

        @Override
        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            IFluidHandler.FluidAction doDrain = shouldDoDrain(resource, action);
            if (resource != null && canFillFluidType(resource)) {
                return drain(resource.getAmount(), doDrain);
            }
            return FluidStack.EMPTY;
        }
    }

}
