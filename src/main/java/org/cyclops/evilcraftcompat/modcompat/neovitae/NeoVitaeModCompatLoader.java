package org.cyclops.evilcraftcompat.modcompat.neovitae;

import net.neoforged.neoforge.common.NeoForge;
import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

/**
 * @author rubensworks
 */
public class NeoVitaeModCompatLoader implements ICompatInitializer {
    @Override
    public void initialize() {
        ClientAnimaHandler.reset();
        EvilCraftCompat._instance.getConfigHandler().addConfigurable(new BoundBloodDropConfig());

        NeoForge.EVENT_BUS.register(ClientAnimaHandler.getInstance());
        NeoForge.EVENT_BUS.register(new VengeanceSpiritSpiritusDropper());
    }
}
