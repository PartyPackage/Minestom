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
public final class RedStainedGlass {
    public static final BlockState RED_STAINED_GLASS_0 = new BlockState(NamespaceID.from("minecraft:red_stained_glass_0"), (short) 4109, Block.RED_STAINED_GLASS, new RawBlockStateData(0.3, 0, false, "NORMAL", true, false, false, false, true, false, 0, "[AABB[0.0, 0.0, 0.0] -> [1.0, 1.0, 1.0]]"));

    static {
        Registry.BLOCK_STATE_REGISTRY.register(RED_STAINED_GLASS_0);
    }

    public static void initStates() {
        Block.RED_STAINED_GLASS.addBlockState(RED_STAINED_GLASS_0);
    }
}