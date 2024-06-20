package org.cyclops.evilcraftcompat.modcompat.thaumcraft;

import net.minecraft.item.ItemStack;
import net.neoforged.fml.common.eventhandler.SubscribeEvent;
import org.cyclops.cyclopscore.config.extendedconfig.BlockConfig;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.cyclopscore.config.extendedconfig.MobConfig;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.block.*;
import org.cyclops.evilcraft.entity.monster.NetherfishConfig;
import org.cyclops.evilcraft.entity.monster.PoisonousLibelleConfig;
import org.cyclops.evilcraft.entity.monster.WerewolfConfig;
import org.cyclops.evilcraft.item.*;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

/**
 * @author rubensworks
 */
public class EvilCraftThaumcraftAspects {

    @SubscribeEvent
    public void register(AspectRegistryEvent event) {
        registerAspects(event.register);

        if(Configs.isEnabled(DarkGemConfig.class)) {
            ThaumcraftApi.addLootBagItem(new ItemStack(DarkGem.getInstance()), 100, 0, 1, 2);
            ThaumcraftApi.addLootBagItem(new ItemStack(DarkGem.getInstance(), 2), 80, 0, 1, 2);
            ThaumcraftApi.addLootBagItem(new ItemStack(DarkGem.getInstance(), 5), 40, 0, 1, 2);
        }
        if(Configs.isEnabled(DarkGemCrushedConfig.class)) {
            ThaumcraftApi.addLootBagItem(new ItemStack(DarkGemCrushedConfig._instance.getItemInstance()), 80, 0, 1, 2);
            ThaumcraftApi.addLootBagItem(new ItemStack(DarkGemCrushedConfig._instance.getItemInstance(), 2), 60, 0, 1, 2);
            ThaumcraftApi.addLootBagItem(new ItemStack(DarkGemCrushedConfig._instance.getItemInstance(), 5), 20, 0, 1, 2);
        }
        if(Configs.isEnabled(GarmonboziaConfig.class)) {
            ThaumcraftApi.addLootBagItem(new ItemStack(GarmonboziaConfig._instance.getItemInstance()), 5, 1, 2);
            ThaumcraftApi.addLootBagItem(new ItemStack(GarmonboziaConfig._instance.getItemInstance(), 2), 3, 1, 2);
            ThaumcraftApi.addLootBagItem(new ItemStack(GarmonboziaConfig._instance.getItemInstance(), 3), 1, 1, 2);
        }
    }

