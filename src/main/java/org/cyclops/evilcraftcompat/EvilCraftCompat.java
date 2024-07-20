package org.cyclops.evilcraftcompat;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.cyclops.cyclopscore.infobook.IInfoBookRegistry;
import org.cyclops.cyclopscore.init.ModBaseVersionable;
import org.cyclops.cyclopscore.modcompat.ModCompatLoader;
import org.cyclops.cyclopscore.proxy.IClientProxy;
import org.cyclops.cyclopscore.proxy.ICommonProxy;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraft.infobook.OriginsOfDarknessBook;
import org.cyclops.evilcraftcompat.modcompat.capabilities.CommonCapabilitiesCompat;
import org.cyclops.evilcraftcompat.proxy.ClientProxy;
import org.cyclops.evilcraftcompat.proxy.CommonProxy;

/**
 * The main mod class of this mod.
 * @author rubensworks (aka kroeserr)
 *
 */
@Mod(Reference.MOD_ID)
public class EvilCraftCompat extends ModBaseVersionable<EvilCraftCompat> {

    /**
     * The unique instance of this mod.
     */
    public static EvilCraftCompat _instance;

    public EvilCraftCompat(IEventBus modEventBus) {
        super(Reference.MOD_ID, (instance) -> _instance = instance, modEventBus);
        modEventBus.addListener(this::afterSetup);
    }

    @Override
    protected void loadModCompats(ModCompatLoader modCompatLoader) {
        // Mod compats
        /*modCompatLoader.addModCompat(new WailaModCompat());
        modCompatLoader.addModCompat(new BloodMagicModCompat());
        modCompatLoader.addModCompat(new TConstructModCompat());
        modCompatLoader.addModCompat(new ForestryModCompat());
        modCompatLoader.addModCompat(new IC2ModCompat());
        modCompatLoader.addModCompat(new ImmersiveEngineeringModCompat());
        modCompatLoader.addModCompat(new EnderIOModCompat());
        modCompatLoader.addModCompat(new ThermalExpansionModCompat());
        modCompatLoader.addModCompat(new CraftTweakerModCompat());
        modCompatLoader.addModCompat(new ThaumcraftModCompat());*/
        modCompatLoader.addModCompat(new CommonCapabilitiesCompat());
    }

    @Override
    protected void setup(FMLCommonSetupEvent event) {
        super.setup(event);
    }

    protected void afterSetup(FMLLoadCompleteEvent event) {
        // Initialize info book
        EvilCraft._instance.getRegistryManager().getRegistry(IInfoBookRegistry.class)
                .registerSection(this,
                        OriginsOfDarknessBook.getInstance(), "info_book.evilcraft.section.main",
                        "/data/" + Reference.MOD_ID + "/info/modcompat.xml");
    }

    @Override
    protected IClientProxy constructClientProxy() {
        return new ClientProxy();
    }

    @Override
    protected ICommonProxy constructCommonProxy() {
        return new CommonProxy();
    }

    @Override
    protected boolean hasDefaultCreativeModeTab() {
        return false;
    }
}
