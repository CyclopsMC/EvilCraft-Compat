package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import org.cyclops.cyclopscore.modcompat.jei.RecipeRegistryJeiRecipeWrapper;
import org.cyclops.evilcraft.RegistryEntries;
import org.cyclops.evilcraft.core.recipe.type.RecipeEnvironmentalAccumulator;
import org.cyclops.evilcraft.core.weather.WeatherType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Recipe wrapper for Envir Acc recipes
 * @author rubensworks
 */
public abstract class CommonEnvironmentalAccumulatorRecipeJEI<T extends CommonEnvironmentalAccumulatorRecipeJEI<T>> extends RecipeRegistryJeiRecipeWrapper<RecipeEnvironmentalAccumulator.Inventory, RecipeEnvironmentalAccumulator, T> {

    private final WeatherType inputWeather;
    private final WeatherType outputWeather;
    private final List<ItemStack> inputItems;
    private final ItemStack outputItem;
    private final int duration;
    private final int cooldownTime;
    private final float processingSpeed;

    public CommonEnvironmentalAccumulatorRecipeJEI(RecipeEnvironmentalAccumulator recipe) {
        super(RegistryEntries.RECIPETYPE_ENVIRONMENTAL_ACCUMULATOR, recipe);
        this.inputItems = Arrays.stream(recipe.getInputIngredient().getItems()).collect(Collectors.toList());
        this.inputWeather = recipe.getInputWeather();
        this.outputItem = recipe.getOutputItem();
        this.outputWeather = recipe.getOutputWeather();
        this.duration = recipe.getDuration();
        this.cooldownTime = recipe.getCooldownTime();
        this.processingSpeed = recipe.getProcessingSpeed();
    }

    protected CommonEnvironmentalAccumulatorRecipeJEI() {
        super(RegistryEntries.RECIPETYPE_ENVIRONMENTAL_ACCUMULATOR, null);
        this.inputWeather = null;
        this.outputWeather = null;
        this.inputItems = null;
        this.outputItem = null;
        this.duration = 0;
        this.cooldownTime = 0;
        this.processingSpeed = 0;
    }

    public WeatherType getInputWeather() {
        return inputWeather;
    }

    public WeatherType getOutputWeather() {
        return outputWeather;
    }

    public List<ItemStack> getInputItems() {
        return inputItems;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public int getDuration() {
        return duration;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public float getProcessingSpeed() {
        return processingSpeed;
    }

    @Override
    protected IRecipeType<RecipeEnvironmentalAccumulator> getRecipeType() {
        return RegistryEntries.RECIPETYPE_ENVIRONMENTAL_ACCUMULATOR;
    }

}
