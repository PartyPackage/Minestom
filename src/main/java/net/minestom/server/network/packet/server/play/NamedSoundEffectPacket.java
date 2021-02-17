package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.sound.SoundCategory;
import net.minestom.server.utils.binary.BinaryReader;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class NamedSoundEffectPacket implements ServerPacket {

    public String soundName;
    public SoundCategory soundCategory;
    public int x, y, z;
    public float volume;
    public float pitch;

    public NamedSoundEffectPacket() {
        soundName = "";
        soundCategory = SoundCategory.AMBIENT;
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(soundName);
        writer.writeVarInt(soundCategory.ordinal());
        writer.writeInt(x * 8);
        writer.writeInt(y * 8);
        writer.writeInt(z * 8);
        writer.writeFloat(volume);
        writer.writeFloat(pitch);
    }

    @Override
    public void read(@NotNull BinaryReader reader) {
        soundName = reader.readSizedString(Integer.MAX_VALUE);
        soundCategory = SoundCategory.values()[reader.readVarInt()];
        x = reader.readInt() / 8;
        y = reader.readInt() / 8;
        z = reader.readInt() / 8;
        volume = reader.readFloat();
        pitch = reader.readFloat();
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.NAMED_SOUND_EFFECT;
    }
}
