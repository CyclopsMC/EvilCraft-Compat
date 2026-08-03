package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;

/**
 * Packet from client to server to register a player for soul network updates.
 *
 * @author rubensworks
 *
 */
public class RequestSoulNetworkUpdatesPacket extends PacketCodec {

    @CodecField
    private String uuid;

    /**
     * Creates a packet with no content
     */
    public RequestSoulNetworkUpdatesPacket() {

    }

    @Override
    public boolean isAsync() {
        return true;
    }

    /**
     * Creates a packet which contains the player uuid.
     * @param uuid The player uuid.
     */
    public RequestSoulNetworkUpdatesPacket(String uuid) {
        this.uuid = uuid;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void actionClient(Level world, Player player) {
        // Do nothing
    }

    @Override
    public void actionServer(Level world, ServerPlayer player) {
        ClientSoulNetworkHandler.getInstance().addUpdatePlayer(player, this.uuid);
    }
}
