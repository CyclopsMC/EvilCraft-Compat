package org.cyclops.evilcraftcompat.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.evilcraftcompat.Reference;
import org.cyclops.evilcraftcompat.modcompat.jei.JeiModCompatLoader;
import org.cyclops.evilcraftcompat.modcompat.jei.spiritfurnace.SpiritFurnaceRecipeJEI;

/**
 * Packet for sending a spirit furnace recipe from server to client.
 * @author rubensworks
 *
 */
public class JeiSpiritFurnaceRecipePacket extends PacketCodec {

    public static final Type<JeiSpiritFurnaceRecipePacket> ID = new Type<>(Identifier.fromNamespaceAndPath(Reference.MOD_ID, "jei_spirit_furnace_recipe"));
    public static final StreamCodec<RegistryFriendlyByteBuf, JeiSpiritFurnaceRecipePacket> CODEC = getCodec(JeiSpiritFurnaceRecipePacket::new);

    private SpiritFurnaceRecipeJEI recipe;
    private int totalCount;

    public JeiSpiritFurnaceRecipePacket() {
        super(ID);
    }

    public JeiSpiritFurnaceRecipePacket(SpiritFurnaceRecipeJEI recipe, int totalCount) {
        this();
        this.recipe = recipe;
        this.totalCount = totalCount;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf output) {
        super.encode(output);
        SpiritFurnaceRecipeJEI.encode(recipe, output);
        output.writeInt(totalCount);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf input) {
        super.decode(input);
        this.recipe = SpiritFurnaceRecipeJEI.decode(input);
        this.totalCount = input.readInt();
    }

    @Override
    public void actionClient(Level world, Player player) {
        JeiModCompatLoader.receiveSpiritFurnaceRecipeOnClient(this.recipe, this.totalCount);
    }

    @Override
    public void actionServer(Level world, ServerPlayer player) {

    }

}
