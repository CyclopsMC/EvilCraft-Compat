package org.cyclops.evilcraftcompat.modcompat.forestry;

import forestry.api.core.ForestryAPI;
import forestry.api.farming.IFarmRegistry;
import forestry.farming.logic.farmables.FarmableSapling;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.block.UndeadSaplingConfig;

/**
 * Registers Evilcraft-Plants in the Forestry Multifarm
 * @author GityUpNow
 */
public class ForestryFarmManager {


    public static void register() {
        IFarmRegistry registry = ForestryAPI.farmRegistry;

        //Add the Undead Sapling to the Tree Farm
        if(Configs.isEnabled(UndeadSaplingConfig.class)) {

            registry.registerFarmables("farmArboreal", new FarmableSapling(
                    new ItemStack(UndeadSaplingConfig._instance.getBlockInstance()),
                    new ItemStack[]{
                            new ItemStack(Blocks.DEADBUSH)
                    }
            ));
        }
    }
}
