package org.cyclops.evilcraftcompat.modcompat.enderio;

import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraftcompat.Reference;

/**
 * Compatibility plugin for Ender IO.
 * @author rubensworks
 *
 */
public class EnderIOModCompat implements IModCompat {

    @Override
    public String getModID() {
       return Reference.MOD_ENDERIO;
    }

    @Override
    public void onInit(Step step) {
        if(step == Step.INIT) {
            EnderIORecipeManager.register();
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Sagmill recipe for Dark Ore and Dark Gem.";
    }

}
