package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.cyclops.evilcraft.core.helper.ItemHelpers;

import javax.annotation.Nonnull;

/**
 * @author rubensworks
 */
public class SubtypeInterpreterActivatableFluidContainer implements ISubtypeInterpreter {

    @Override
    public String apply(@Nonnull ItemStack itemStack) {
        FluidStack fluidStack = FluidUtil.getFluidContained(itemStack).orElse(FluidStack.EMPTY);
        return (ItemHelpers.isActivated(itemStack) ? "true" : "false") + "::" + (!fluidStack.isEmpty() ? fluidStack.getFluid().getRegistryName() : "none");
    }
}
