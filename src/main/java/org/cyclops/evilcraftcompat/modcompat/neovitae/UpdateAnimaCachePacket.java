package org.cyclops.evilcraftcompat.modcompat.neovitae;

import com.google.common.collect.Maps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.evilcraftcompat.Reference;

import java.util.Map;

/**
 * Update the anima cache at the clients originating from the server.
 *
 * @author rubensworks
 *
 */
public class UpdateAnimaCachePacket extends PacketCodec<UpdateAnimaCachePacket> {

    public static final Type<UpdateAnimaCachePacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "neovitae_update_anima_cache"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateAnimaCachePacket> CODEC = getCodec(UpdateAnimaCachePacket::new);

    @CodecField
    private Map<String, Integer> playerEssences = Maps.newHashMap();

    /**
     * Creates a packet with no content
     */
    public UpdateAnimaCachePacket() {
        super(ID);
    }

    /**
     * Creates a packet which contains the player uuids and amount of essence.
     * @param playerEssences A map of players with their essence.
     */
    public UpdateAnimaCachePacket(Map<String, Integer> playerEssences) {
        this();
        this.playerEssences = playerEssences;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void actionClient(Level world, Player player) {
        for (Map.Entry<String, Integer> entry : this.playerEssences.entrySet()) {
            ClientAnimaHandler.getInstance().setCurrentEssence(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void actionServer(Level world, ServerPlayer player) {
        // Do nothing
    }
}
