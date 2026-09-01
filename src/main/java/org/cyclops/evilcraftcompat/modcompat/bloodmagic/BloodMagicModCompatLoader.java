package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import net.minecraftforge.common.MinecraftForge;
import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

/**
 * @author rubensworks
 */
public class BloodMagicModCompatLoader implements ICompatInitializer {
    @Override
    public void initialize() {
        ClientSoulNetworkHandler.reset();
        EvilCraftCompat._instance.getConfigHandler().addConfigurable(new BoundBloodDropConfig());

        MinecraftForge.EVENT_BUS.register(ClientSoulNetworkHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(new VengeanceSpiritWillDropper());
        TranquilityHandlers.register();
    }
}
