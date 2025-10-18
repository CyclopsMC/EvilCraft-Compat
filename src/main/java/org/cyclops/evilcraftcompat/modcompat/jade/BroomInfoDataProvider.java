package org.cyclops.evilcraftcompat.modcompat.jade;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.entity.item.EntityBroom;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/**
 * Waila data provider for broom info.
 * @author rubensworks
 *
 */
public class BroomInfoDataProvider implements IEntityComponentProvider {

    public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "broom");

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (entityAccessor.getEntity() instanceof EntityBroom entityBroom) {
            ItemStack broomStack = entityBroom.getBroomStack();
            for (Component tooltipLine : broomStack.getTooltipLines(entityAccessor.getPlayer(), TooltipFlag.Default.NORMAL)) {
                tooltip.add(tooltipLine);
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
