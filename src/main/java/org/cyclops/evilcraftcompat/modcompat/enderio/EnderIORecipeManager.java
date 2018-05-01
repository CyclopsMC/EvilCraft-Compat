package org.cyclops.evilcraftcompat.modcompat.enderio;

import crazypants.enderio.base.recipe.Recipe;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeInput;
import crazypants.enderio.base.recipe.RecipeOutput;
import crazypants.enderio.base.recipe.sagmill.SagMillRecipeManager;
import net.minecraft.item.ItemStack;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.block.DarkOre;
import org.cyclops.evilcraft.item.DarkGem;
import org.cyclops.evilcraft.item.DarkGemConfig;
import org.cyclops.evilcraft.item.DarkGemCrushedConfig;

/**
 * EnderIO recipe manager registrations.
 * @author runesmacher
 *
 */
public class EnderIORecipeManager{
    public static void register() {
        // Sagmill dark ore
        if(Configs.isEnabled(DarkGemConfig.class) && Configs.isEnabled(DarkGemCrushedConfig.class)) {
            ItemStack input = new ItemStack(DarkOre.getInstance());
            ItemStack output = new ItemStack(DarkGem.getInstance(), 2);
            int energy = 3600;

            SagMillRecipeManager.getInstance().addRecipe(new Recipe(
                    new RecipeInput(input), energy, RecipeBonusType.MULTIPLY_OUTPUT, new RecipeOutput(output)));
        }

        // Sagmill dark gem -> crushed dark gem
        if(Configs.isEnabled(DarkGemConfig.class) && Configs.isEnabled(DarkGemCrushedConfig.class)) {
            ItemStack input = new ItemStack(DarkGem.getInstance());
            ItemStack output = new ItemStack(DarkGemCrushedConfig._instance.getItemInstance(), 1);
            int energy = 2400;

            SagMillRecipeManager.getInstance().addRecipe(new Recipe(
                    new RecipeInput(input), energy, RecipeBonusType.MULTIPLY_OUTPUT, new RecipeOutput(output)));
        }
    }

}
