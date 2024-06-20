package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.entity.player.EntityPlayerMP;
import net.neoforged.neoforge.event.world.WorldEvent;
import net.neoforged.fml.common.FMLCommonHandler;
import net.neoforged.fml.common.eventhandler.EventPriority;
import net.neoforged.fml.common.eventhandler.SubscribeEvent;
import net.neoforged.fml.common.gameevent.TickEvent.Phase;
import net.neoforged.fml.common.gameevent.TickEvent.ServerTickEvent;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.cyclopscore.helper.WorldHelpers;
import org.cyclops.evilcraft.EvilCraft;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A client-side cache for the soul network contents.
 * @author rubensworks
 */
public class ClientSoulNetworkHandler {

    private static org.cyclops.evilcraftcompat.modcompat.bloodmagic.ClientSoulNetworkHandler _instance = null;
    private Map<String, Integer> PLAYER_CONTENTS_CACHE = Maps.newHashMap();
    private Map<String, Integer> PLAYER_MAX_CACHE = Maps.newHashMap();
    private Set<String> UPDATE_PLAYERS = Sets.newHashSet();

    private ClientSoulNetworkHandler() {

    }

    /**
     * Reset the instance.
     */
    public static void reset() {
        getInstance().PLAYER_CONTENTS_CACHE = Maps.newHashMap();
        getInstance().PLAYER_MAX_CACHE = Maps.newHashMap();
    }

    /**
     * @return The unique instance.
     */
    public static org.cyclops.evilcraftcompat.modcompat.bloodmagic.ClientSoulNetworkHandler getInstance() {
        if(_instance == null) {
            _instance = new org.cyclops.evilcraftcompat.modcompat.bloodmagic.ClientSoulNetworkHandler();
        }
        return _instance;
    }

    /**
     * Get the cached current essence.
     * Clients will automatically send a request packet to the server to stay updated for this player's essence.
     * Servers will always delegate to the SoulNetworkHandler.
     * @param uuid The owner uuid.
     * @return The essence.
     */
    public int getCurrentEssence(UUID uuid) {
        if(MinecraftHelpers.isClientSide()) {
            Integer ret = PLAYER_CONTENTS_CACHE.get(uuid.toString());
            if(ret == null) {
                EvilCraft._instance.getPacketHandler().sendToServer(new RequestSoulNetworkUpdatesPacket(uuid.toString()));
                return 0;
            }
            return ret;
        } else {
            return NetworkHelper.getSoulNetwork(uuid).getCurrentEssence();
        }
    }

    /**
     * Set the essence for the player.
     * @param uuid The player uuid.
     * @param currentEssence The essence.
     */
    public void setCurrentEssence(String uuid, int currentEssence) {
        PLAYER_CONTENTS_CACHE.put(uuid, currentEssence);
    }

    /**
     * Get the cached current essence.
     * Clients will automatically send a request packet to the server to stay updated for this player's essence.
     * Servers will always delegate to the SoulNetworkHandler.
     * @param uuid The owner uuid.
     * @return The essence.
     */
    public int getMaxEssence(UUID uuid) {
        if(MinecraftHelpers.isClientSide()) {
            Integer ret = PLAYER_MAX_CACHE.get(uuid.toString());
            if(ret == null) {
                EvilCraft._instance.getPacketHandler().sendToServer(new RequestSoulNetworkUpdatesPacket(uuid.toString()));
                return 0;
            }
            return ret;
        } else {
            return NetworkHelper.getMaximumForTier(NetworkHelper.getSoulNetwork(uuid).getOrbTier());
        }
    }

    /**
     * Set the essence for the player.
     * @param uuid The player uuid.
     * @param currentEssence The essence.
     */
    public void setMaxEssence(String uuid, int currentEssence) {
        PLAYER_MAX_CACHE.put(uuid, currentEssence);
    }

    /**
     * Add the given player to the server list of essence watchers.
     * @param player The player.
     * @param uuid Player uuid
     */
    //@SideOnly(Side.SERVER)
    public void addUpdatePlayer(EntityPlayerMP player, String uuid) {
        UPDATE_PLAYERS.add(uuid);
        EvilCraft._instance.getPacketHandler().sendToPlayer(
                new UpdateSoulNetworkCachePacket(PLAYER_CONTENTS_CACHE, PLAYER_MAX_CACHE), player);
    }

    /**
     * When a server tick event is received.
     * @param event The received event.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onServerTick(ServerTickEvent event) {
        if(event.phase == Phase.START && WorldHelpers.efficientTick(
                FMLCommonHandler.instance().getMinecraftServerInstance().worlds[0],
                BoundBloodDropConfig.maxUpdateTicks)) {
            Map<String, Integer> toSend = Maps.newHashMap();
            Map<String, Integer> toSendMax = Maps.newHashMap();
            for(String uuid : UPDATE_PLAYERS) {
                SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(uuid);
                int essence = soulNetwork.getCurrentEssence();
                int max = NetworkHelper.getMaximumForTier(soulNetwork.getOrbTier());
                Integer found = PLAYER_CONTENTS_CACHE.get(uuid);
                if(found == null || essence != found) {
                    toSend.put(uuid, essence);
                    setCurrentEssence(uuid, essence);
                }
                Integer foundMax = PLAYER_MAX_CACHE.get(uuid);
                if(foundMax == null || max != foundMax) {
                    toSendMax.put(uuid, max);
                    setMaxEssence(uuid, max);
                }
            }
            sendUpdates(toSend, toSendMax);
        }
    }

    private void sendUpdates(Map<String, Integer> toSendContents, Map<String, Integer> toSendMax) {
        if (!toSendContents.isEmpty() || !toSendMax.isEmpty()) {
            EvilCraft._instance.getPacketHandler().sendToAll(new UpdateSoulNetworkCachePacket(toSendContents, toSendMax));
        }
    }

    /**
     * When a world is loaded.
     * @param event the event.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldEvent.Load event) {
        if(event.getWorld().isRemote) {
            reset();
        }
    }

}
