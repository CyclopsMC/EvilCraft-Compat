package org.cyclops.evilcraftcompat.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.evilcraftcompat.modcompat.jei.JeiCompatLoader;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace.SpiritFurnaceRecipeJEI;

/**
 * Packet for sending a spirit furnace recipe from server to client.
 * @author rubensworks
 *
 */
public class JeiSpiritFurnaceRecipePacket extends PacketCodec {

    private SpiritFurnaceRecipeJEI recipe;
    private int totalCount;

    public JeiSpiritFurnaceRecipePacket() {

    }

    public JeiSpiritFurnaceRecipePacket(SpiritFurnaceRecipeJEI recipe, int totalCount) {
        this.recipe = recipe;
        this.totalCount = totalCount;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public void encode(FriendlyByteBuf output) {
        super.encode(output);
        SpiritFurnaceRecipeJEI.encode(recipe, output);
        output.writeInt(totalCount);
    }

    @Override
    public void decode(FriendlyByteBuf input) {
        super.decode(input);
        this.recipe = SpiritFurnaceRecipeJEI.decode(input);
        this.totalCount = input.readInt();
    }

    @Override
    public void actionClient(Level world, Player player) {
        JeiCompatLoader.receiveSpiritFurnaceRecipeOnClient(this.recipe, this.totalCount);
    }

    @Override
    public void actionServer(Level world, ServerPlayer player) {

    }

}
