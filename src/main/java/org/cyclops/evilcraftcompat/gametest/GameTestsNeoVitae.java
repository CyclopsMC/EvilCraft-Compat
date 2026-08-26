package org.cyclops.evilcraftcompat.gametest;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.api.incense.ITranquilityHandler;
import com.breakinblocks.neovitae.common.datacomponent.Binding;
import com.breakinblocks.neovitae.common.datacomponent.NVDataComponents;
import com.breakinblocks.neovitae.common.item.NVItems;
import com.breakinblocks.neovitae.incense.EnumTranquilityType;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import org.cyclops.evilcraft.ExtendedDamageSources;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.blockentity.BlockEntityBloodInfuser;
import org.cyclops.evilcraft.entity.monster.EntityVengeanceSpirit;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.neovitae.AnimaHelpers;
import org.cyclops.evilcraftcompat.modcompat.neovitae.BoundBloodDrop;
import org.cyclops.evilcraftcompat.modcompat.neovitae.BoundBloodDropConfig;

/**
 * Game tests for the Neo Vitae mod compatibility.
 * @author rubensworks
 */
@GameTestHolder(Reference.MOD_ID)
@PrefixGameTestTemplate(false)
public class GameTestsNeoVitae {

    public static final String TEMPLATE_EMPTY = "empty10";
    public static final BlockPos POS = BlockPos.ZERO.offset(2, 0, 2);

    protected static ItemStack createBoundBloodDrop(Player owner) {
        ItemStack itemStack = new ItemStack(BoundBloodDrop.getInstance());
        itemStack.set(NVDataComponents.BINDING.get(), new Binding(owner.getUUID(), owner.getName().getString()));
        return itemStack;
    }

    protected static IFluidHandlerItem getFluidHandler(GameTestHelper helper, ItemStack itemStack) {
        IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(itemStack).orElse(null);
        helper.assertTrue(fluidHandler != null, "The Bound Blood Drop has no fluid handler");
        helper.assertTrue(fluidHandler instanceof BoundBloodDrop.FluidHandler,
                "The Bound Blood Drop has an unexpected fluid handler: " + fluidHandler.getClass());
        return fluidHandler;
    }

    /**
     * Right-clicking with the Bound Blood Drop must bind it to the player, via Neo Vitae's binding logic.
     */
    @GameTest(template = TEMPLATE_EMPTY, batch = "neovitae_binding")
    public void testBoundBloodDropBinding(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(helper.absolutePos(POS).getBottomCenter());
        ItemStack itemStack = new ItemStack(BoundBloodDrop.getInstance());
        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.assertTrue(BoundBloodDrop.getInstance().getBinding(itemStack) == null, "The Bound Blood Drop was already bound");

        NeoForge.EVENT_BUS.post(new PlayerInteractEvent.RightClickItem(player, InteractionHand.MAIN_HAND));

        Binding binding = BoundBloodDrop.getInstance().getBinding(player.getItemInHand(InteractionHand.MAIN_HAND));
        helper.assertTrue(binding != null, "The Bound Blood Drop was not bound");
        helper.assertValueEqual(binding.uuid(), player.getUUID(), "The Bound Blood Drop was bound to the wrong player");

        helper.succeed();
    }

    /**
     * An unbound Bound Blood Drop must not expose any capacity or contents.
     */
    @GameTest(template = TEMPLATE_EMPTY, batch = "neovitae_unbound")
    public void testBoundBloodDropUnbound(GameTestHelper helper) {
        ItemStack itemStack = new ItemStack(BoundBloodDrop.getInstance());
        IFluidHandlerItem fluidHandler = getFluidHandler(helper, itemStack);

        helper.assertValueEqual(fluidHandler.getTankCapacity(0), 0, "An unbound Bound Blood Drop has a capacity");
        helper.assertTrue(fluidHandler.getFluidInTank(0).isEmpty(), "An unbound Bound Blood Drop is not empty");
        helper.assertValueEqual(fluidHandler.fill(new FluidStack(RegistryEntries.FLUID_BLOOD.get(), 1000),
                IFluidHandler.FluidAction.EXECUTE), 0, "An unbound Bound Blood Drop can be filled");
        helper.assertTrue(fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE).isEmpty(),
                "An unbound Bound Blood Drop can be drained");

