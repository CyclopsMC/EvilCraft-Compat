package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author rubensworks
 */
public class SubtypeInterpreterActivatableFluidContainer implements ISubtypeRegistry.ISubtypeInterpreter {

    @Override
    public String apply(@Nonnull ItemStack itemStack) {
        return "";
        /*FluidStack fluidStack = FluidUtil.getFluidContained(itemStack);
        if (fluidStack.amount == 0) fluidStack = null;
        return (ItemHelpers.isActivated(itemStack) ? "true" : "false") + "::" + (fluidStack != null ? fluidStack.getFluid().getUnlocalizedName() : "none");*/
    }
}