    private static void registerAspects(AspectEventProxy registry) {
        // Entities
        registerEntityTagSafe(registry, NetherfishConfig._instance, new AspectList().add(Aspect.FIRE, 4).add(Aspect.BEAST, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
        registerEntityTagSafe(registry, PoisonousLibelleConfig._instance, new AspectList().add(Aspect.BEAST, 1).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[0]);
        registerEntityTagSafe(registry, WerewolfConfig._instance, new AspectList().add(Aspect.BEAST, 4), new ThaumcraftApi.EntityTagsNBT[0]);

        // Blood
        AspectList bloodLists = new AspectList().add(Aspect.ENERGY, 2).add(Aspect.LIFE, 4);
        registerObjectTagSafe(registry, FluidBlockBloodConfig._instance, bloodLists.copy());
        registerObjectTagSafe(registry, HardenedBloodConfig._instance, bloodLists.copy());
        registerObjectTagSafe(registry, HardenedBloodShardConfig._instance, new AspectList().add(Aspect.LIFE, 1));
        registerObjectTagSafe(registry, BloodStainedBlockConfig._instance, bloodLists.copy());
        registerObjectTagSafe(registry, BloodyCobblestoneConfig._instance, bloodLists.copy().add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1));
        registerObjectTagSafe(registry, CondensedBloodConfig._instance, bloodLists.copy());

        // Dark Gem
        registry.registerObjectTag("gemDark", new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.DARKNESS, 4));
        registerObjectTagSafe(registry, DarkOreConfig._instance, new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.DARKNESS, 2).add(Aspect.EARTH, 1));
        registerObjectTagSafe(registry, DarkGemCrushedConfig._instance, new AspectList().add(Aspect.SOUL, 2).add(Aspect.DARKNESS, 2).add(Aspect.EARTH, 1));
        registerObjectTagSafe(registry, DarkPowerGemConfig._instance, new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.DARKNESS, 4).add(bloodLists.copy()));

        // Poison
        registerObjectTagSafe(registry, FluidBlockPoisonConfig._instance, new AspectList().add(Aspect.EARTH, 4));
        registerObjectTagSafe(registry, PoisonSacConfig._instance, new AspectList().add(Aspect.MOTION, 4).add(Aspect.EARTH, 2));

        // Undead tree
        registerObjectTagSafe(registry, UndeadSaplingConfig._instance, new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 2).add(Aspect.UNDEAD, 2));
        registerObjectTagSafe(registry, UndeadPlankConfig._instance, new AspectList().add(Aspect.EARTH, 1).add(Aspect.UNDEAD, 1));
        registerObjectTagSafe(registry, UndeadLogConfig._instance, new AspectList().add(Aspect.EARTH, 4).add(Aspect.UNDEAD, 4));
        registerObjectTagSafe(registry, UndeadLeavesConfig._instance, new AspectList().add(Aspect.PLANT, 1).add(Aspect.UNDEAD, 1));

        // Werewolf drops
        registerObjectTagSafe(registry, WerewolfBoneConfig._instance, new AspectList().add(Aspect.DEATH, 2));
        registerObjectTagSafe(registry, WerewolfFleshConfig._instance, new AspectList().add(Aspect.MAN, 8));
        registerObjectTagSafe(registry, WerewolfFurConfig._instance, new AspectList().add(Aspect.BEAST, 8));

        // Weather containers
        AspectList weatherContainerList = new AspectList().add(Aspect.DARKNESS, 3).add(Aspect.CRYSTAL, 1);
        registerObjectTagSafe(registry, WeatherContainerConfig._instance, new AspectList().add(weatherContainerList.copy()));
        registerObjectTagSafe(registry, WeatherContainerConfig._instance, WeatherContainer.WeatherContainerTypes.CLEAR.ordinal(), new AspectList().add(weatherContainerList.copy()).add(Aspect.ORDER, 6));
        registerObjectTagSafe(registry, WeatherContainerConfig._instance, WeatherContainer.WeatherContainerTypes.RAIN.ordinal(), new AspectList().add(weatherContainerList.copy()).add(Aspect.WATER, 6));
        registerObjectTagSafe(registry, WeatherContainerConfig._instance, WeatherContainer.WeatherContainerTypes.LIGHTNING.ordinal(), new AspectList().add(weatherContainerList.copy()).add(Aspect.ENERGY, 6));

        // Other
        registerObjectTagSafe(registry, EnvironmentalAccumulatorConfig._instance, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.EXCHANGE, 4).add(Aspect.MECHANISM, 3));
        registerObjectTagSafe(registry, SpiritPortalConfig._instance, new AspectList().add(Aspect.FLUX, 10).add(Aspect.MOTION, 10).add(Aspect.UNDEAD, 10));
        registerObjectTagSafe(registry, DarkBloodBrickConfig._instance, new AspectList(new ItemStack(DarkBrickConfig._instance.getBlockInstance())).add(new AspectList(new ItemStack(DarkPowerGemConfig._instance.getItemInstance()))));
        registerObjectTagSafe(registry, BroomConfig._instance, new AspectList().add(Aspect.MOTION, 4).add(Aspect.FLIGHT, 6).add(Aspect.MOTION, 2));
        registerObjectTagSafe(registry, BlookConfig._instance, new AspectList().add(bloodLists.copy()).add(Aspect.MIND, 1));
        registerObjectTagSafe(registry, InvertedPotentiaConfig._instance, InvertedPotentia.EMPOWERED_META, new AspectList(new ItemStack(InvertedPotentia.getInstance())).add(Aspect.ENERGY, 4));
        registerObjectTagSafe(registry, CorruptedTearConfig._instance, new AspectList().add(Aspect.DEATH, 4).add(Aspect.SOUL, 4).add(Aspect.ENTROPY, 4).add(Aspect.EXCHANGE, 8).add(Aspect.MOTION, 2).add(Aspect.FLUX, 4));
        for(int i = 0; i < 3; i++) {
            registerObjectTagSafe(registry, PromiseAcceptorConfig._instance, i, new AspectList().add(Aspect.METAL, 10).add(Aspect.MECHANISM, 10));
        }
        registerObjectTagSafe(registry, BloodWaxedCoalConfig._instance, new AspectList().add(Aspect.FIRE, 4).add(Aspect.ENERGY, 4).add(bloodLists.copy()));
        registerObjectTagSafe(registry, BloodPotashConfig._instance, new AspectList().add(Aspect.SENSES, 2).add(bloodLists.copy()));
        registerObjectTagSafe(registry, EnderTearConfig._instance, new AspectList().add(Aspect.MOTION, 8).add(Aspect.ELDRITCH, 8).add(Aspect.ELDRITCH, 4));
        registerObjectTagSafe(registry, BloodOrbConfig._instance, 1, new AspectList().add(Aspect.ORDER, 1).add(bloodLists.copy()));
        registerObjectTagSafe(registry, OriginsOfDarknessConfig._instance, new AspectList().add(Aspect.MIND, 4).add(Aspect.DARKNESS, 4));
        registry.registerObjectTag(DarkSpikeConfig._instance.getOreDictionaryId(), new AspectList().add(Aspect.METAL, 2).add(Aspect.DARKNESS, 2).add(Aspect.DEATH, 1));
        registerObjectTagSafe(registry, ObscuredGlassConfig._instance, new AspectList().add(Aspect.DARKNESS, 4).add(Aspect.LIGHT, 4));
        registerObjectTagSafe(registry, BurningGemStoneConfig._instance, new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.DARKNESS, 10).add(Aspect.FIRE, 4).add(Aspect.SOUL, 2));
        registerObjectTagSafe(registry, GemStoneTorchConfig._instance, new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.DARKNESS, 2).add(Aspect.FIRE, 2).add(Aspect.LIGHT, 2).add(Aspect.SOUL, 1));
        registerObjectTagSafe(registry, PotentiaSphereConfig._instance, new AspectList().add(Aspect.EARTH, 2).add(Aspect.CRYSTAL, 2).add(Aspect.LIGHT, 2).add(Aspect.MECHANISM, 1));
        registerObjectTagSafe(registry, BiomeExtractConfig._instance, new AspectList().add(Aspect.EARTH, 2).add(Aspect.EXCHANGE, 4));
        registerObjectTagSafe(registry, EnvironmentalAccumulationCoreConfig._instance, new AspectList().add(Aspect.EXCHANGE, 10).add(Aspect.MECHANISM, 2));

    }

    private static void registerEntityTagSafe(AspectEventProxy registry, MobConfig mobConfig, AspectList aspects, ThaumcraftApi.EntityTagsNBT... nbt) {
        if(mobConfig != null) {
            // TODO: registry is not supported yet?
            ThaumcraftApi.registerEntityTag(mobConfig.getNamedId(), aspects, nbt);
        }
    }

    private static void registerObjectTagSafe(AspectEventProxy registry, ItemConfig itemConfig, AspectList aspects) {
        if(itemConfig != null) {
            registry.registerObjectTag(new ItemStack(itemConfig.getItemInstance()), aspects);
        }
    }

    private static void registerObjectTagSafe(AspectEventProxy registry, ItemConfig itemConfig, int meta, AspectList aspects) {
        if(itemConfig != null) {
            registry.registerObjectTag(new ItemStack(itemConfig.getItemInstance(), 1, meta), aspects);
        }
    }

    private static void registerObjectTagSafe(AspectEventProxy registry, BlockConfig blockConfig, AspectList aspects) {
        if(blockConfig != null) {
            registry.registerObjectTag(new ItemStack(blockConfig.getBlockInstance()), aspects);
        }
    }

}
