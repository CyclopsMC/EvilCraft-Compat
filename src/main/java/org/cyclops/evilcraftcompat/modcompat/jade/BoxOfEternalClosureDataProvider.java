package org.cyclops.evilcraftcompat.modcompat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.blockentity.BlockEntityBoxOfEternalClosure;
import org.cyclops.evilcraft.entity.monster.EntityVengeanceSpiritData;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/**
 * Waila data provider for the BOEC.
 * @author rubensworks
 *
 */
public class BoxOfEternalClosureDataProvider implements IBlockComponentProvider {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "box_of_eternal_closure");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof BlockEntityBoxOfEternalClosure blockEntity) {
            // Derived from ItemBlockBoxOfEternalClosure
            Component content = Component.translatable("general." + Reference.MOD_ID + ".info.empty")
                    .withStyle(ChatFormatting.ITALIC);
            if (blockEntity.hasSpirit()) {
                EntityVengeanceSpiritData spiritData = blockEntity.getSpiritData();
                if (spiritData.containsPlayer()) {
                    content = Component.literal(spiritData.getPlayerName());
                } else {
                    EntityType<?> spiritType = spiritData.isSwarm() ? RegistryEntries.ENTITY_VENGEANCE_SPIRIT.get() : spiritData.getInnerEntityType();
                    if (spiritType != null) {
                        content = spiritType.getDescription();
                    }
                }
            }
            tooltip.add(Component.translatable("block.evilcraft.box_of_eternal_closure.info.content")
                    .withStyle(ChatFormatting.LIGHT_PURPLE)
                    .append(content));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

}
