package org.cyclops.evilcraftcompat;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.cyclops.cyclopscore.infobook.IInfoBookRegistry;
import org.cyclops.cyclopscore.init.ModBaseVersionable;
import org.cyclops.cyclopscore.modcompat.ModCompatLoader;
import org.cyclops.cyclopscore.proxy.IClientProxy;
import org.cyclops.cyclopscore.proxy.ICommonProxy;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraft.core.tileentity.TickingTankInventoryTileEntity;
import org.cyclops.evilcraft.infobook.OriginsOfDarknessBook;
import org.cyclops.evilcraft.proxy.ClientProxy;
import org.cyclops.evilcraft.proxy.CommonProxy;
import org.cyclops.evilcraft.tileentity.TileBloodInfuser;
import org.cyclops.evilcraft.tileentity.TileEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.modcompat.capabilities.RecipeHandlerBloodInfuserTileCompat;
import org.cyclops.evilcraftcompat.modcompat.capabilities.WorkerEnvirAccTileCompat;
import org.cyclops.evilcraftcompat.modcompat.capabilities.WorkerWorkingTileCompat;
import org.cyclops.evilcraftcompat.modcompat.curios.CuriosCompat;

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

    public EvilCraftCompat() {
        super(Reference.MOD_ID, (instance) -> _instance = instance);
    }

    @Override
    protected void loadModCompats(ModCompatLoader modCompatLoader) {
        // Mod compats
        modCompatLoader.addModCompat(new CuriosCompat());
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

        // Capabilities
        getCapabilityConstructorRegistry().registerTile(TickingTankInventoryTileEntity.class, new WorkerWorkingTileCompat());
        getCapabilityConstructorRegistry().registerTile(TileEnvironmentalAccumulator.class, new WorkerEnvirAccTileCompat());
        getCapabilityConstructorRegistry().registerTile(TileBloodInfuser.class, new RecipeHandlerBloodInfuserTileCompat());
    }

    @Override
    protected void setup(FMLCommonSetupEvent event) {
        super.setup(event);

        // Initialize info book
        EvilCraft._instance.getRegistryManager().getRegistry(IInfoBookRegistry.class)
                .registerSection(
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
    protected ItemGroup constructDefaultItemGroup() {
        return null;
    }

}
