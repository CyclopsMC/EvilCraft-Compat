package org.cyclops.evilcraftcompat.modcompat.neovitae;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.cyclopscore.helper.WorldHelpers;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A client-side cache for the anima (soul network) contents.
 * @author rubensworks
 */
public class ClientAnimaHandler {

    private static ClientAnimaHandler _instance = null;
    private Map<String, Integer> playerContentsCache = Maps.newHashMap();
    private final Set<String> updatePlayers = Sets.newHashSet();

    private ClientAnimaHandler() {

    }

    /**
     * Reset the instance.
     */
    public static void reset() {
        getInstance().playerContentsCache = Maps.newHashMap();
    }

    /**
     * @return The unique instance.
     */
    public static ClientAnimaHandler getInstance() {
        if (_instance == null) {
            _instance = new ClientAnimaHandler();
        }
        return _instance;
    }

    /**
     * Get the cached current essence.
     * Clients will automatically send a request packet to the server to stay updated for this player's essence.
     * Servers will always delegate to the anima itself.
     * @param uuid The owner uuid.
     * @return The essence.
     */
    public int getCurrentEssence(UUID uuid) {
        if (MinecraftHelpers.isClientSideThread()) {
            Integer ret = this.playerContentsCache.get(uuid.toString());
            if (ret == null) {
                EvilCraftCompat._instance.getPacketHandler().sendToServer(new RequestAnimaUpdatesPacket(uuid.toString()));
                return 0;
            }
            return ret;
        } else {
            return AnimaHelpers.getCurrentEssence(uuid);
        }
    }

    /**
     * Set the essence for the player.
     * @param uuid The player uuid.
     * @param currentEssence The essence.
     */
    public void setCurrentEssence(String uuid, int currentEssence) {
        this.playerContentsCache.put(uuid, currentEssence);
    }

    /**
     * Add the given player to the server list of essence watchers.
     * @param player The player.
     * @param uuid Player uuid
     */
    public void addUpdatePlayer(ServerPlayer player, String uuid) {
        this.updatePlayers.add(uuid);
        EvilCraftCompat._instance.getPacketHandler().sendToPlayer(
                new UpdateAnimaCachePacket(Maps.newHashMap(this.playerContentsCache)), player);
    }

    /**
     * When a server tick event is received.
     * @param event The received event.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onServerTick(ServerTickEvent.Pre event) {
        if (ServerLifecycleHooks.getCurrentServer() != null
                && WorldHelpers.efficientTick(
                        ServerLifecycleHooks.getCurrentServer().overworld(),
                        BoundBloodDropConfig.maxUpdateTicks)) {
            Map<String, Integer> toSend = Maps.newHashMap();
            for (String uuid : this.updatePlayers) {
                int essence = AnimaHelpers.getCurrentEssence(UUID.fromString(uuid));
                Integer found = this.playerContentsCache.get(uuid);
                if (found == null || essence != found) {
                    toSend.put(uuid, essence);
                    setCurrentEssence(uuid, essence);
                }
            }
            sendUpdates(toSend);
        }
    }

    private void sendUpdates(Map<String, Integer> toSendContents) {
        if (!toSendContents.isEmpty()) {
            EvilCraftCompat._instance.getPacketHandler().sendToAll(new UpdateAnimaCachePacket(toSendContents));
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
