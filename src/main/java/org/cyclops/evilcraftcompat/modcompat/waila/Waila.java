package org.cyclops.evilcraftcompat.modcompat.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import org.cyclops.cyclopscore.config.configurable.IConfigurable;
import org.cyclops.cyclopscore.helper.L10NHelpers;
import org.cyclops.cyclopscore.tileentity.TankInventoryTileEntity;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.block.*;
import org.cyclops.evilcraft.entity.item.EntityBroom;
import org.cyclops.evilcraftcompat.modcompat.waila.BroomInfoDataProvider;
import org.cyclops.evilcraftcompat.modcompat.waila.TankDataProvider;
import org.cyclops.evilcraft.tileentity.TileBoxOfEternalClosure;

/**
 * Waila support class.
 * @author rubensworks
 *
 */
public class Waila {
    
    /**
     * Waila callback.
     * @param registrar The Waila registrar.
     */
    public static void callbackRegister(IWailaRegistrar registrar){
        registrar.addConfig(Reference.MOD_NAME, getTankConfigID(), L10NHelpers.localize("gui.waila.tankConfig"));
        registrar.addConfig(Reference.MOD_NAME, getInnerBlockConfigID(), L10NHelpers.localize("gui.waila.innerBlocksConfig"));
        registrar.addConfig(Reference.MOD_NAME, getBoxOfEternalClosureConfigID(), L10NHelpers.localize("gui.waila.boxOfEternalClosureConfig"));
        registrar.addConfig(Reference.MOD_NAME, getBlockInfoConfigID(), L10NHelpers.localize("gui.waila.blockInfoConfig"));
        registrar.addConfig(Reference.MOD_NAME, getBroomInfoConfigID(), L10NHelpers.localize("gui.waila.broomInfoConfig"));
        
        // Tanks
        registrar.registerBodyProvider(new TankDataProvider(), TankInventoryTileEntity.class);
        
        // Inner blocks
        if(Configs.isEnabled(BloodStainedBlockConfig.class))
            registrar.registerStackProvider(new InnerBlockDataProvider(), BloodStainedBlock.class);
        if(Configs.isEnabled(NetherfishSpawnConfig.class))
            registrar.registerStackProvider(new InnerBlockDataProvider(), NetherfishSpawn.class);
        
        // Box of Eternal Closure
        if(Configs.isEnabled(BoxOfEternalClosureConfig.class)) {
            BoxOfEternalClosureDataProvider dataProvider = new BoxOfEternalClosureDataProvider();
            registrar.registerNBTProvider(dataProvider, TileBoxOfEternalClosure.class);
            registrar.registerBodyProvider(dataProvider, TileBoxOfEternalClosure.class);
        }

        // Generic blockState info
        registrar.registerBodyProvider(new GenericBlockInfoDataProvider(), IConfigurable.class);

        // Broom info
        registrar.registerBodyProvider(new BroomInfoDataProvider(), EntityBroom.class);
    }
    
    /**
     * Config ID.
     * @return The config ID.
     */
    public static String getTankConfigID() {
        return Reference.MOD_NAME + ".tank";
    }
    
    /**
     * Config ID.
     * @return The config ID.
     */
    public static String getInnerBlockConfigID() {
        return Reference.MOD_NAME + ".innerBlock";
    }
    
    /**
     * Config ID.
     * @return The config ID.
     */
    public static String getBoxOfEternalClosureConfigID() {
        return Reference.MOD_NAME + ".boxOfEternalClosure";
    }
    
    /**
     * Config ID.
     * @return The config ID.
     */
    public static String getBlockInfoConfigID() {
        return Reference.MOD_NAME + ".genericBlockInfo";
    }

    /**
     * Config ID.
     * @return The config ID.
     */
    public static String getBroomInfoConfigID() {
        return Reference.MOD_NAME + ".broomInfo";
    }
    
}
