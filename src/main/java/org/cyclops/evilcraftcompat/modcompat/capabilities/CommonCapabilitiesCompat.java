package org.cyclops.evilcraftcompat.modcompat.capabilities;

import org.cyclops.cyclopscore.modcompat.ICompatInitializer;
import org.cyclops.cyclopscore.modcompat.IModCompat;

/**
 * Compatibility plugin for Common Capabilities.
 * @author rubensworks
 *
 */
public class CommonCapabilitiesCompat implements IModCompat {

    @Override
    public String getId() {
        return "commoncapabilities";
    }

    @Override
    public boolean isEnabledDefault() {
        return true;
    }

    @Override
    public String getComment() {
        return "Compatibility plugin for Common Capabilities";
    }

    @Override
    public ICompatInitializer createInitializer() {
        return new CommonCapabilitiesCompatInitializer();
    }
}
