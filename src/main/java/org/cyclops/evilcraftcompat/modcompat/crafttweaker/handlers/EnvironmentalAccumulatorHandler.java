package org.cyclops.evilcraftcompat.modcompat.crafttweaker.handlers;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import org.cyclops.cyclopscore.modcompat.crafttweaker.handlers.RecipeRegistryHandler;
import org.cyclops.cyclopscore.recipe.custom.Recipe;
import org.cyclops.evilcraft.block.EnvironmentalAccumulator;
import org.cyclops.evilcraft.core.recipe.custom.EnvironmentalAccumulatorRecipeComponent;
import org.cyclops.evilcraft.core.recipe.custom.EnvironmentalAccumulatorRecipeProperties;
import org.cyclops.evilcraft.core.weather.WeatherType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Objects;

@ZenClass("mods.evilcraft.EnvironmentalAccumulator")
@ZenRegister
public class EnvironmentalAccumulatorHandler extends RecipeRegistryHandler<EnvironmentalAccumulator, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeProperties> {

    private static final EnvironmentalAccumulatorHandler INSTANCE = new EnvironmentalAccumulatorHandler();

    @Override
    protected EnvironmentalAccumulator getMachine() {
        return EnvironmentalAccumulator.getInstance();
    }

    @Override
    protected String getRegistryName() {
        return "EnvironmentalAccumulator";
    }

    protected static WeatherType getWeather(String weather) {
        return Objects.requireNonNull(WeatherType.valueOf(weather), "Could not find a weather by name " + weather);
    }

    @ZenMethod
    public static void addRecipe(IIngredient inputIngredient, String inputWeather,
                                 IItemStack outputStack, String outputWeather,
                                 int duration, int cooldownTime, double processingSpeed) {
        INSTANCE.add(new Recipe<EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeProperties>(
                new EnvironmentalAccumulatorRecipeComponent(RecipeRegistryHandler.toIngredient(inputIngredient), getWeather(inputWeather)),
                new EnvironmentalAccumulatorRecipeComponent(RecipeRegistryHandler.toStack(outputStack), getWeather(outputWeather)),
                new EnvironmentalAccumulatorRecipeProperties(duration, cooldownTime, processingSpeed)));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient inputIngredient, String inputWeather,
                                    IItemStack outputStack, String outputWeather,
                                    int duration, int cooldownTime, double processingSpeed) {
        INSTANCE.add(new Recipe<EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeComponent, EnvironmentalAccumulatorRecipeProperties>(
                new EnvironmentalAccumulatorRecipeComponent(RecipeRegistryHandler.toIngredient(inputIngredient), getWeather(inputWeather)),
                new EnvironmentalAccumulatorRecipeComponent(RecipeRegistryHandler.toStack(outputStack), getWeather(outputWeather)),
                new EnvironmentalAccumulatorRecipeProperties(duration, cooldownTime, processingSpeed)));
    }

    @ZenMethod
    public static void removeRecipesWithOutput(IItemStack outputStack, String outputWeather) {
        INSTANCE.remove(
                new EnvironmentalAccumulatorRecipeComponent(RecipeRegistryHandler.toStack(outputStack), getWeather(outputWeather))
        );
    }
}
