package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraftcompat.Reference;

/**
 * Compatibility plugin for Blood Magic.
 * @author rubensworks
 *
 */
public class BloodMagicModCompat implements IModCompat {

    @Override
    public String getId() {
        return Reference.MOD_BLOODMAGIC;
    }

    @Override
    public boolean isEnabledDefault() {
        return false;
    }

    @Override
    public String getComment() {
        return "Bound Blood Drop item to directly drain Blood from your soul network.";
    }

    @Override
    public ICompatInitializer createInitializer() {
        return new BloodMagicModCompatLoader();
    }

}
