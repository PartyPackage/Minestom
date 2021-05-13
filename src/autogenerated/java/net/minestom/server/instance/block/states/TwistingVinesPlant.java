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
public final class TwistingVinesPlant {
    public static final BlockState TWISTING_VINES_PLANT_0 = new BlockState(NamespaceID.from("minecraft:twisting_vines_plant_0"), (short) 15051, Block.TWISTING_VINES_PLANT, new RawBlockStateData(0.0, 0, false, "DESTROY", false, false, false, false, false, false, 7, "[]"));

    static {
        Registry.BLOCK_STATE_REGISTRY.register(TWISTING_VINES_PLANT_0);
    }

    public static void initStates() {
        Block.TWISTING_VINES_PLANT.addBlockState(TWISTING_VINES_PLANT_0);
    }
}