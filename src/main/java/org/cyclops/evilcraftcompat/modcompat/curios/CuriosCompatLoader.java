package org.cyclops.evilcraftcompat.modcompat.curios;

import net.minecraftforge.fml.InterModComms;
import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

/**
 * @author rubensworks
 */
public class CuriosCompatLoader implements ICompatInitializer {
    @Override
    public void initialize() {
        // Register curios types
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
    }
}
