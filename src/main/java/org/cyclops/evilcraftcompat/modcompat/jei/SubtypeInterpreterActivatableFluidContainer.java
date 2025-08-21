package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author rubensworks
 */
public class SubtypeInterpreterActivatableFluidContainer implements ISubtypeInterpreter<ItemStack> {

    @Override
    public @Nullable Object getSubtypeData(ItemStack itemStack, UidContext context) {
        FluidStack fluidStack = FluidUtil.getFluidContained(itemStack).orElse(FluidStack.EMPTY);
        return fluidStack.getFluid();
    }
}
