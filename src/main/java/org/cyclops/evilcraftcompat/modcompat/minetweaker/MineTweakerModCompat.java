package org.cyclops.evilcraftcompat.modcompat.minetweaker;

import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraftcompat.modcompat.minetweaker.MineTweaker;

/**
 * Config for the JEI integration of this mod.
 * @author rubensworks
 *
 */
public class MineTweakerModCompat implements IModCompat {

    @Override
    public void onInit(Step initStep) {
        if(initStep == Step.INIT) {
            MineTweaker.register();
        }
    }

    @Override
    public String getModID() {
        return Reference.MOD_MINETWEAKER;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Integration for EvilCraft recipes.";
    }

}
