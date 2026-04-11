package org.cyclops.evilcraftcompat.modcompat.jei.environmentalaccumulator;

import com.google.common.collect.Maps;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraft.core.recipe.type.RecipeEnvironmentalAccumulator;
import org.cyclops.evilcraft.core.weather.WeatherType;

import java.util.Map;

/**
 * Category for the Envir Acc recipes.
 * @author rubensworks
 */
public abstract class CommonEnvironmentalAccumulatorRecipeCategory implements IRecipeCategory<RecipeHolder<RecipeEnvironmentalAccumulator>> {

    private final Map<WeatherType, IDrawableStatic> weatherIcons;
    private final Pair<Integer, Integer> weatherInPos;
    private final Pair<Integer, Integer> weatherOutPos;

    private RecipeHolder<RecipeEnvironmentalAccumulator> lastRecipe = null;

    public CommonEnvironmentalAccumulatorRecipeCategory(IGuiHelper guiHelper, Pair<Integer, Integer> weatherInPos, Pair<Integer, Integer> weatherOutPos) {
        this.weatherInPos = weatherInPos;
        this.weatherOutPos = weatherOutPos;
        weatherIcons = Maps.newHashMap();
        Identifier weatherResourceLocation = Identifier.parse(Reference.MOD_ID + ":" + Reference.TEXTURE_PATH_GUI + "weathers.png");
        weatherIcons.put(WeatherType.CLEAR, guiHelper.createDrawable(weatherResourceLocation, 0, 0, 16, 16));
        weatherIcons.put(WeatherType.RAIN, guiHelper.createDrawable(weatherResourceLocation, 16, 0, 16, 16));
        weatherIcons.put(WeatherType.LIGHTNING, guiHelper.createDrawable(weatherResourceLocation, 32, 0, 16, 16));
    }

    @Override
    public void draw(RecipeHolder<RecipeEnvironmentalAccumulator> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        if(lastRecipe != null) {
            if(lastRecipe.value().getInputWeather() != WeatherType.ANY) {
                weatherIcons.get(lastRecipe.value().getInputWeather()).draw(guiGraphics, weatherInPos.getLeft(), weatherInPos.getRight());
            }
            if(lastRecipe.value().getOutputWeather() != WeatherType.ANY) {
                weatherIcons.get(lastRecipe.value().getOutputWeather()).draw(guiGraphics, weatherOutPos.getLeft(), weatherOutPos.getRight());
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<RecipeEnvironmentalAccumulator> recipe, IFocusGroup focuses) {
        this.lastRecipe = recipe;
    }
}
