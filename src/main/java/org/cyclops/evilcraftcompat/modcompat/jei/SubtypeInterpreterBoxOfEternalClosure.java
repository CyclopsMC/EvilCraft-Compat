package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.cyclops.evilcraft.block.BlockBoxOfEternalClosure;
import org.jetbrains.annotations.Nullable;

/**
 * @author rubensworks
 */
public class SubtypeInterpreterBoxOfEternalClosure implements ISubtypeInterpreter<ItemStack> {

    @Override
    public @Nullable Object getSubtypeData(ItemStack itemStack, UidContext context) {
        return BlockBoxOfEternalClosure.getSpiritTypeRaw(itemStack);
    }
}
