package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.cyclopscore.helper.WorldHelpers;
import org.cyclops.evilcraftcompat.EvilCraftCompat;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A client-side cache for the soul network contents.
 * @author rubensworks
 */
public class ClientSoulNetworkHandler {

    private static ClientSoulNetworkHandler _instance = null;
    private Map<String, Integer> PLAYER_CONTENTS_CACHE = Maps.newHashMap();
    private Map<String, Integer> PLAYER_MAX_CACHE = Maps.newHashMap();
    private final Set<String> UPDATE_PLAYERS = Sets.newHashSet();

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
    public static ClientSoulNetworkHandler getInstance() {
        if (_instance == null) {
            _instance = new ClientSoulNetworkHandler();
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
        if (MinecraftHelpers.isClientSideThread()) {
            Integer ret = PLAYER_CONTENTS_CACHE.get(uuid.toString());
            if (ret == null) {
                EvilCraftCompat._instance.getPacketHandler().sendToServer(new RequestSoulNetworkUpdatesPacket(uuid.toString()));
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
     * Get the cached max essence.
     * Clients will automatically send a request packet to the server to stay updated for this player's essence.
     * Servers will always delegate to the SoulNetworkHandler.
     * @param uuid The owner uuid.
     * @return The max essence.
     */
    public int getMaxEssence(UUID uuid) {
        if (MinecraftHelpers.isClientSideThread()) {
            Integer ret = PLAYER_MAX_CACHE.get(uuid.toString());
            if (ret == null) {
                EvilCraftCompat._instance.getPacketHandler().sendToServer(new RequestSoulNetworkUpdatesPacket(uuid.toString()));
                return 0;
            }
            return ret;
        } else {
            return NetworkHelper.getMaximumForTier(NetworkHelper.getSoulNetwork(uuid).getOrbTier());
        }
    }

    /**
     * Set the max essence for the player.
     * @param uuid The player uuid.
     * @param currentEssence The max essence.
     */
    public void setMaxEssence(String uuid, int currentEssence) {
        PLAYER_MAX_CACHE.put(uuid, currentEssence);
    }

    /**
     * Add the given player to the server list of essence watchers.
     * @param player The player.
     * @param uuid Player uuid
     */
    public void addUpdatePlayer(ServerPlayer player, String uuid) {
        UPDATE_PLAYERS.add(uuid);
        EvilCraftCompat._instance.getPacketHandler().sendToPlayer(
                new UpdateSoulNetworkCachePacket(PLAYER_CONTENTS_CACHE, PLAYER_MAX_CACHE), player);
    }

    /**
     * When a server tick event is received.
     * @param event The received event.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START
                && ServerLifecycleHooks.getCurrentServer() != null
                && WorldHelpers.efficientTick(
                        ServerLifecycleHooks.getCurrentServer().overworld(),
                        BoundBloodDropConfig.maxUpdateTicks)) {
            Map<String, Integer> toSend = Maps.newHashMap();
            Map<String, Integer> toSendMax = Maps.newHashMap();
            for (String uuid : UPDATE_PLAYERS) {
                SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(uuid);
                int essence = soulNetwork.getCurrentEssence();
                int max = NetworkHelper.getMaximumForTier(soulNetwork.getOrbTier());
                Integer found = PLAYER_CONTENTS_CACHE.get(uuid);
                if (found == null || essence != found) {
                    toSend.put(uuid, essence);
                    setCurrentEssence(uuid, essence);
                }
                Integer foundMax = PLAYER_MAX_CACHE.get(uuid);
                if (foundMax == null || max != foundMax) {
                    toSendMax.put(uuid, max);
                    setMaxEssence(uuid, max);
                }
            }
            sendUpdates(toSend, toSendMax);
        }
    }

    private void sendUpdates(Map<String, Integer> toSendContents, Map<String, Integer> toSendMax) {
        if (!toSendContents.isEmpty() || !toSendMax.isEmpty()) {
            EvilCraftCompat._instance.getPacketHandler().sendToAll(new UpdateSoulNetworkCachePacket(toSendContents, toSendMax));
        }
    }

    /**
     * When a level is loaded.
     * @param event the event.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel().isClientSide()) {
            reset();
        }
    }

}