        helper.succeed();
    }

    /**
     * Filling the Bound Blood Drop must add essence to the anima of its owner.
     */
    @GameTest(template = TEMPLATE_EMPTY, batch = "neovitae_fill")
    public void testBoundBloodDropFill(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack itemStack = createBoundBloodDrop(player);
        IFluidHandlerItem fluidHandler = getFluidHandler(helper, itemStack);

        helper.assertValueEqual(fluidHandler.getTankCapacity(0), BoundBloodDropConfig.animaCapacity,
                "The Bound Blood Drop has an unexpected capacity");

        // Simulated fills may not modify the anima
        helper.assertValueEqual(fluidHandler.fill(new FluidStack(RegistryEntries.FLUID_BLOOD.get(), 1000),
                IFluidHandler.FluidAction.SIMULATE), 1000, "The simulated filled amount is wrong");
        helper.assertValueEqual(AnimaHelpers.getCurrentEssence(player.getUUID()), 0,
                "A simulated fill modified the anima");

        // Executed fills must modify the anima
        helper.assertValueEqual(fluidHandler.fill(new FluidStack(RegistryEntries.FLUID_BLOOD.get(), 1000),
                IFluidHandler.FluidAction.EXECUTE), 1000, "The filled amount is wrong");
        helper.assertValueEqual(AnimaHelpers.getCurrentEssence(player.getUUID()), 1000,
                "The anima was not filled");
        helper.assertValueEqual(FluidUtil.getFluidContained(itemStack).orElse(FluidStack.EMPTY).getAmount(), 1000,
                "The Bound Blood Drop does not expose the anima contents");

        // Other fluids must be rejected
        helper.assertValueEqual(fluidHandler.fill(new FluidStack(RegistryEntries.FLUID_POISON.get(), 1000),
                IFluidHandler.FluidAction.EXECUTE), 0, "A non-blood fluid was accepted");
        helper.assertValueEqual(AnimaHelpers.getCurrentEssence(player.getUUID()), 1000,
                "A non-blood fluid modified the anima");

        helper.succeed();
    }

    /**
     * Draining the Bound Blood Drop must syphon essence from the anima of its owner.
     */
    @GameTest(template = TEMPLATE_EMPTY, batch = "neovitae_drain")
    public void testBoundBloodDropDrain(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack itemStack = createBoundBloodDrop(player);
        IFluidHandlerItem fluidHandler = getFluidHandler(helper, itemStack);

        // Fill the anima directly, the Bound Blood Drop must expose it as blood
        AnimaHelpers.addEssence(player.getUUID(), 5000, BoundBloodDropConfig.animaCapacity);
        FluidStack contents = fluidHandler.getFluidInTank(0);
        helper.assertValueEqual(contents.getFluid(), RegistryEntries.FLUID_BLOOD.get(),
                "The Bound Blood Drop does not contain blood");
        helper.assertValueEqual(contents.getAmount(), 5000, "The Bound Blood Drop contents are wrong");

        // Simulated drains may not modify the anima
        helper.assertValueEqual(fluidHandler.drain(2000, IFluidHandler.FluidAction.SIMULATE).getAmount(), 2000,
                "The simulated drained amount is wrong");
        helper.assertValueEqual(AnimaHelpers.getCurrentEssence(player.getUUID()), 5000,
                "A simulated drain modified the anima");

        // Executed drains must modify the anima
        helper.assertValueEqual(fluidHandler.drain(2000, IFluidHandler.FluidAction.EXECUTE).getAmount(), 2000,
                "The drained amount is wrong");
        helper.assertValueEqual(AnimaHelpers.getCurrentEssence(player.getUUID()), 3000,
                "The anima was not drained");

        // Draining more than available must only drain what is available
        helper.assertValueEqual(fluidHandler.drain(10000, IFluidHandler.FluidAction.EXECUTE).getAmount(), 3000,
                "The drained overflow amount is wrong");
        helper.assertValueEqual(AnimaHelpers.getCurrentEssence(player.getUUID()), 0, "The anima was not emptied");
        helper.assertTrue(fluidHandler.getFluidInTank(0).isEmpty(), "The Bound Blood Drop is not empty");

        helper.succeed();
    }

    /**
     * Vengeance spirits that are killed by a vengeance beam must drop raw spiritus.
     */
    @GameTest(template = TEMPLATE_EMPTY, batch = "neovitae_spiritus")
    public void testVengeanceSpiritDropsSpiritus(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(helper.absolutePos(POS).getBottomCenter());

        BlockPos spiritPos = POS.south().south();
        EntityVengeanceSpirit spirit = helper.spawnWithNoFreeWill(RegistryEntries.ENTITY_VENGEANCE_SPIRIT.get(), spiritPos);
        spirit.setInnerEntityType(EntityType.ZOMBIE);
        spirit.hurt(ExtendedDamageSources.vengeanceBeam(player), 1000F);

        helper.succeedWhen(() -> {
            helper.assertEntityNotPresent(RegistryEntries.ENTITY_VENGEANCE_SPIRIT.get());
            helper.assertItemEntityPresent(NVItems.MONSTER_SOUL_RAW.get(), spiritPos, 2);
        });
    }

    /**
     * The Blood Infuser must be able to infuse a Neo Vitae Divination Sigil into a Bound Blood Drop.
     */
    @GameTest(template = TEMPLATE_EMPTY, timeoutTicks = 600, batch = "neovitae_recipe")
    public void testBloodInfuserBoundBloodDrop(GameTestHelper helper) {
        helper.assertTrue(helper.getLevel().getRecipeManager()
                .byKey(ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "blood_infuser/neovitae/bound_blood_drop")).isPresent(),
                "The Bound Blood Drop recipe was not loaded");

        helper.setBlock(POS, RegistryEntries.BLOCK_BLOOD_INFUSER.get());
        BlockEntityBloodInfuser infuser = helper.getBlockEntity(POS);
        infuser.getTank().setFluid(new FluidStack(RegistryEntries.FLUID_BLOOD.get(), 10000));
        infuser.getInventory().setItem(BlockEntityBloodInfuser.SLOT_INFUSE, new ItemStack(NVItems.SIGIL_DIVINATION.get()));
        infuser.getInventory().setItem(BlockEntityBloodInfuser.SLOTS, new ItemStack(RegistryEntries.ITEM_PROMISE_TIER_2, 1));
        infuser.getInventory().setItem(BlockEntityBloodInfuser.SLOTS + 1, new ItemStack(RegistryEntries.ITEM_PROMISE_SPEED, 4));

        helper.succeedWhen(() -> {
            ItemStack result = infuser.getInventory().getItem(BlockEntityBloodInfuser.SLOT_INFUSE_RESULT);
            helper.assertFalse(result.isEmpty(), "Result is not available");
            helper.assertTrue(result.getItem() == BoundBloodDrop.getInstance(), "Result item is wrong: " + result);
        });
    }

    /**
     * The tranquility values that this mod adds for EvilCraft blocks must be picked up by Neo Vitae.
     */
    @GameTest(template = TEMPLATE_EMPTY, batch = "neovitae_tranquility")
    public void testTranquility(GameTestHelper helper) {
        ITranquilityHandler tranquilityHandler = NeoVitaeAPI.getInstance().getTranquilityHandler();

        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_UNDEAD_LOG.get(), EnumTranquilityType.TREE, 1.2);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_UNDEAD_LOG_STRIPPED.get(), EnumTranquilityType.TREE, 1.2);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_UNDEAD_LEAVES.get(), EnumTranquilityType.PLANT, 1.2);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_UNDEAD_PLANK.get(), EnumTranquilityType.PLANT, 0.8);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_BLOOD.get(), EnumTranquilityType.WATER, 1.6);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_POISON.get(), EnumTranquilityType.WATER, 0.8);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_HARDENED_BLOOD.get(), EnumTranquilityType.EARTHEN, 1.5);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_DARK_BRICK.get(), EnumTranquilityType.EARTHEN, 1.0);
        assertTranquility(helper, tranquilityHandler, RegistryEntries.BLOCK_DARK_BLOOD_BRICK.get(), EnumTranquilityType.EARTHEN, 1.5);

        helper.succeed();
    }

    protected static void assertTranquility(GameTestHelper helper, ITranquilityHandler tranquilityHandler,
                                            Block block, EnumTranquilityType expectedType, double expectedValue) {
        helper.assertTrue(tranquilityHandler.hasTranquility(block), "Block has no tranquility: " + block);
        helper.assertValueEqual(tranquilityHandler.getTranquilityType(block), expectedType,
                "Block has an unexpected tranquility type: " + block);
        helper.assertValueEqual(tranquilityHandler.getTranquilityValue(block), expectedValue,
                "Block has an unexpected tranquility value: " + block);
    }

}
