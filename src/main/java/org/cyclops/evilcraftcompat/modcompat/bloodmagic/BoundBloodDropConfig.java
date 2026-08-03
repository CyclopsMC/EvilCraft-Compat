package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import org.cyclops.cyclopscore.config.ConfigurableProperty;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.cyclopscore.init.ModBase;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

/**
 * Config for the {@link BoundBloodDrop}.
 * @author rubensworks
 *
 */
public class BoundBloodDropConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static BoundBloodDropConfig _instance;

    /**
     * Max update frequency
     */
    @ConfigurableProperty(category = "item", comment = "The amount of ticks the server should wait before sending a soul network update. (only for servers)", isCommandable = true)
    public static int maxUpdateTicks = 40;
    /**
     * If held buckets should be autofilled when enabled.
     */
    @ConfigurableProperty(category = "item", comment = "If held buckets should be autofilled when enabled.", isCommandable = true)
    public static boolean autoFillBuckets = false;

    /**
     * Make a new instance.
     */
    public BoundBloodDropConfig() {
        super(EvilCraftCompat._instance, "bound_blood_drop", BoundBloodDrop::new);
    }

    @Override
    public ModBase getMod() {
        return EvilCraft._instance;
    }
}
