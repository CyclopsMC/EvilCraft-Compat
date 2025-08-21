package org.cyclops.evilcraftcompat.modcompat.jei;

import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraftcompat.Reference;

/**
 * Mod compat for the JEI mod.
 * @author rubensworks
 *
 */
public class JeiModCompat implements IModCompat {

    @Override
    public String getId() {
        return Reference.MOD_JEI;
    }

    @Override
    public boolean isEnabledDefault() {
        return true;
    }

    @Override
    public String getComment() {
        return "JEI integration.";
    }

    @Override
    public ICompatInitializer createInitializer() {
        return new JeiModCompatLoader();
    }

}
