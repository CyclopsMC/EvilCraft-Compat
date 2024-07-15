package org.cyclops.evilcraftcompat.modcompat.capabilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.cyclops.commoncapabilities.api.capability.Capabilities;
import org.cyclops.commoncapabilities.api.capability.fluidhandler.FluidMatch;
import org.cyclops.commoncapabilities.api.capability.recipehandler.IPrototypedIngredientAlternatives;
import org.cyclops.commoncapabilities.api.capability.recipehandler.IRecipeHandler;
import org.cyclops.commoncapabilities.api.capability.recipehandler.PrototypedIngredientAlternativesList;
import org.cyclops.commoncapabilities.api.ingredient.IMixedIngredients;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;
import org.cyclops.commoncapabilities.api.ingredient.MixedIngredients;
import org.cyclops.commoncapabilities.api.ingredient.PrototypedIngredient;
import org.cyclops.cyclopscore.ingredient.recipe.IngredientRecipeHelpers;
import org.cyclops.cyclopscore.ingredient.recipe.RecipeHandlerRecipeType;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.blockentity.BlockEntityBloodInfuser;
import org.cyclops.evilcraft.core.recipe.type.IInventoryFluidTier;
import org.cyclops.evilcraft.core.recipe.type.InventoryFluidTier;
import org.cyclops.evilcraft.core.recipe.type.RecipeBloodInfuser;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Compatibility for blood infuser recipe handler capabilities.
 * @author rubensworks
 */
public class RecipeHandlerBloodInfuserBlockEntityCompat implements ICapabilityConstructor<BlockEntityBloodInfuser, Direction, IRecipeHandler, BlockEntityType<BlockEntityBloodInfuser>> {

    @Override
    public BaseCapability<IRecipeHandler, Direction> getCapability() {
        return Capabilities.RecipeHandler.BLOCK;
    }

    @Nullable
    @Override
    public ICapabilityProvider<BlockEntityBloodInfuser, Direction, IRecipeHandler> createProvider(BlockEntityType<BlockEntityBloodInfuser> host) {
        return (blockEntity, side) -> new RecipeHandler(blockEntity);
    }

    public static class RecipeHandler extends RecipeHandlerRecipeType<IInventoryFluidTier, RecipeBloodInfuser> {

        private final BlockEntityBloodInfuser host;

        public RecipeHandler(BlockEntityBloodInfuser host) {
            super(host::getLevel, RegistryEntries.RECIPETYPE_BLOOD_INFUSER.get(),
                    Sets.newHashSet(IngredientComponent.ITEMSTACK, IngredientComponent.FLUIDSTACK),
                    Sets.newHashSet(IngredientComponent.ITEMSTACK));
            this.host = host;
        }

        @Override
        public boolean isValidSizeInput(IngredientComponent<?, ?> component, int size) {
            return (component == IngredientComponent.ITEMSTACK || component == IngredientComponent.FLUIDSTACK) && size == 1;
        }

        @Nullable
        @Override
        protected IInventoryFluidTier getRecipeInputContainer(IMixedIngredients input) {
            InventoryFluidTier inventory = new InventoryFluidTier(NonNullList.withSize(1, ItemStack.EMPTY), NonNullList.withSize(1, FluidStack.EMPTY),
                    host.getTileWorkingMetadata().getTier(host.getInventory()));
            if (!input.getInstances(IngredientComponent.ITEMSTACK).isEmpty()) {
                inventory.setItem(0, input.getInstances(IngredientComponent.ITEMSTACK).get(0));
            }
            if (!input.getInstances(IngredientComponent.FLUIDSTACK).isEmpty()) {
                inventory.getFluidHandler().fill(input.getInstances(IngredientComponent.FLUIDSTACK).get(0), IFluidHandler.FluidAction.EXECUTE);
            }
            return inventory;
        }

        @Nullable
        @Override
        protected Map<IngredientComponent<?, ?>, List<IPrototypedIngredientAlternatives<?, ?>>> getRecipeInputIngredients(RecipeBloodInfuser recipe) {
            Map<IngredientComponent<?, ?>, List<IPrototypedIngredientAlternatives<?, ?>>> inputs = Maps.newIdentityHashMap();
            if (recipe.getInputIngredient().isPresent()) {
                inputs.put(IngredientComponent.ITEMSTACK, Lists.newArrayList(IngredientRecipeHelpers.getPrototypesFromIngredient(recipe.getInputIngredient().get())));
            }
            if (recipe.getInputFluid().isPresent()) {
                inputs.put(IngredientComponent.FLUIDSTACK, Lists.newArrayList(new PrototypedIngredientAlternativesList<>(
                        Lists.newArrayList(new PrototypedIngredient<>(IngredientComponent.FLUIDSTACK, recipe.getInputFluid().get(), FluidMatch.EXACT)))));
            }
            return inputs;
        }

        @Nullable
        @Override
        protected IMixedIngredients getRecipeOutputIngredients(RecipeBloodInfuser recipe) {
            Map<IngredientComponent<?, ?>, List<?>> outputIngredients = Maps.newIdentityHashMap();
            if (!recipe.getOutputItemFirst().isEmpty()) {
                outputIngredients.put(IngredientComponent.ITEMSTACK, Lists.newArrayList(recipe.getOutputItemFirst()));
            }

            // Validate output
            if (outputIngredients.isEmpty()) {
                return null;
            }

            return new MixedIngredients(outputIngredients);
        }
    }
}
