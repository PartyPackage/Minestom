package net.minestom.server.entity.metadata.monster.zombie;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.villager.VillagerMeta;
import net.minestom.server.entity.metadata.villager.VillagerProfession;
import net.minestom.server.entity.metadata.villager.VillagerType;
import org.jetbrains.annotations.NotNull;

public class ZombieVillagerMeta extends ZombieMeta {

    public ZombieVillagerMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isConverting() {
        return super.metadata.getIndex((byte) 18, false);
    }

    public void setConverting(boolean value) {
        super.metadata.setIndex((byte) 18, Metadata.Boolean(value));
    }

    public VillagerMeta.VillagerData getVillagerData() {
        int[] data = super.metadata.getIndex((byte) 17, null);
        if (data == null) {
            return new VillagerMeta.VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, VillagerMeta.Level.NOVICE);
        }
        return new VillagerMeta.VillagerData(VillagerType.values().get(data[0]), VillagerProfession.values().get(data[1]), VillagerMeta.Level.VALUES[data[2] - 1]);
    }

    public void setVillagerData(VillagerMeta.VillagerData data) {
        super.metadata.setIndex((byte) 17, Metadata.VillagerData(
                data.getType().getNumericalId(),
                data.getProfession().getNumericalId(),
                data.getLevel().ordinal() + 1
        ));
    }

}
