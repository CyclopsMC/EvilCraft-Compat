package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.cyclops.cyclopscore.config.configurable.ConfigurableDamageIndicatedItemFluidContainer;
import org.cyclops.cyclopscore.config.extendedconfig.ExtendedConfig;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.cyclopscore.helper.ItemStackHelpers;
import org.cyclops.cyclopscore.helper.L10NHelpers;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.evilcraft.core.fluid.BloodFluidConverter;
import org.cyclops.evilcraft.core.fluid.FluidContainerItemWrapperWithSimulation;
import org.cyclops.evilcraft.core.helper.ItemHelpers;
import org.cyclops.evilcraft.fluid.Blood;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * Can convert soul network life essence to blood.
 * @author rubensworks
 *
 */
public class BoundBloodDrop extends ConfigurableDamageIndicatedItemFluidContainer implements IBindable {
    
    private static org.cyclops.evilcraftcompat.modcompat.bloodmagic.BoundBloodDrop _instance = null;
    
    /**
     * Get the unique instance.
     * @return The instance.
     */
    public static org.cyclops.evilcraftcompat.modcompat.bloodmagic.BoundBloodDrop getInstance() {
        return _instance;
    }

    public BoundBloodDrop(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, Fluid.BUCKET_VOLUME, Blood.getInstance());
        setPlaceFluids(true);
    }

    @Override
    public boolean onBind(EntityPlayer player, ItemStack stack) {
        return true;
    }

    private static int getCurrentEssence(UUID uuid) {
    	return ClientSoulNetworkHandler.getInstance().getCurrentEssence(uuid);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return ItemHelpers.isActivated(itemStack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> itemList) {
        if (ItemStackHelpers.isValidCreativeTab(this, tab)) {
            itemList.add(new ItemStack(this));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(itemStack, world, list, flag);
        L10NHelpers.addStatusInfo(list, ItemHelpers.isActivated(itemStack),
                getUnlocalizedName() + ".info.auto_supply");
        Binding binding = getBinding(itemStack);
        if(binding != null) {
        	String owner = binding.getOwnerName();
        	if(owner == null || owner.isEmpty()) {
        		owner = TextFormatting.ITALIC + L10NHelpers.localize(getUnlocalizedName() + ".info.current_owner.none");
        	}
        	list.add(L10NHelpers.localize(getUnlocalizedName() + ".info.current_owner", owner));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
    	if(player.isSneaking()) {
            if(!world.isRemote)
            	ItemHelpers.toggleActivation(itemStack);
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
        }
        return super.onItemRightClick(world, player, hand);
    }
    
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
    	if(ItemHelpers.isActivated(itemStack)) {
    		ItemHelpers.updateAutoFill(FluidUtil.getFluidHandler(itemStack), world, entity);
    	}
        super.onUpdate(itemStack, world, entity, par4, par5);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        // This is a hack!
        return new FluidHandler(stack, Fluid.BUCKET_VOLUME);
    }

    public static class FluidHandler extends FluidContainerItemWrapperWithSimulation {

        public FluidHandler(ItemStack container, int capacity) {
            super(container, capacity, Blood.getInstance());
        }

        @Override
        public int getCapacity() {
            FluidStack contents = FluidUtil.getFluidContained(container);
            int contentsAmount = contents == null ? 0 : contents.amount;
            return Math.max(contentsAmount, BoundBloodDropConfig.maxCapacity);
        }

        @Nullable
        protected Binding getBinding() {
            return BoundBloodDrop.getInstance().getBinding(getContainer());
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            doFill = shouldDoFill(resource, doFill);
            Binding binding = getBinding();
            if (binding == null) {
                return 0;
            }
            UUID uuid = binding.getOwnerId();
            if (uuid == null) {
                return 0;
            }
            int essence = getCurrentEssence(uuid);
            FluidStack essenceFluid = BloodFluidConverter.getInstance().convertReverse(BlockLifeEssence.getLifeEssence(), resource);
            int filled = essenceFluid == null ? 0 : essenceFluid.amount;
            if(doFill && !MinecraftHelpers.isClientSide()) {
                NetworkHelper.getSoulNetwork(uuid).setCurrentEssence(essence + filled);
            }
            return filled;
        }

        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            Binding binding = getBinding();
            if (binding == null) {
                return null;
            }
            UUID uuid = binding.getOwnerId();
            if(uuid == null) {
                return null;
            }
            int essence = getCurrentEssence(uuid);
            FluidStack toDrain = new FluidStack(Blood.getInstance(), maxDrain);
            FluidStack toDrainEssence = BloodFluidConverter.getInstance().convertReverse(BlockLifeEssence.getLifeEssence(), toDrain);
            int drainEssence = Math.min(essence, toDrainEssence == null ? 0 : toDrainEssence.amount);
            if(doDrain && !MinecraftHelpers.isClientSide()) {
                NetworkHelper.getSoulNetwork(uuid).setCurrentEssence(essence - drainEssence);
            }
            FluidStack drainedEssence = new FluidStack(BlockLifeEssence.getLifeEssence(), drainEssence);
            return wrapSimulatedDrained(BloodFluidConverter.getInstance().convert(drainedEssence), doDrain);
        }

        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            doDrain = shouldDoDrain(resource, doDrain);
            if (resource != null && canDrainFluidType(resource)) {
                return drain(resource.amount, doDrain);
            }
            return null;
        }
    }

}
