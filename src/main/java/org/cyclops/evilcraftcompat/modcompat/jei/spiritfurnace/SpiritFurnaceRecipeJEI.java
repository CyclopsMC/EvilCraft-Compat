package org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace;

import com.google.common.collect.Lists;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.block.BlockSpiritFurnaceConfig;
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

    public SpiritFurnaceRecipeJEI(EntityType<?> entityType) {
        Entity entity = entityType.create(ServerLifecycleHooks.getCurrentServer().overworld());
        this.duration = getRequiredTicks(entity);
        this.inputFluid = new FluidStack(RegistryEntries.FLUID_BLOOD, this.duration * BlockSpiritFurnaceConfig.mBPerTick);
        this.inputItem = SpiritReanimatorRecipeJEI.getBox(entityType);
        this.outputItems = getMobDrops(entityType, entity);
    }

    public static List<ItemStack> getMobDrops(EntityType<?> entityType, Entity entity) {
        List<ItemStack> items = Lists.newArrayList();

        ServerLevel level = ServerLifecycleHooks.getCurrentServer().overworld();
        FakePlayer killerEntity = FakePlayerFactory.getMinecraft(level);
        LootContext.Builder lootcontext$builder = (new LootContext.Builder(level))
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, Vec3.ZERO)
                .withParameter(LootContextParams.KILLER_ENTITY, killerEntity).withParameter(LootContextParams.DIRECT_KILLER_ENTITY, killerEntity)
                .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.GENERIC);
        LootContext context = lootcontext$builder.create(LootContextParamSets.ENTITY);

        LootTable lootTable = ServerLifecycleHooks.getCurrentServer().getLootTables().get(entityType.getDefaultLootTable());
        for (LootPool pool : lootTable.pools) {
            for (LootPoolEntryContainer entryContainer : pool.entries) {
                entryContainer.expand(context, entry -> entry.createItemStack(items::add, context));
            }
        }
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

    public static List<SpiritFurnaceRecipeJEI> getAllRecipes() {
        List<SpiritFurnaceRecipeJEI> recipes = Lists.newArrayList();
        for (EntityType<?> entityType : ForgeRegistries.ENTITY_TYPES) {
            try {
                recipes.add(new SpiritFurnaceRecipeJEI(entityType));
            } catch (RuntimeException e) {
                // Ignore errors during entity creation
            }
        }
        return recipes;
    }
}
