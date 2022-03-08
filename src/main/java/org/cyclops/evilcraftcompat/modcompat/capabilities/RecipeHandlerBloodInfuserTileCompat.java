package org.cyclops.evilcraftcompat.modcompat.capabilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
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
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.SimpleCapabilityConstructor;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.core.recipe.type.IInventoryFluidTier;
import org.cyclops.evilcraft.core.recipe.type.InventoryFluidTier;
import org.cyclops.evilcraft.core.recipe.type.RecipeBloodInfuser;
import org.cyclops.evilcraft.tileentity.TileBloodInfuser;
import org.cyclops.evilcraftcompat.Capabilities;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Compatibility for blood infuser recipe handler capabilities.
 * @author rubensworks
 */
public class RecipeHandlerBloodInfuserTileCompat extends SimpleCapabilityConstructor<IRecipeHandler, TileBloodInfuser> {

    @Override
    public Capability<IRecipeHandler> getCapability() {
        return Capabilities.RECIPE_HANDLER;
    }

    @Nullable
    @Override
    public ICapabilityProvider createProvider(TileBloodInfuser host) {
        return new DefaultCapabilityProvider<>(() -> Capabilities.RECIPE_HANDLER, new RecipeHandler(host));
    }

    public static class RecipeHandler extends RecipeHandlerRecipeType<IInventoryFluidTier, RecipeBloodInfuser> {

        private final TileBloodInfuser host;

        public RecipeHandler(TileBloodInfuser host) {
            super(host::getLevel, RegistryEntries.RECIPETYPE_BLOOD_INFUSER,
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
            IInventoryFluidTier inventory = new InventoryFluidTier(NonNullList.withSize(1, ItemStack.EMPTY), NonNullList.withSize(1, FluidStack.EMPTY),
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
            if (!recipe.getInputIngredient().isEmpty()) {
                inputs.put(IngredientComponent.ITEMSTACK, Lists.newArrayList(IngredientRecipeHelpers.getPrototypesFromIngredient(recipe.getInputIngredient())));
            }
            if (!recipe.getInputFluid().isEmpty()) {
                inputs.put(IngredientComponent.FLUIDSTACK, Lists.newArrayList(new PrototypedIngredientAlternativesList<>(
                        Lists.newArrayList(new PrototypedIngredient<>(IngredientComponent.FLUIDSTACK, recipe.getInputFluid(), FluidMatch.EXACT)))));
            }
            return inputs;
        }

        @Nullable
        @Override
        protected IMixedIngredients getRecipeOutputIngredients(RecipeBloodInfuser recipe) {
            Map<IngredientComponent<?, ?>, List<?>> outputIngredients = Maps.newIdentityHashMap();
            if (!recipe.getOutputItem().isEmpty()) {
                outputIngredients.put(IngredientComponent.ITEMSTACK, Lists.newArrayList(recipe.getOutputItem()));
            }

            // Validate output
            if (outputIngredients.isEmpty()) {
                return null;
            }

            return new MixedIngredients(outputIngredients);
        }
    }
}
