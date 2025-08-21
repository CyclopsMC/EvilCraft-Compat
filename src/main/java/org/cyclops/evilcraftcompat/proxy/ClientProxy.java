package org.cyclops.evilcraftcompat.proxy;

import org.cyclops.cyclopscore.init.ModBaseNeoForge;
import org.cyclops.cyclopscore.proxy.ClientProxyComponent;
import org.cyclops.evilcraftcompat.EvilCraftCompat;

/**
 * Proxy for the client side.
 *
 * @author rubensworks
 *
 */
public class ClientProxy extends ClientProxyComponent {

    public ClientProxy() {
        super(new CommonProxy());
    }

    @Override
    public ModBaseNeoForge<?> getMod() {
        return EvilCraftCompat._instance;
    }

}
