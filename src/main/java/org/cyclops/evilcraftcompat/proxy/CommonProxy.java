package org.cyclops.evilcraftcompat.proxy;

import org.cyclops.cyclopscore.init.ModBaseNeoForge;
import org.cyclops.cyclopscore.network.IPacketHandler;
import org.cyclops.cyclopscore.proxy.CommonProxyComponent;
import org.cyclops.evilcraftcompat.EvilCraftCompat;
import org.cyclops.evilcraftcompat.network.packet.JeiSpiritFurnaceRecipePacket;

/**
 * Proxy for server and client side.
 * @author rubensworks
 *
 */
public class CommonProxy extends CommonProxyComponent {

    @Override
    public ModBaseNeoForge<?> getMod() {
        return EvilCraftCompat._instance;
    }

    @Override
    public void registerPackets(IPacketHandler packetHandler) {
        super.registerPackets(packetHandler);
        // JEI
        packetHandler.register(JeiSpiritFurnaceRecipePacket.class, JeiSpiritFurnaceRecipePacket.ID, JeiSpiritFurnaceRecipePacket.CODEC);

        // Blood Magic
//        packetHandler.register(UpdateSoulNetworkCachePacket.class);
//        packetHandler.register(RequestSoulNetworkUpdatesPacket.class);
    }
}
