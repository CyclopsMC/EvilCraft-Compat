package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.cyclops.evilcraft.core.helper.ItemHelpers;

import javax.annotation.Nonnull;

/**
 * @author rubensworks
 */
public class SubtypeInterpreterActivatableFluidContainer implements IIngredientSubtypeInterpreter<ItemStack> {

    @Override
    public String apply(@Nonnull ItemStack itemStack, UidContext context) {
        FluidStack fluidStack = FluidUtil.getFluidContained(itemStack).orElse(FluidStack.EMPTY);
        return (ItemHelpers.isActivated(itemStack) ? "true" : "false") + "::" + (!fluidStack.isEmpty() ? BuiltInRegistries.FLUID.getKey(fluidStack.getFluid()) : "none");
    }
}
