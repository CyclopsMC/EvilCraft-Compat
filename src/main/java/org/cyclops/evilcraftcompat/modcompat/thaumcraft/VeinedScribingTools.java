package org.cyclops.evilcraftcompat.modcompat.thaumcraft;

import net.minecraft.item.ItemStack;
import net.neoforged.neoforge.fluids.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.cyclops.cyclopscore.config.configurable.ConfigurableDamageIndicatedItemFluidContainer;
import org.cyclops.cyclopscore.config.extendedconfig.ExtendedConfig;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.evilcraft.fluid.Blood;
import thaumcraft.api.items.IScribeTools;

/**
 * Scribing tools that run on Blood.
 * Texture is based on the one from Thaumcraft.
 * @author rubensworks
 *
 */
public class VeinedScribingTools extends ConfigurableDamageIndicatedItemFluidContainer implements IScribeTools {

    private static final int CAPACITY = Fluid.BUCKET_VOLUME * 2;
    private static final int USAGE = 10;

    private static VeinedScribingTools _instance = null;

    /**
     * Get the unique instance.
     * @return The instance.
     */
    public static VeinedScribingTools getInstance() {
        return _instance;
    }

    public VeinedScribingTools(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, CAPACITY, Blood.getInstance());
        setMaxDamage(CAPACITY / USAGE);
        this.canPickUp = false;
    }

    @Override
    public int getDamage(ItemStack itemStack) {
        FluidStack fluidStack = null;
        if (!itemStack.isEmpty()) {
            IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(itemStack);
            if (fluidHandler != null) {
                fluidStack = fluidHandler.drain(Integer.MAX_VALUE, false);
            }
        }
        if(fluidStack == null) return 0;
        return (CAPACITY - fluidStack.amount) / USAGE;
    }

    @Override
    public void setDamage(ItemStack itemStack, int damage) {
        IFluidHandler fluidHandler = FluidUtil.getFluidHandler(itemStack);
        FluidStack fluidStack = fluidHandler.drain(Integer.MAX_VALUE, false);
        if(fluidStack != null) {
            fluidHandler.drain((damage - getDamage(itemStack)) * USAGE, true);
        }
    }

}
