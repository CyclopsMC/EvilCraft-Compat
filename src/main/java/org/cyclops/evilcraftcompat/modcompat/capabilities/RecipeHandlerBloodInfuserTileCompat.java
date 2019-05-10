package org.cyclops.evilcraftcompat.modcompat.capabilities;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.api.capability.recipehandler.IRecipeHandler;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.api.ingredient.IMixedIngredients;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;
import org.cyclops.cyclopscore.init.RecipeHandler;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.SimpleCapabilityConstructor;
import org.cyclops.cyclopscore.recipe.custom.RecipeHandlerMachine;
import org.cyclops.cyclopscore.recipe.custom.component.IngredientAndFluidStackRecipeComponent;
import org.cyclops.cyclopscore.recipe.custom.component.IngredientRecipeComponent;
import org.cyclops.evilcraft.block.BloodInfuser;
import org.cyclops.evilcraft.core.recipe.custom.DurationXpRecipeProperties;
import org.cyclops.evilcraft.core.recipe.custom.IngredientFluidStackAndTierRecipeComponent;
import org.cyclops.evilcraft.tileentity.TileBloodInfuser;
import org.cyclops.evilcraft.tileentity.TileEnvironmentalAccumulator;
import org.cyclops.evilcraftcompat.Capabilities;

import javax.annotation.Nullable;

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

    public static class RecipeHandler extends RecipeHandlerMachine<BloodInfuser, IngredientFluidStackAndTierRecipeComponent, IngredientRecipeComponent, DurationXpRecipeProperties> {

        private final TileBloodInfuser host;

        public RecipeHandler(TileBloodInfuser host) {
            super(BloodInfuser.getInstance(),
                    Sets.newHashSet(IngredientComponent.ITEMSTACK, IngredientComponent.FLUIDSTACK),
                    Sets.newHashSet(IngredientComponent.ITEMSTACK));
            this.host = host;
        }

        @Override
        public boolean isValidSizeInput(IngredientComponent<?, ?> component, int size) {
            return (component == IngredientComponent.ITEMSTACK || component == IngredientComponent.FLUIDSTACK) && size == 1;
        }

        @Override
        protected IngredientFluidStackAndTierRecipeComponent inputIngredientsToRecipeInput(IMixedIngredients inputIngredients) {
            return new IngredientFluidStackAndTierRecipeComponent(
                    inputIngredients.getFirstNonEmpty(IngredientComponent.ITEMSTACK),
                    inputIngredients.getFirstNonEmpty(IngredientComponent.FLUIDSTACK),
                    host.getTier()
            );
        }

    }
}
