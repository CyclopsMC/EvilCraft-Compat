package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import com.google.common.collect.Maps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;

import java.util.Map;

/**
 * Update the soul network cache at the clients originating from the server.
 *
 * @author rubensworks
 *
 */
public class UpdateSoulNetworkCachePacket extends PacketCodec {

    @CodecField
    private Map<String, Integer> playerEssences = Maps.newHashMap();
    @CodecField
    private Map<String, Integer> playerEssencesMax = Maps.newHashMap();

    /**
     * Creates a packet with no content
     */
    public UpdateSoulNetworkCachePacket() {

    }

    @Override
    public boolean isAsync() {
        return true;
    }

    /**
     * Creates a packet which contains the player names and amount of essence.
     * @param playerEssences A map of players with their essence.
     * @param playerEssencesMax A map of players with their max essence.
     */
    public UpdateSoulNetworkCachePacket(Map<String, Integer> playerEssences, Map<String, Integer> playerEssencesMax) {
        this.playerEssences = playerEssences;
        this.playerEssencesMax = playerEssencesMax;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void actionClient(Level world, Player player) {
        for (Map.Entry<String, Integer> entry : playerEssences.entrySet()) {
            ClientSoulNetworkHandler.getInstance().setCurrentEssence(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : playerEssencesMax.entrySet()) {
            ClientSoulNetworkHandler.getInstance().setMaxEssence(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void actionServer(Level world, ServerPlayer player) {
        // Do nothing
    }
}
