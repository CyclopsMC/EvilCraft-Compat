package org.cyclops.evilcraftcompat.modcompat.neovitae;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.api.soul.AnimaTicket;
import com.breakinblocks.neovitae.api.soul.IAnima;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Server-side helpers to interact with the anima of a player,
 * which is what Neo Vitae calls the soul network of Blood Magic.
 * @author rubensworks
 */
public final class AnimaHelpers {

    private AnimaHelpers() {

    }

    /**
     * Get the anima of the given player.
     * @param uuid The player uuid.
     * @return The anima, or null if it is unavailable, e.g. when there is no server (yet).
     */
    @Nullable
    public static IAnima getAnima(UUID uuid) {
        try {
            return NeoVitaeAPI.getInstance().getAnima(uuid);
        } catch (IllegalStateException e) {
            // Neo Vitae has not been initialized yet
            return null;
        }
    }

    /**
     * Get the essence (Essentia Vitae) that is currently stored in the given player's anima.
     * @param uuid The player uuid.
     * @return The stored essence.
     */
    public static int getCurrentEssence(UUID uuid) {
        IAnima anima = getAnima(uuid);
        return anima == null ? 0 : anima.getCurrentEV();
    }

    /**
     * Add essence to the given player's anima.
     * @param uuid The player uuid.
     * @param amount The amount to add.
     * @param maximum The maximum amount the anima may hold.
     * @return The amount that was actually added.
     */
    public static int addEssence(UUID uuid, int amount, int maximum) {
        IAnima anima = getAnima(uuid);
        if (anima == null || amount <= 0) {
            return 0;
        }
        return anima.add(AnimaTicket.create(amount), maximum);
    }

    /**
     * Remove essence from the given player's anima.
     * @param uuid The player uuid.
     * @param amount The amount to remove.
     * @return The amount that was actually removed.
     */
    public static int syphonEssence(UUID uuid, int amount) {
        IAnima anima = getAnima(uuid);
        if (anima == null || amount <= 0) {
            return 0;
        }
        return anima.syphon(AnimaTicket.create(amount));
    }

    /**
     * Overwrite the essence in the given player's anima.
     * @param uuid The player uuid.
     * @param amount The amount to set.
     * @param maximum The maximum amount the anima may hold.
     */
    public static void setEssence(UUID uuid, int amount, int maximum) {
        IAnima anima = getAnima(uuid);
        if (anima != null) {
            anima.set(AnimaTicket.create(Math.max(0, amount)), maximum);
        }
    }

}
