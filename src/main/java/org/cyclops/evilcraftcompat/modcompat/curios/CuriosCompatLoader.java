package org.cyclops.evilcraftcompat.modcompat.curios;

import net.minecraftforge.fml.InterModComms;
import org.cyclops.cyclopscore.inventory.PlayerExtendedInventoryIterator;
import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

/**
 * @author rubensworks
 */
public class CuriosCompatLoader implements ICompatInitializer {
    @Override
    public void initialize() {
        // Register curios types
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());

        // Extend player iterator
        PlayerExtendedInventoryIterator.INVENTORY_EXTENDERS
                .add(playerEntity -> CuriosApi.getCuriosHelper().getEquippedCurios(playerEntity).orElse(null));
    }
}
