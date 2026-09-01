package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.cyclops.evilcraft.ExtendedDamageSources;
import org.cyclops.evilcraft.entity.monster.EntityVengeanceSpirit;
import wayoftime.bloodmagic.api.compat.IDemonWill;
import wayoftime.bloodmagic.common.item.BloodMagicItems;

/**
 * Will's will be dropped when Vengeance Spirits are killed.
 * @author rubensworks
 */
public class VengeanceSpiritWillDropper {

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        LivingEntity attackedEntity = event.getEntity();
        if (attackedEntity instanceof EntityVengeanceSpirit && event.getSource().is(ExtendedDamageSources.DAMAGE_TYPE_VENGEANCE_BEAM)) {
            double amountOfSouls = attackedEntity.level().random.nextDouble() * 20;
            ItemStack soulStack = ((IDemonWill) BloodMagicItems.MONSTER_SOUL_RAW.get()).createWill(amountOfSouls);
            event.getDrops().add(new ItemEntity(attackedEntity.level(), attackedEntity.getX(), attackedEntity.getY(), attackedEntity.getZ(), soulStack));
        }
    }

}
