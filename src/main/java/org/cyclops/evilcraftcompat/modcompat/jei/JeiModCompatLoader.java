package org.cyclops.evilcraftcompat.modcompat.jei;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.cyclops.cyclopscore.init.IModBase;
import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraftcompat.EvilCraftCompat;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace.SpiritFurnaceRecipeJEI;
import org.cyclops.evilcraftcompat.network.packet.JeiSpiritFurnaceRecipePacket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rubensworks
 */
public class JeiModCompatLoader implements ICompatInitializer {

    private static List<SpiritFurnaceRecipeJEI> SPIRIT_FURNACE_RECIPES;

    @Override
    public void initialize(IModBase mod) {
        mod.getModHelpers().getMinecraftHelpers().sendRecipesToClients(() -> List.of(
                RegistryEntries.RECIPETYPE_BLOOD_INFUSER.get(),
                RegistryEntries.RECIPETYPE_ENVIRONMENTAL_ACCUMULATOR.get()
        ));
        NeoForge.EVENT_BUS.addListener(this::onPlayerJoin);
    }

    private void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // Send loot tables from server to client.
        List<SpiritFurnaceRecipeJEI> recipes = SpiritFurnaceRecipeJEI.generateServerRecipes();
        for (SpiritFurnaceRecipeJEI recipe : recipes) {
            try {
                EvilCraftCompat._instance.getPacketHandler().sendToPlayer(new JeiSpiritFurnaceRecipePacket(recipe, recipes.size()), (ServerPlayer) event.getEntity());
            } catch (UnsupportedOperationException e) {
                // Can occur during game testing
                e.printStackTrace();
            }
        }
    }

    public static void receiveSpiritFurnaceRecipeOnClient(SpiritFurnaceRecipeJEI recipe, int totalCount) {
        if (SPIRIT_FURNACE_RECIPES == null) {
            SPIRIT_FURNACE_RECIPES = new ArrayList<>();
        }
        SPIRIT_FURNACE_RECIPES.add(recipe);
        if (SPIRIT_FURNACE_RECIPES.size() == totalCount) {
            if (JEIEvilCraftConfig.SPIRIT_FURNACE_RECIPES_REGISTRAR != null) {
                JEIEvilCraftConfig.SPIRIT_FURNACE_RECIPES_REGISTRAR.accept(SPIRIT_FURNACE_RECIPES);
            } else {
                JEIEvilCraftConfig.PENDING_SPIRIT_FURNACE_RECIPES = SPIRIT_FURNACE_RECIPES;
            }
            SPIRIT_FURNACE_RECIPES = null;
        }
    }
}
