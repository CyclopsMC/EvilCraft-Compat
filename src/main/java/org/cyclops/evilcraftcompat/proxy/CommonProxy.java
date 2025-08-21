package org.cyclops.evilcraftcompat.proxy;

import org.cyclops.cyclopscore.init.ModBaseNeoForge;
import org.cyclops.cyclopscore.proxy.CommonProxyComponent;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

/**
 * Proxy for server and client side.
 * @author rubensworks
 *
 */
public class CommonProxy extends CommonProxyComponent {

    @Override
    public ModBaseNeoForge<?> getMod() {
        return EvilCraftCompat._instance;
    }

}
