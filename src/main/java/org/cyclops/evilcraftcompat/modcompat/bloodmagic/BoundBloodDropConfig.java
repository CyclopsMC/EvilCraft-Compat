package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.neoforged.neoforge.common.MinecraftForge;
import net.neoforged.neoforge.event.RegistryEvent;
import net.neoforged.fml.common.eventhandler.EventPriority;
import net.neoforged.fml.common.eventhandler.SubscribeEvent;
import org.cyclops.cyclopscore.config.ConfigurableProperty;
import org.cyclops.cyclopscore.config.ConfigurableTypeCategory;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

/**
 * Config for the {@link org.cyclops.evilcraftcompat.modcompat.bloodmagic.BoundBloodDrop}.
 * @author rubensworks
 *
 */
public class BoundBloodDropConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static org.cyclops.evilcraftcompat.modcompat.bloodmagic.BoundBloodDropConfig _instance;

    /**
     * Max update frequency
     */
    @ConfigurableProperty(category = ConfigurableTypeCategory.WORLDGENERATION, comment = "The amount of ticks the server should wait before sending a soul network update. (only for servers)")
    public static int maxUpdateTicks = 40;
    /**
     * If held buckets should be autofilled when enabled.
     */
    @ConfigurableProperty(category = ConfigurableTypeCategory.ITEM, comment = "If held buckets should be autofilled when enabled.", isCommandable = true)
    public static boolean autoFillBuckets = false;

    /**
     * Make a new instance.
     */
    public BoundBloodDropConfig() {
        super(
                EvilCraft._instance,
                true,
                "bound_blood_drop",
                null,
                BoundBloodDrop.class
        );
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegistered() {
        super.onRegistered();
        // TODO: make this not required anymore
        // This is to make the recipe work, which was registered in EC
        EvilCraft._instance.getConfigHandler().addToConfigDictionary(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void afterItemsRegistered(RegistryEvent<Item> event) {
        // Register predefined item for recipe
        ItemStack weakOrb = new ItemStack(Item.getByNameOrId("bloodmagic:blood_orb"));
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("orb", "bloodmagic:weak");
        weakOrb.setTagCompound(tag);
        EvilCraftCompat._instance.getRecipeHandler().getPredefinedItems().put("evilcraft:weakbloodorb", weakOrb);
    }
}
