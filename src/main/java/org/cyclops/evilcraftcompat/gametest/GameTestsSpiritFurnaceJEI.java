package org.cyclops.evilcraftcompat.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.cyclops.cyclopscore.gametest.GameTest;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace.SpiritFurnaceRecipeJEI;

import java.util.List;

/**
 * @author rubensworks
 */
public class GameTestsSpiritFurnaceJEI {

    @GameTest(template = Reference.MOD_ID + ":empty")
    public void testMobDropsIncludeAllEntriesOfNestedLootTables(GameTestHelper helper) {
        // The guardian loot table contains a nested reference to the fishing loot table, guarded by a random chance condition.
        // All fish from that nested table must be included, independent of random chance.
        Guardian guardian = EntityType.GUARDIAN.create(helper.getLevel(), EntitySpawnReason.TRIGGERED);

        List<ItemStack> drops = SpiritFurnaceRecipeJEI.getMobDrops(EntityType.GUARDIAN, guardian, helper.getLevel());

        for (Item item : List.of(Items.PRISMARINE_SHARD, Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH)) {
            helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(item)), "Drops do not contain " + item + ": " + drops);
        }
        helper.succeed();
    }

}
