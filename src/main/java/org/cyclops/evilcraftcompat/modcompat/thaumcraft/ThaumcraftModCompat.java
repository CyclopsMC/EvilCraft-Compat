package org.cyclops.evilcraftcompat.modcompat.thaumcraft;

import net.minecraftforge.common.MinecraftForge;
import org.cyclops.cyclopscore.config.ConfigHandler;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraftcompat.EvilCraftCompat;
import org.cyclops.evilcraftcompat.Reference;

/**
 * Compatibility plugin for Thaumcraft.
 * @author rubensworks
 *
 */
public class ThaumcraftModCompat implements IModCompat {

    @Override
    public String getModID() {
        return Reference.MOD_THAUMCRAFT;
    }

    @Override
    public void onInit(Step step) {
        if(step == Step.PREINIT) {
            ConfigHandler configs = EvilCraftCompat._instance.getConfigHandler();
            configs.add(new VeinedScribingToolsConfig());
        } else if(step == Step.INIT) {
            MinecraftForge.EVENT_BUS.register(new EvilCraftThaumcraftAspects());
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Adds Thaumcraft aspects to EvilCraft items and blocks, Veined Scribing Tools and extra Loot Bag items.";
    }

}
