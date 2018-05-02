package org.cyclops.evilcraftcompat;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Level;
import org.cyclops.cyclopscore.config.ConfigHandler;
import org.cyclops.cyclopscore.infobook.IInfoBookRegistry;
import org.cyclops.cyclopscore.init.ModBaseVersionable;
import org.cyclops.cyclopscore.init.RecipeHandler;
import org.cyclops.cyclopscore.modcompat.ModCompatLoader;
import org.cyclops.cyclopscore.proxy.ICommonProxy;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraft.ExtendedRecipeHandler;
import org.cyclops.evilcraft.core.tileentity.TickingTankInventoryTileEntity;
import org.cyclops.evilcraft.infobook.OriginsOfDarknessBook;
import org.cyclops.evilcraft.tileentity.TileEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.modcompat.bloodmagic.BloodMagicModCompat;
import org.cyclops.evilcraftcompat.modcompat.capabilities.WorkerEnvirAccTileCompat;
import org.cyclops.evilcraftcompat.modcompat.capabilities.WorkerWorkingTileCompat;
import org.cyclops.evilcraftcompat.modcompat.crafttweaker.CraftTweakerModCompat;
import org.cyclops.evilcraftcompat.modcompat.enderio.EnderIOModCompat;
import org.cyclops.evilcraftcompat.modcompat.forestry.ForestryModCompat;
import org.cyclops.evilcraftcompat.modcompat.ic2.IC2ModCompat;
import org.cyclops.evilcraftcompat.modcompat.immersiveengineering.ImmersiveEngineeringModCompat;
import org.cyclops.evilcraftcompat.modcompat.jei.JEIModCompat;
import org.cyclops.evilcraftcompat.modcompat.tconstruct.TConstructModCompat;
import org.cyclops.evilcraftcompat.modcompat.thaumcraft.ThaumcraftModCompat;
import org.cyclops.evilcraftcompat.modcompat.thermalexpansion.ThermalExpansionModCompat;
import org.cyclops.evilcraftcompat.modcompat.waila.WailaModCompat;

/**
 * The main mod class of this mod.
 * @author rubensworks (aka kroeserr)
 *
 */
@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        useMetadata = true,
        version = Reference.MOD_VERSION,
        dependencies = Reference.MOD_DEPENDENCIES,
        guiFactory = "org.cyclops.evilcraftcompat.GuiConfigOverview$ExtendedConfigGuiFactory"
)
public class EvilCraftCompat extends ModBaseVersionable {
    
    /**
     * The proxy of this mod, depending on 'side' a different proxy will be inside this field.
     * @see SidedProxy
     */
    @SidedProxy(clientSide = "org.cyclops.evilcraftcompat.proxy.ClientProxy", serverSide = "org.cyclops.evilcraftcompat.proxy.CommonProxy")
    public static ICommonProxy proxy;
    
    /**
     * The unique instance of this mod.
     */
    @Instance(value = Reference.MOD_ID)
    public static EvilCraftCompat _instance;

    public EvilCraftCompat() {
        super(Reference.MOD_ID, Reference.MOD_NAME, Reference.MOD_VERSION);
    }

    @Override
    protected void loadModCompats(ModCompatLoader modCompatLoader) {
        // Mod compats
        modCompatLoader.addModCompat(new WailaModCompat());
        modCompatLoader.addModCompat(new JEIModCompat());
        modCompatLoader.addModCompat(new BloodMagicModCompat());
        modCompatLoader.addModCompat(new TConstructModCompat());
        modCompatLoader.addModCompat(new ForestryModCompat());
        modCompatLoader.addModCompat(new IC2ModCompat());
        modCompatLoader.addModCompat(new ImmersiveEngineeringModCompat());
        modCompatLoader.addModCompat(new EnderIOModCompat());
        modCompatLoader.addModCompat(new ThermalExpansionModCompat());
        modCompatLoader.addModCompat(new CraftTweakerModCompat());
        modCompatLoader.addModCompat(new ThaumcraftModCompat());

        // Capabilities
        getCapabilityConstructorRegistry().registerTile(TickingTankInventoryTileEntity.class, new WorkerWorkingTileCompat());
        getCapabilityConstructorRegistry().registerTile(TileEnvironmentalAccumulator.class, new WorkerEnvirAccTileCompat());
    }

    @Override
    protected RecipeHandler constructRecipeHandler() {
        return new ExtendedRecipeHandler(EvilCraft._instance,
                "bloodinfuser_mods.xml",
                "shapeless_mods.xml"
        ) {
            @Override
            protected String getRecipesBasePath() {
                return "/assets/" + Reference.MOD_ID + "/recipes/";
            }
        };
    }

    /**
     * The pre-initialization, will register required configs.
     * @param event The Forge event required for this.
     */
    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    
    /**
     * Register the config dependent things like world generation and proxy handlers.
     * @param event The Forge event required for this.
     */
    @EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
    
    /**
     * Register the event hooks.
     * @param event The Forge event required for this.
     */
    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        // Initialize info book
        EvilCraft._instance.getRegistryManager().getRegistry(IInfoBookRegistry.class)
                .registerSection(
                        OriginsOfDarknessBook.getInstance(), "info_book.evilcraft.section.main",
                        "/assets/" + Reference.MOD_ID + "/info/modcompat.xml");
    }
    
    /**
     * Register the things that are related to server starting, like commands.
     * @param event The Forge event required for this.
     */
    @EventHandler
    @Override
    public void onServerStarting(FMLServerStartingEvent event) {
        super.onServerStarting(event);
    }

    /**
     * Register the things that are related to server starting.
     * @param event The Forge event required for this.
     */
    @EventHandler
    @Override
    public void onServerStarted(FMLServerStartedEvent event) {
        super.onServerStarted(event);
    }

    /**
     * Register the things that are related to server stopping, like persistent storage.
     * @param event The Forge event required for this.
     */
    @EventHandler
    @Override
    public void onServerStopping(FMLServerStoppingEvent event) {
        super.onServerStopping(event);
    }

    @Override
    public CreativeTabs constructDefaultCreativeTab() {
        // Uncomment the following line and specify an item config class to add a creative tab
        // return new ItemCreativeTab(this, new ItemConfigReference(ITEM CONFIG CLASS));
        return null;
    }

    @Override
    public void onGeneralConfigsRegister(ConfigHandler configHandler) {
        configHandler.add(new GeneralConfig());
    }

    @Override
    public ICommonProxy getProxy() {
        return proxy;
    }

    /**
     * Log a new info message for this mod.
     * @param message The message to show.
     */
    public static void clog(String message) {
        clog(Level.INFO, message);
    }
    
    /**
     * Log a new message of the given level for this mod.
     * @param level The level in which the message must be shown.
     * @param message The message to show.
     */
    public static void clog(Level level, String message) {
        EvilCraftCompat._instance.getLoggerHelper().log(level, message);
    }
    
}
