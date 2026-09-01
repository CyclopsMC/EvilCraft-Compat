package org.cyclops.evilcraftcompat.modcompat.bloodmagic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.cyclops.evilcraft.RegistryEntries;
import wayoftime.bloodmagic.incense.EnumTranquilityType;
import wayoftime.bloodmagic.incense.ITranquilityHandler;
import wayoftime.bloodmagic.incense.IncenseTranquilityRegistry;
import wayoftime.bloodmagic.incense.TranquilityStack;

/**
 * Tranquility handlers for Blood Magic
 * @author rubensworks
 */
public class TranquilityHandlers {

    public static void register() {
        IncenseTranquilityRegistry.registerTranquilityHandler(new TreeLog());
        IncenseTranquilityRegistry.registerTranquilityHandler(new TreeLeaves());
        IncenseTranquilityRegistry.registerTranquilityHandler(new Planks());
        IncenseTranquilityRegistry.registerTranquilityHandler(new Poison());
        IncenseTranquilityRegistry.registerTranquilityHandler(new HardenedBlood());
        IncenseTranquilityRegistry.registerTranquilityHandler(new DarkBricks());
        IncenseTranquilityRegistry.registerTranquilityHandler(new DarkBloodBricks());
    }

    public static class TreeLog implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_UNDEAD_LOG) {
                return new TranquilityStack(EnumTranquilityType.TREE, 1.2);
            }
            return null;
        }
    }

    public static class TreeLeaves implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_UNDEAD_LEAVES) {
                return new TranquilityStack(EnumTranquilityType.PLANT, 1.2);
            }
            return null;
        }
    }

    public static class Planks implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_UNDEAD_PLANK) {
                return new TranquilityStack(EnumTranquilityType.PLANT, 0.8);
            }
            return null;
        }
    }

    public static class Blood implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_BLOOD) {
                return new TranquilityStack(EnumTranquilityType.WATER, 1.6);
            }
            return null;
        }
    }

    public static class Poison implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_POISON) {
                return new TranquilityStack(EnumTranquilityType.WATER, 0.8);
            }
            return null;
        }
    }

    public static class HardenedBlood implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_HARDENED_BLOOD) {
                return new TranquilityStack(EnumTranquilityType.EARTHEN, 1.5);
            }
            return null;
        }
    }

    public static class DarkBricks implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_DARK_BRICK) {
                return new TranquilityStack(EnumTranquilityType.EARTHEN, 1);
            }
            return null;
        }
    }

    public static class DarkBloodBricks implements ITranquilityHandler {
        @Override
        public TranquilityStack getTranquilityOfBlock(Level world, BlockPos pos, Block block, BlockState state) {
            if (block == RegistryEntries.BLOCK_DARK_BLOOD_BRICK) {
                return new TranquilityStack(EnumTranquilityType.EARTHEN, 1.5);
            }
            return null;
        }
    }

}
