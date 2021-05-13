package net.minestom.server.instance.block.states;

import java.lang.Deprecated;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockState;
import net.minestom.server.raw_data.RawBlockStateData;
import net.minestom.server.registry.Registry;
import net.minestom.server.utils.NamespaceID;

/**
 * AUTOGENERATED by BlockGenerator
 */
@Deprecated(
        since = "forever",
        forRemoval = false
)
public final class WhiteConcretePowder {
    public static final BlockState WHITE_CONCRETE_POWDER_0 = new BlockState(NamespaceID.from("minecraft:white_concrete_powder_0"), (short) 9458, Block.WHITE_CONCRETE_POWDER, new RawBlockStateData(0.5, 0, true, "NORMAL", true, false, false, false, true, true, 2, "[AABB[0.0, 0.0, 0.0] -> [1.0, 1.0, 1.0]]"));

    static {
        Registry.BLOCK_STATE_REGISTRY.register(WHITE_CONCRETE_POWDER_0);
    }

    public static void initStates() {
        Block.WHITE_CONCRETE_POWDER.addBlockState(WHITE_CONCRETE_POWDER_0);
    }
}