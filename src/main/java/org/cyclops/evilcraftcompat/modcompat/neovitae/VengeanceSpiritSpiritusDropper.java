package org.cyclops.evilcraftcompat.modcompat.neovitae;

import com.breakinblocks.neovitae.common.item.NVItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.cyclops.evilcraft.ExtendedDamageSources;
import org.cyclops.evilcraft.entity.monster.EntityVengeanceSpirit;

/**
 * Spiritus (formerly known as demon will) will be dropped when Vengeance Spirits are killed.
 * @author rubensworks
 */
public class VengeanceSpiritSpiritusDropper {

    /**
     * The maximum amount of raw spiritus that can be dropped by a single spirit.
     */
    public static final double MAX_SPIRITUS = 20;

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        LivingEntity attackedEntity = event.getEntity();
        if (attackedEntity instanceof EntityVengeanceSpirit && event.getSource().is(ExtendedDamageSources.DAMAGE_TYPE_VENGEANCE_BEAM)) {
            double amountOfSouls = attackedEntity.level().random.nextDouble() * MAX_SPIRITUS;
            ItemStack soulStack = NVItems.MONSTER_SOUL_RAW.get().createSpiritus(amountOfSouls);
            event.getDrops().add(new ItemEntity(attackedEntity.level(), attackedEntity.getX(), attackedEntity.getY(), attackedEntity.getZ(), soulStack));
        }
    }

}
