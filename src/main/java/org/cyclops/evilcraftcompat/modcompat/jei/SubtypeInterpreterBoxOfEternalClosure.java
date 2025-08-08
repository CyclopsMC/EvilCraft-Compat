package org.cyclops.evilcraftcompat.modcompat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import org.cyclops.evilcraft.block.BlockBoxOfEternalClosure;

import javax.annotation.Nonnull;

/**
 * @author rubensworks
 */
public class SubtypeInterpreterBoxOfEternalClosure implements IIngredientSubtypeInterpreter<ItemStack> {

    @Override
    public String apply(@Nonnull ItemStack itemStack, UidContext context) {
        EntityType<?> entityType = BlockBoxOfEternalClosure.getSpiritTypeRaw(itemStack);
        return entityType == null ? "" : BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
    }
}
