package org.cyclops.evilcraftcompat.modcompat.jei.spiritreanimator;

import com.google.common.collect.Lists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.fluids.FluidStack;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.block.BlockSpiritReanimatorConfig;

import java.util.List;

/**
 * Recipe wrapper for Spirit Reanimator recipes
 * @author rubensworks
 */
public class SpiritReanimatorRecipeJEI {

    private final FluidStack inputFluid;
    private final ItemStack inputItem;
    private final ItemStack outputItem;
    private final int duration;

    public SpiritReanimatorRecipeJEI(EntityType<?> entityType) {
        this.inputFluid = new FluidStack(RegistryEntries.FLUID_BLOOD, BlockSpiritReanimatorConfig.mBPerTick * BlockSpiritReanimatorConfig.requiredTicks);
        this.inputItem = getBox(entityType);
        this.outputItem = new ItemStack(SpawnEggItem.byId(entityType));
        this.duration = BlockSpiritReanimatorConfig.requiredTicks;
    }

    public static ItemStack getBox(EntityType<?> entityType) {
        ItemStack stack = new ItemStack(RegistryEntries.BLOCK_BOX_OF_ETERNAL_CLOSURE.get());
        CompoundTag spiritTag = new CompoundTag();
        spiritTag.putString("innerEntity", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
        stack.set(RegistryEntries.COMPONENT_BOX_SPIRIT_DATA, spiritTag);
        return stack;
    }

    public FluidStack getInputFluid() {
        return inputFluid;
    }

    public ItemStack getInputItem() {
        return inputItem;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public int getDuration() {
        return duration;
    }

    public static List<SpiritReanimatorRecipeJEI> getAllRecipes() {
        List<SpiritReanimatorRecipeJEI> recipes = Lists.newArrayList();
        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (SpawnEggItem.byId(entityType) != null) {
                recipes.add(new SpiritReanimatorRecipeJEI(entityType));
            }
        }
        return recipes;
    }
}
