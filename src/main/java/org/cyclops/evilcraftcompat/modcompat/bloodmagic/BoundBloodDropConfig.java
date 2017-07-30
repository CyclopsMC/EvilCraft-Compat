package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import org.cyclops.cyclopscore.config.ConfigurableProperty;
import org.cyclops.cyclopscore.config.ConfigurableTypeCategory;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraftcompat.modcompat.bloodmagic.BoundBloodDrop;

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
     * The maximum capacity in (Blood) mB that can be filled.
     */
    @ConfigurableProperty(category = ConfigurableTypeCategory.ITEM, comment = "The maximum capacity in (Blood) mB that can be filled.", isCommandable = true)
    public static int maxCapacity = 250000;

    /**
     * Max update frequency
     */
    @ConfigurableProperty(category = ConfigurableTypeCategory.WORLDGENERATION, comment = "The amount of ticks the server should wait before sending a soul network update. (only for servers)")
    public static int maxUpdateTicks = 40;

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
    }
    
}
