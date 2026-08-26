package org.cyclops.evilcraftcompat.proxy;

import org.cyclops.cyclopscore.init.ModBase;
import org.cyclops.cyclopscore.network.PacketHandler;
import org.cyclops.cyclopscore.proxy.CommonProxyComponent;
import org.cyclops.evilcraftcompat.EvilCraftCompat;
import org.cyclops.evilcraftcompat.modcompat.neovitae.RequestAnimaUpdatesPacket;
import org.cyclops.evilcraftcompat.modcompat.neovitae.UpdateAnimaCachePacket;
import org.cyclops.evilcraftcompat.network.packet.JeiSpiritFurnaceRecipePacket;

/**
 * Proxy for server and client side.
 * @author rubensworks
 *
 */
public class CommonProxy extends CommonProxyComponent {

    @Override
    public ModBase getMod() {
        return EvilCraftCompat._instance;
    }

    @Override
    public void registerPacketHandlers(PacketHandler packetHandler) {
        super.registerPacketHandlers(packetHandler);
        // JEI
        packetHandler.register(JeiSpiritFurnaceRecipePacket.class, JeiSpiritFurnaceRecipePacket.ID, JeiSpiritFurnaceRecipePacket.CODEC);

        // Neo Vitae
        packetHandler.register(UpdateAnimaCachePacket.class, UpdateAnimaCachePacket.ID, UpdateAnimaCachePacket.CODEC);
        packetHandler.register(RequestAnimaUpdatesPacket.class, RequestAnimaUpdatesPacket.ID, RequestAnimaUpdatesPacket.CODEC);
    }
}
