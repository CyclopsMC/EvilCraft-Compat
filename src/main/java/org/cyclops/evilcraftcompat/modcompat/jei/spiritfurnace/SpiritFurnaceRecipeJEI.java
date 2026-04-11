package org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace;

import com.google.common.collect.Lists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.block.BlockSpiritFurnaceConfig;
import org.cyclops.evilcraft.entity.monster.EntityVengeanceSpirit;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritreanimator.SpiritReanimatorRecipeJEI;

import java.util.List;

/**
 * Recipe wrapper for Spirit Furnace recipes
 * @author rubensworks
 */
public class SpiritFurnaceRecipeJEI {

    private final FluidStack inputFluid;
    private final ItemStack inputItem;
    private final List<ItemStack> outputItems;
    private final int duration;

    public SpiritFurnaceRecipeJEI(FluidStack inputFluid, ItemStack inputItem, List<ItemStack> outputItems, int duration) {
        this.inputFluid = inputFluid;
        this.inputItem = inputItem;
        this.outputItems = outputItems;
        this.duration = duration;
    }

    public static SpiritFurnaceRecipeJEI create(EntityType<?> entityType, LivingEntity entity, ServerLevel level) {
        int duration = getRequiredTicks(entity);
        return new SpiritFurnaceRecipeJEI(
                new FluidStack(RegistryEntries.FLUID_BLOOD, duration * BlockSpiritFurnaceConfig.mBPerTick),
                SpiritReanimatorRecipeJEI.getBox(entityType),
                getMobDrops(entityType, entity, level),
                getRequiredTicks(entity)
        );
    }

    public static List<ItemStack> getMobDrops(EntityType<?> entityType, Entity entity, ServerLevel level) {
        List<ItemStack> items = Lists.newArrayList();

        FakePlayer killerEntity = FakePlayerFactory.getMinecraft(level);
        LootParams.Builder lootParamsBuilder = (new LootParams.Builder(level))
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, Vec3.ZERO)
                .withParameter(LootContextParams.ATTACKING_ENTITY, killerEntity)
                .withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, killerEntity)
                .withParameter(LootContextParams.DAMAGE_SOURCE, killerEntity.damageSources().generic())
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, killerEntity);
        LootParams params = lootParamsBuilder.create(LootContextParamSets.ENTITY);
        LootContext context = new LootContext.Builder(params).create(entityType.getDefaultLootTable().map(ResourceKey::identifier));

        entityType.getDefaultLootTable().ifPresent(lootTableKey -> {
            LootTable lootTable = ServerLifecycleHooks.getCurrentServer().reloadableRegistries().getLootTable(lootTableKey);
            for (LootPool pool : lootTable.pools) {
                for (LootPoolEntryContainer entryContainer : pool.entries) {
                    entryContainer.expand(context, entry -> entry.createItemStack(items::add, context));
                }
            }
        });
        return items;
    }

    public static int getRequiredTicks(Entity entity) {
        int requiredTicksBase;
        try {
            // Copied from BoxCookTickAction
            LivingEntity livingEntity = (LivingEntity)entity;
            requiredTicksBase = (int)((livingEntity.getHealth() + (float)livingEntity.getArmorValue()) * (float)BlockSpiritFurnaceConfig.requiredTicksPerHp);
        } catch (RuntimeException var7) {
            requiredTicksBase = 40 * BlockSpiritFurnaceConfig.requiredTicksPerHp;
        }
        return requiredTicksBase;
    }

    public FluidStack getInputFluid() {
        return inputFluid;
    }

    public ItemStack getInputItem() {
        return inputItem;
    }

    public List<ItemStack> getOutputItems() {
        return outputItems;
    }

    public int getDuration() {
        return duration;
    }

    public static void encode(SpiritFurnaceRecipeJEI recipe, RegistryFriendlyByteBuf output) {
        FluidStack.OPTIONAL_STREAM_CODEC.encode(output, recipe.getInputFluid());
        ItemStack.OPTIONAL_STREAM_CODEC.encode(output, recipe.getInputItem());
        output.writeInt(recipe.outputItems.size());
        for (ItemStack outputItem : recipe.outputItems) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(output, outputItem);
        }
        output.writeInt(recipe.duration);
    }

    public static SpiritFurnaceRecipeJEI decode(RegistryFriendlyByteBuf input) {
        FluidStack inputFluid = FluidStack.OPTIONAL_STREAM_CODEC.decode(input);
        ItemStack inputItem = ItemStack.OPTIONAL_STREAM_CODEC.decode(input);
        List<ItemStack> outputItems = Lists.newArrayList();
        int outputItemsCount = input.readInt();
        for (int i = 0; i < outputItemsCount; ++i) {
            outputItems.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(input));
        }
        int duration = input.readInt();
        return new SpiritFurnaceRecipeJEI(inputFluid, inputItem, outputItems, duration);
    }

    public static List<SpiritFurnaceRecipeJEI> generateServerRecipes() {
        ServerLevel level = ServerLifecycleHooks.getCurrentServer().overworld();
        List<SpiritFurnaceRecipeJEI> recipes = Lists.newArrayList();
        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            try {
                Entity entity = entityType.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
                if (entity instanceof LivingEntity livingEntity && EntityVengeanceSpirit.canSustain(livingEntity)) {
                    recipes.add(SpiritFurnaceRecipeJEI.create(entityType, livingEntity, level));
                }
            } catch (RuntimeException e) {
                // Ignore errors during entity creation
            }
        }
        return recipes;
    }
}
