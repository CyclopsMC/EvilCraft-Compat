package org.cyclops.evilcraftcompat.modcompat.tconstruct;

import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraftcompat.modcompat.tconstruct.TConstructRecipeManager;

/**
 * Compatibility plugin for Tinkers' Construct.
 * @author rubensworks
 *
 */
public class TConstructModCompat implements IModCompat {

    @Override
    public String getModID() {
        return Reference.MOD_TCONSTRUCT;
    }

    @Override
    public void onInit(Step step) {
        if (step == Step.POSTINIT) {
            TConstructRecipeManager.register();
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Blood chest repair support for tinker tools.";
    }

}
