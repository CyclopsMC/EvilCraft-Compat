package org.cyclops.evilcraftcompat.modcompat.minetweaker;

import minetweaker.MineTweakerAPI;
import org.cyclops.evilcraftcompat.modcompat.minetweaker.handlers.BloodInfuserHandler;
import org.cyclops.evilcraftcompat.modcompat.minetweaker.handlers.EnvironmentalAccumulatorHandler;

/**
 * @author rubensworks
 */
public class MineTweaker {

    public static void register() {
        MineTweakerAPI.registerClass(BloodInfuserHandler.class);
        MineTweakerAPI.registerClass(EnvironmentalAccumulatorHandler.class);
    }

}
