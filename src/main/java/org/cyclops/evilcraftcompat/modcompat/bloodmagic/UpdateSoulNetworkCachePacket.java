package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.neoforged.fml.relauncher.Side;
import net.neoforged.fml.relauncher.SideOnly;
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
    @SideOnly(Side.CLIENT)
    public void actionClient(World world, EntityPlayer player) {
        for(Map.Entry<String, Integer> entry : playerEssences.entrySet()) {
            ClientSoulNetworkHandler.getInstance().setCurrentEssence(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String, Integer> entry : playerEssencesMax.entrySet()) {
            ClientSoulNetworkHandler.getInstance().setMaxEssence(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void actionServer(World world, EntityPlayerMP player) {
        // Do nothing
    }
}
