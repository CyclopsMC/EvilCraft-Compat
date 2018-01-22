package org.cyclops.evilcraftcompat.modcompat.forestry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.block.UndeadLogConfig;
import org.cyclops.evilcraft.item.DarkGem;
import org.cyclops.evilcraft.item.DarkGemConfig;
import org.cyclops.evilcraft.item.PoisonSacConfig;

/**
 * Compatibility plugin for Forestry.
 * @author rubensworks
 *
 */
public class ForestryModCompat implements IModCompat {

    @Override
    public String getModID() {
       return Reference.MOD_FORESTRY;
    }

    @Override
    public void onInit(Step step) {
    	if(step == Step.INIT) {

	        // Add dark gem to the miner backpack.
	        if(Configs.isEnabled(DarkGemConfig.class)) {
	            FMLInterModComms.sendMessage(getModID(), "add-backpack-items",
						"forestry.miner@" + Item.REGISTRY.getNameForObject(DarkGem.getInstance()).toString() + ":*");
	        }
	        
	        // Add poison sac to hunter backpack.
	        if(Configs.isEnabled(PoisonSacConfig.class)) {
	            FMLInterModComms.sendMessage(getModID(), "add-backpack-items",
						"forestry.hunter@" + Item.REGISTRY.getNameForObject(PoisonSacConfig._instance.getItemInstance()).toString() + ":*");
	        }
	        
	        // Add undead clog to forester backpack.
	        if(Configs.isEnabled(UndeadLogConfig.class)) {
	            FMLInterModComms.sendMessage(getModID(), "add-backpack-items",
						"forestry.forester@" + Block.REGISTRY.getNameForObject(UndeadLogConfig._instance.getBlockInstance()).toString() + ":*");
	        }

			ForestryFarmManager.register();
	        ForestryRecipeManager.register();
    	}
    }
    
    @Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getComment() {
		return "Multifarm, squeezer and backpack support.";
	}

}
