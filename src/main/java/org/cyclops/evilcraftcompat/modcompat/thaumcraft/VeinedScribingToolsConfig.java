package org.cyclops.evilcraftcompat.modcompat.thaumcraft;

import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.evilcraft.EvilCraft;

/**
 * Config for the {@link org.cyclops.evilcraftcompat.modcompat.thaumcraft.VeinedScribingTools}.
 * @author rubensworks
 *
 */
public class VeinedScribingToolsConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static VeinedScribingToolsConfig _instance;

    /**
     * Make a new instance.
     */
    public VeinedScribingToolsConfig() {
        super(
            EvilCraft._instance,
            true,
            "veined_scribing_tools",
            null,
            VeinedScribingTools.class
        );
    }

    @Override
    public void onRegistered() {
        super.onRegistered();
        // TODO: make this not required anymore
        // This is to make the recipe work, which was registered in EC
        EvilCraft._instance.getConfigHandler().addToConfigDictionary(this);
    }

}
