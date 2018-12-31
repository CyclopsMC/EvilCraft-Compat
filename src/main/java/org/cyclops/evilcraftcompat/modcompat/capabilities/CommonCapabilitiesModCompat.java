package org.cyclops.evilcraftcompat.modcompat.capabilities;

import org.cyclops.cyclopscore.CyclopsCore;
import org.cyclops.cyclopscore.ingredient.recipe.IRecipeInputOutputDefinitionRegistry;
import org.cyclops.cyclopscore.ingredient.recipe.handler.IngredientAndFluidStackRecipeComponentHandler;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.evilcraft.core.recipe.custom.IngredientFluidStackAndTierRecipeComponent;
import org.cyclops.evilcraftcompat.Reference;

/**
 * Compatibility plugin for Common Capabilities.
 * @author rubensworks
 *
 */
public class CommonCapabilitiesModCompat implements IModCompat {

    @Override
    public String getModID() {
       return Reference.MOD_COMMONCAPABILITIES;
    }

    @Override
    public void onInit(Step step) {
    	if (step == Step.INIT) {
			CyclopsCore._instance.getRegistryManager().getRegistry(IRecipeInputOutputDefinitionRegistry.class)
					.setRecipeInputOutputHandler(IngredientFluidStackAndTierRecipeComponent.class, new IngredientAndFluidStackRecipeComponentHandler());
		}
    }
    
    @Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getComment() {
		return "Capabilities support";
	}

}
