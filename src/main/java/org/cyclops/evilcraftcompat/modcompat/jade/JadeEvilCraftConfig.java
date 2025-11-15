package org.cyclops.evilcraftcompat.modcompat.jade;

import org.cyclops.evilcraft.block.BlockBoxOfEternalClosure;
import org.cyclops.evilcraft.entity.item.EntityBroom;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

/**
 * Waila support class.
 * @author rubensworks
 *
 */
@WailaPlugin
public class JadeEvilCraftConfig implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registrar) {
        // Broom info
        registrar.registerEntityComponent(new BroomInfoDataProvider(), EntityBroom.class);

        // Box of Eternal Closure
        registrar.registerBlockComponent(new BoxOfEternalClosureDataProvider(), BlockBoxOfEternalClosure.class);
    }

}
