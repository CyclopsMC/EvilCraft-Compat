package org.cyclops.evilcraftcompat.modcompat.neovitae;

import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraftcompat.Reference;

/**
 * Compatibility plugin for Neo Vitae (the successor of Blood Magic).
 * @author rubensworks
 *
 */
public class NeoVitaeModCompat implements IModCompat {

    @Override
    public String getId() {
        return Reference.MOD_NEOVITAE;
    }

    @Override
    public boolean isEnabledDefault() {
        return true;
    }

    @Override
    public String getComment() {
        return "Bound Blood Drop item to directly drain Blood from your anima (soul network).";
    }

    @Override
    public ICompatInitializer createInitializer() {
        return new NeoVitaeModCompatLoader();
    }

}
