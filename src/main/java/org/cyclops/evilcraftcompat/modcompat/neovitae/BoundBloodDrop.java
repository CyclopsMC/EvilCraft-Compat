package org.cyclops.evilcraftcompat.modcompat.neovitae;

import com.breakinblocks.neovitae.common.datacomponent.Binding;
import com.breakinblocks.neovitae.common.item.IBindable;
import net.minecraft.ChatFormatting;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.cyclopscore.helper.L10NHelpers;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.cyclopscore.item.DamageIndicatedItemFluidContainer;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.core.fluid.FluidContainerItemWrapperWithSimulation;
import org.cyclops.evilcraft.core.helper.ItemHelpers;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * Can convert the Essentia Vitae of an anima (Neo Vitae's soul network) to Blood.
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
        super(new Item.Properties(), BoundBloodDropConfig.animaCapacity, RegistryEntries.FLUID_BLOOD::get);
        _instance = this;
    }

    @Override
    public boolean onBind(Player player, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return ItemHelpers.isActivated(itemStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemStack, context, list, flag);
        L10NHelpers.addStatusInfo(list, ItemHelpers.isActivated(itemStack),
                getDescriptionId() + ".info.auto_supply");
        Binding binding = getBinding(itemStack);
        String owner = binding == null ? null : binding.name();
        if (owner == null || owner.isEmpty()) {
            owner = ChatFormatting.ITALIC + L10NHelpers.localize(getDescriptionId() + ".info.current_owner.none");
        }
        list.add(Component.literal(L10NHelpers.localize(getDescriptionId() + ".info.current_owner", owner)));
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

    /**
     * A fluid handler that is backed by the anima of the item's owner,
     * instead of by the item itself.
     */
    public static class FluidHandler extends FluidContainerItemWrapperWithSimulation {

        public FluidHandler(ItemStack container) {
            super(container, BoundBloodDropConfig.animaCapacity, RegistryEntries.FLUID_BLOOD.get());
        }

        @Nullable
        protected Binding getBinding() {
            Item item = getContainer().getItem();
            return item instanceof IBindable ? ((IBindable) item).getBinding(getContainer()) : null;
        }

        @Nullable
        protected UUID getUuid() {
            Binding binding = getBinding();
            return binding == null ? null : binding.uuid();
        }

        protected int getCurrentEssence(UUID uuid) {
            return ClientAnimaHandler.getInstance().getCurrentEssence(uuid);
        }

        @Override
        public int getCapacity() {
            return getUuid() == null ? 0 : BoundBloodDropConfig.animaCapacity;
        }

        @Override
        public int getTankCapacity(int tank) {
            return getCapacity();
        }

        @Override
        public void setCapacity(int capacity) {
            // The capacity is determined by the config, and can not be changed per-item.
        }

        @Override
        public FluidStack getFluid() {
            this.capacity = getCapacity(); // Force overriding the protected capacity field as soon as possible.
            UUID uuid = getUuid();
            if (uuid == null) {
                return FluidStack.EMPTY;
            }
            int essence = getCurrentEssence(uuid);
            return essence <= 0 ? FluidStack.EMPTY : new FluidStack(RegistryEntries.FLUID_BLOOD.get(), essence);
        }

        @Override
        protected void setFluid(FluidStack fluidStack) {
            UUID uuid = getUuid();
            if (uuid == null || MinecraftHelpers.isClientSideThread()) {
                return;
            }
            AnimaHelpers.setEssence(uuid, canFillFluidType(fluidStack) ? fluidStack.getAmount() : 0, getCapacity());
        }

        @Override
        protected void setContainerToEmpty() {
            // The item itself never holds any fluid, so there is nothing to empty.
        }

        @Override
        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            IFluidHandler.FluidAction doFill = shouldDoFill(resource, action);
            UUID uuid = getUuid();
            if (uuid == null || resource.isEmpty() || !canFillFluidType(resource)) {
                return 0;
            }
            int capacity = getCapacity();
            int filled = Math.min(Math.max(0, capacity - getCurrentEssence(uuid)), resource.getAmount());
            if (filled > 0 && doFill.execute() && !MinecraftHelpers.isClientSideThread()) {
                filled = AnimaHelpers.addEssence(uuid, filled, capacity);
            }
            return filled;
        }

        @Override
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            UUID uuid = getUuid();
            if (uuid == null || maxDrain <= 0) {
                return FluidStack.EMPTY;
            }
            int drained = Math.min(getCurrentEssence(uuid), maxDrain);
            if (drained > 0 && action.execute() && !MinecraftHelpers.isClientSideThread()) {
                drained = AnimaHelpers.syphonEssence(uuid, drained);
            }
            if (drained <= 0) {
                return FluidStack.EMPTY;
            }
            return wrapSimulatedDrained(new FluidStack(RegistryEntries.FLUID_BLOOD.get(), drained), action);
        }

        @Override
        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            if (resource.isEmpty() || !canFillFluidType(resource)) {
                return FluidStack.EMPTY;
            }
            return drain(resource.getAmount(), shouldDoDrain(resource, action));
        }
    }

}
