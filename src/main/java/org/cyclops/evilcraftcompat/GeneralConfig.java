package org.cyclops.evilcraftcompat;

import org.cyclops.cyclopscore.config.extendedconfig.DummyConfigCommon;

/**
 * A config with general options for this mod.
 * @author rubensworks
 *
 */
public class GeneralConfig extends DummyConfigCommon<EvilCraftCompat> {

    public GeneralConfig() {
        super(EvilCraftCompat._instance, "general");
    }

}
