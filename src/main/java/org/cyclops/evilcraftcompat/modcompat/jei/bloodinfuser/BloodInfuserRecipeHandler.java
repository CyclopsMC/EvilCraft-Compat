package org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.cyclops.evilcraft.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI;

import javax.annotation.Nonnull;

/**
 * Handler for the Blood Infuser recipes.
 * @author rubensworks
 */
public class BloodInfuserRecipeHandler implements IRecipeHandler<org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI> {

    public static final String CATEGORY = Reference.MOD_ID + ":bloodInfuser";

    @Nonnull
    @Override
    public Class<org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI> getRecipeClass() {
        return org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI recipe) {
        return CATEGORY;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull org.cyclops.evilcraftcompat.modcompat.jei.bloodinfuser.BloodInfuserRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull BloodInfuserRecipeJEI recipe) {
        return recipe != null;
    }

}
