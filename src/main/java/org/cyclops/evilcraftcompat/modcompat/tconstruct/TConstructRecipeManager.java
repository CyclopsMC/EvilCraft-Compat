package org.cyclops.evilcraftcompat.modcompat.tconstruct;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraft.api.blockentity.bloodchest.IBloodChestRepairActionRegistry;
import org.cyclops.evilcraft.block.HardenedBlood;
import org.cyclops.evilcraft.block.HardenedBloodConfig;
import org.cyclops.evilcraft.fluid.Blood;
import org.cyclops.evilcraft.fluid.BloodConfig;
import org.cyclops.evilcraft.item.EnderTearConfig;
import org.cyclops.evilcraftcompat.modcompat.tconstruct.TConstructToolRepairTickAction;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;

/**
 * Tinkers' Construct recipe manager registrations.
 * @author runesmacher
 *
 */
public class TConstructRecipeManager {
    public static void register() {
        EvilCraft._instance.getRegistryManager().getRegistry(IBloodChestRepairActionRegistry.class)
                .register(new TConstructToolRepairTickAction());
        Fluid ender = FluidRegistry.getFluid("ender");
        if (Configs.isEnabled(EnderTearConfig.class) && ender != null) {
            TinkerRegistry.registerMelting(new ItemStack(EnderTearConfig._instance.getItemInstance()), ender,
                    EnderTearConfig.mbLiquidEnder);
        }

        if (Configs.isEnabled(HardenedBloodConfig.class) && Configs.isEnabled(BloodConfig.class)) {
            TinkerRegistry.registerBasinCasting(
                    new CastingRecipe(new ItemStack(HardenedBlood.getInstance()), Blood.getInstance(), 1000, 400));
        }
    }

}
