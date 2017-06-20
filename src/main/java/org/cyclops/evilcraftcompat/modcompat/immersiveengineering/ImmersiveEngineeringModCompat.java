package org.cyclops.evilcraftcompat.modcompat.immersiveengineering;

import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraftcompat.modcompat.immersiveengineering.ImmersiveEngineeringRecipeManager;

/**
 * Compatibility plugin for Immersive Engineering.
 * @author runesmacher
 *
 */
public class ImmersiveEngineeringModCompat implements IModCompat {

    @Override
    public String getModID() {
       return Reference.MOD_IMMERSIVEENGINEERING;
    }

    @Override
    public void onInit(Step step) {
        if(step == Step.INIT) {
            ImmersiveEngineeringRecipeManager.register();
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "squeezer support.";
    }

}
