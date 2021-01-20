package org.cyclops.evilcraftcompat.modcompat.curios;

import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraftcompat.Reference;

/**
 * @author rubensworks
 */
public class CuriosCompat implements IModCompat {
    @Override
    public String getId() {
        return Reference.MOD_CURIOS;
    }

    @Override
    public boolean isEnabledDefault() {
        return true;
    }

    @Override
    public String getComment() {
        return "Curios slots types";
    }

    @Override
    public ICompatInitializer createInitializer() {
        return new CuriosCompatLoader();
    }
}
