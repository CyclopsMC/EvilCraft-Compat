package org.cyclops.evilcraftcompat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * @author rubensworks
 */
public class RegistryEntriesCompat {

    public static final DeferredHolder<Item, Item> ITEM_INVIGORATING_PENDANT = DeferredHolder.create(Registries.ITEM, ResourceLocation.parse("evilcraft:invigorating_pendant"));
    public static final DeferredHolder<Item, Item> ITEM_PRIMED_PENDANT = DeferredHolder.create(Registries.ITEM, ResourceLocation.parse("evilcraft:primed_pendant"));
    public static final DeferredHolder<Item, Item> ITEM_KINETICATOR = DeferredHolder.create(Registries.ITEM, ResourceLocation.parse("evilcraft:kineticator"));
    public static final DeferredHolder<Item, Item> ITEM_KINETICATOR_REPELLING = DeferredHolder.create(Registries.ITEM, ResourceLocation.parse("evilcraft:kineticator_repelling"));
    public static final DeferredHolder<Item, Item> ITEM_MACE_OF_DESTRUCTION = DeferredHolder.create(Registries.ITEM, ResourceLocation.parse("evilcraft:mace_of_destruction"));
    public static final DeferredHolder<Item, Item> ITEM_NECROMANCER_STAFF = DeferredHolder.create(Registries.ITEM, ResourceLocation.parse("evilcraft:necromancer_staff"));
    public static final DeferredHolder<Item, Item> ITEM_FLESH_REJUVENATED = DeferredHolder.create(Registries.ITEM, ResourceLocation.parse("evilcraft:flesh_rejuvenated"));

}
