package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import com.google.common.collect.Maps;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.core.weather.WeatherType;

import java.util.Map;

/**
 * Category for the Envir Acc recipes.
 * @author rubensworks
 */
public abstract class CommonEnvironmentalAccumulatorRecipeCategory<T extends CommonEnvironmentalAccumulatorRecipeJEI<T>> implements IRecipeCategory<T> {

    private final Map<WeatherType, IDrawableStatic> weatherIcons;
    private final Pair<Integer, Integer> weatherInPos;
    private final Pair<Integer, Integer> weatherOutPos;

    private T lastRecipe = null;

    public CommonEnvironmentalAccumulatorRecipeCategory(IGuiHelper guiHelper, Pair<Integer, Integer> weatherInPos, Pair<Integer, Integer> weatherOutPos) {
        this.weatherInPos = weatherInPos;
        this.weatherOutPos = weatherOutPos;
        weatherIcons = Maps.newHashMap();
        ResourceLocation weatherResourceLocation = new ResourceLocation(Reference.MOD_ID + ":" + Reference.TEXTURE_PATH_GUI + "weathers.png");
        weatherIcons.put(WeatherType.CLEAR, guiHelper.createDrawable(weatherResourceLocation, 0, 0, 16, 16));
        weatherIcons.put(WeatherType.RAIN, guiHelper.createDrawable(weatherResourceLocation, 16, 0, 16, 16));
        weatherIcons.put(WeatherType.LIGHTNING, guiHelper.createDrawable(weatherResourceLocation, 32, 0, 16, 16));
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if(lastRecipe != null) {
            if(lastRecipe.getInputWeather() != WeatherType.ANY) {
                weatherIcons.get(lastRecipe.getInputWeather()).draw(guiGraphics, weatherInPos.getLeft(), weatherInPos.getRight());
            }
            if(lastRecipe.getOutputWeather() != WeatherType.ANY) {
                weatherIcons.get(lastRecipe.getOutputWeather()).draw(guiGraphics, weatherOutPos.getLeft(), weatherOutPos.getRight());
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        this.lastRecipe = recipe;
    }
}
