package org.cyclops.evilcraftcompat.modcompat.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.api.crafting.SqueezerRecipe;
import net.minecraft.item.ItemStack;
import net.neoforged.neoforge.fluids.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.block.*;
import org.cyclops.evilcraft.fluid.Blood;
import org.cyclops.evilcraft.fluid.BloodConfig;
import org.cyclops.evilcraft.item.DarkGem;
import org.cyclops.evilcraft.item.DarkGemConfig;
import org.cyclops.evilcraft.item.DarkGemCrushedConfig;
import org.cyclops.evilcraft.item.HardenedBloodShardConfig;

/**
 * Immersive Engineering recipe manager registrations.
 * @author runesmacher
 *
 */
public class ImmersiveEngineeringRecipeManager {
    public static void register() {
        // Register Undead Log squeezer recipe.
        if(Configs.isEnabled(UndeadLogConfig.class)
                && Configs.isEnabled(BloodConfig.class)
                && Configs.isEnabled(HardenedBloodShardConfig.class)) {
            int energy = 10000;
            ItemStack[] input = {new ItemStack(UndeadLogConfig._instance.getBlockInstance())};
            FluidStack fluidStack = new FluidStack(Blood.getInstance(),
                    Fluid.BUCKET_VOLUME / 10);
            SqueezerRecipe recipe = new SqueezerRecipe(fluidStack, null, input, energy);
            SqueezerRecipe.recipeList.add(recipe);
        }

        // Register Undead Leaves squeezer recipes.
        if(Configs.isEnabled(UndeadLeavesConfig.class)
                && Configs.isEnabled(BloodConfig.class)
                && Configs.isEnabled(HardenedBloodShardConfig.class)) {
            int energy = 5000;
            ItemStack[] input = {new ItemStack(UndeadLeaves.getInstance())};
            FluidStack fluidStack = new FluidStack(Blood.getInstance(),
                    Fluid.BUCKET_VOLUME / 20);
            SqueezerRecipe recipe = new SqueezerRecipe(fluidStack, null, input, energy);
            SqueezerRecipe.recipeList.add(recipe);
        }

        // Crusher dark ore -> dark gem
        if(Configs.isEnabled(DarkOreConfig.class) && Configs.isEnabled(DarkGemConfig.class)) {
            ItemStack input = new ItemStack(DarkOre.getInstance());
            ItemStack output = new ItemStack(DarkGem.getInstance(), 2);
            int energy = 3600;

            CrusherRecipe recipe = new CrusherRecipe(output, input, energy);
            CrusherRecipe.recipeList.add(recipe);
        }

        // Crusher dark gem -> crushed dark gem
        if(Configs.isEnabled(DarkGemConfig.class) && Configs.isEnabled(DarkGemCrushedConfig.class)) {
            ItemStack input = new ItemStack(DarkGem.getInstance());
            ItemStack output = new ItemStack(DarkGemCrushedConfig._instance.getItemInstance(), 1);
            int energy = 3600;

            CrusherRecipe recipe = new CrusherRecipe(output, input, energy);
            CrusherRecipe.recipeList.add(recipe);
        }
    }

}
