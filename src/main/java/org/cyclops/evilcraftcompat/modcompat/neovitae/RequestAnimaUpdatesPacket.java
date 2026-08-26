package org.cyclops.evilcraftcompat.modcompat.neovitae;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.evilcraftcompat.Reference;

/**
 * Packet from client to server to register a player for anima updates.
 *
 * @author rubensworks
 *
 */
public class RequestAnimaUpdatesPacket extends PacketCodec<RequestAnimaUpdatesPacket> {

    public static final Type<RequestAnimaUpdatesPacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "neovitae_request_anima_updates"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestAnimaUpdatesPacket> CODEC = getCodec(RequestAnimaUpdatesPacket::new);

    @CodecField
    private String uuid;

    /**
     * Creates a packet with no content
     */
    public RequestAnimaUpdatesPacket() {
        super(ID);
    }

    /**
     * Creates a packet which contains the player uuid.
     * @param uuid The player uuid.
     */
    public RequestAnimaUpdatesPacket(String uuid) {
        this();
        this.uuid = uuid;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void actionClient(Level world, Player player) {
        // Do nothing
    }

    @Override
    public void actionServer(Level world, ServerPlayer player) {
        ClientAnimaHandler.getInstance().addUpdatePlayer(player, this.uuid);
    }
}
