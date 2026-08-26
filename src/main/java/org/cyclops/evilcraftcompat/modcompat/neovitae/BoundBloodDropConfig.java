package org.cyclops.evilcraftcompat.modcompat.neovitae;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.cyclops.cyclopscore.CyclopsCore;
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
    @ConfigurableProperty(category = "item", comment = "The amount of ticks the server should wait before sending an anima update. (only for servers)", isCommandable = true)
    public static int maxUpdateTicks = 40;
    /**
     * If held buckets should be autofilled when enabled.
     */
    @ConfigurableProperty(category = "item", comment = "If held buckets should be autofilled when enabled.", isCommandable = true)
    public static boolean autoFillBuckets = false;
    /**
     * The maximum amount of essence that an anima is assumed to hold.
     */
    @ConfigurableProperty(category = "item", comment = "The maximum amount of Essentia Vitae that an anima is assumed to be able to hold. Defaults to the capacity of Neo Vitae's highest-tier blood orb.", isCommandable = true, minimalValue = 0)
    public static int animaCapacity = 10000000;

    /**
     * Make a new instance.
     */
    public BoundBloodDropConfig() {
        super(EvilCraftCompat._instance, "bound_blood_drop", BoundBloodDrop::new);
        _instance = this;
        // This listener must be registered on CyclopsCore's mod event bus,
        // as DamageIndicatedItemFluidContainer registers its (item-backed) fluid handler there as well.
        // Since the first non-null capability provider wins, ours must be registered first,
        // which is guaranteed here because the item instance (and thus its own listener) is only created later.
        CyclopsCore._instance.getModEventBus().addListener(this::registerCapability);
    }

    protected void registerCapability(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM,
                (stack, context) -> new BoundBloodDrop.FluidHandler(stack), getInstance());
        event.registerItem(org.cyclops.cyclopscore.Capabilities.Item.FLUID_HANDLER_CAPACITY,
                (stack, context) -> new BoundBloodDrop.FluidHandler(stack), getInstance());
    }

    @Override
    public ModBase getMod() {
        return EvilCraft._instance;
    }
}
